package top.legendscloud.gateway.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.netty.buffer.ByteBufAllocator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.legendscloud.gateway.util.SqLinjectionRuleUtils;
import top.legendscloud.gateway.util.XssCleanRuleUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Component
@ConfigurationProperties(prefix = "gateway.security.ignore")
@RefreshScope
public class SqLinjectionFilter implements GlobalFilter, Ordered {

    private String[] sqlinjectionHttpUrls = new String[0];

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        // grab configuration from Config object
        log.debug("----自定义防sql注入网关全局过滤器生效----");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        String contentType = serverHttpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        URI uri = exchange.getRequest().getURI();

        //1.动态刷新 sql注入的过滤的路径
        String path = serverHttpRequest.getURI().getRawPath();
        String matchUrls[] = this.getSqlinjectionHttpUrls();

//        if( AuthUtils.isMatchPath(path, matchUrls)){
//            log.error("请求【{}】在sql注入过滤白名单中，直接放行", path);
//            return chain.filter(exchange);
//        }

        Boolean postFlag = (method == HttpMethod.POST || method == HttpMethod.PUT) &&
                (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_VALUE.equals(contentType));

        //过滤get请求
        if (method == HttpMethod.GET) {

            String rawQuery = uri.getRawQuery();
            if (StringUtils.isBlank(rawQuery)){
                return chain.filter(exchange);
            }

            log.debug("请求参数为：{}", rawQuery);
            // 执行sql注入校验清理
            boolean chkRet = SqLinjectionRuleUtils.getRequestSqlKeyWordsCheck(rawQuery);

            //    如果存在sql注入,直接拦截请求
            if (chkRet) {
                log.error("请求【" + uri.getRawPath() + uri.getRawQuery() + "】参数中包含不允许sql的关键词, 请求拒绝");
                return setUnauthorizedResponse(chain,exchange);
            }
            //透传参数，不对参数做任何处理
            return chain.filter(exchange);
        }
        //post请求时，如果是文件上传之类的请求，不修改请求消息体
        else if (postFlag){

            return DataBufferUtils.join(serverHttpRequest.getBody()).flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(
                    Optional.empty())
                    .flatMap(optional -> {
                        // 取出body中的参数
                        String bodyString = "";
                        if (optional.isPresent()) {
                            byte[] oldBytes = new byte[optional.get().readableByteCount()];
                            optional.get().read(oldBytes);
                            bodyString = new String(oldBytes, StandardCharsets.UTF_8);
                        }
                        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
                        // 执行XSS清理
                        log.debug("{} - [{}] 请求参数：{}", method, uri.getPath(), bodyString);
                        boolean chkRet=false;
                        if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                            //如果MediaType是json才执行json方式验证
                            chkRet = SqLinjectionRuleUtils.postRequestSqlKeyWordsCheck(bodyString);
                        } else {
                            //form表单方式，需要走get请求
                            try {
                                chkRet = SqLinjectionRuleUtils.getRequestSqlKeyWordsCheck(bodyString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        //  如果存在sql注入,直接拦截请求
                        if (chkRet) {
                            log.error("{} - [{}] 参数：{}, 包含不允许sql的关键词，请求拒绝", method, uri.getPath(), bodyString);
                            return setUnauthorizedResponse(chain,exchange);
                        }

                        ServerHttpRequest newRequest = serverHttpRequest.mutate().uri(uri).build();

                        // 重新构造body
                        byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
                        DataBuffer bodyDataBuffer = toDataBuffer(newBytes);
                        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                        // 重新构造header
                        HttpHeaders headers = new HttpHeaders();
                        headers.putAll(httpHeaders);
                        // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
                        int length = newBytes.length;
                        headers.remove(HttpHeaders.CONTENT_LENGTH);
                        headers.setContentLength(length);
                        headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8");
                        // 重写ServerHttpRequestDecorator，修改了body和header，重写getBody和getHeaders方法
                        newRequest = new ServerHttpRequestDecorator(newRequest) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return bodyFlux;
                            }

                            @Override
                            public HttpHeaders getHeaders() {
                                return headers;
                            }
                        };

                        return chain.filter(exchange.mutate().request(newRequest).build());
                    });
        } else {
            return chain.filter(exchange);
        }

    }


    // 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 设置403拦截状态
     */
    private Mono<Void> setUnauthorizedResponse(GatewayFilterChain chain,ServerWebExchange exchange) {
//        return WebfluxResponseUtil.responseFailed(exchange, HttpStatus.FORBIDDEN.value(),
//                "request is forbidden, SQL keywords are not allowed in the parameters.");
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 定义新的消息头
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getResponse().getHeaders());
        //在ServerHttpResponseDecorator 这个类里面实现对响应数据的处理，复写writeWith()和 getHeaders()两个方法
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffer -> {

                        //过滤器后置逻辑
                        //上面第2个问题是因为响应体数据量太大，分段截取时造成的数据错误问题
                        //使用dataBufferFactory.join解决该问题

                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffer);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        //释放掉内存
                        DataBufferUtils.release(join);
                        //实际获取到的响应数据
                        String responseStr = new String(content, StandardCharsets.UTF_8);

                        //这里 自定义一个新的响应数据，作为返回数据，相当于修改响应体
                        String newResponseStr = "{\"hhhh\":666}";
                        byte[] newResponseBytes = newResponseStr.getBytes(StandardCharsets.UTF_8);

                        // 给header设置长度 解决上面第1个无响应数据的问题
                        headers.setContentLength(newResponseBytes.length);
                        HttpHeaders httpHeaders = originalResponse.getHeaders();

                        //设置响应体类型 Content-Type 解决第3个问题
                        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
                        //newResponseBytes是修改之后要返回的响应体数据
                        return bufferFactory.wrap(newResponseBytes);
                    }));
                }
                return super.writeWith(body);
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                //由于修改了请求体的body，导致content-length长度不确定，因此使用分块编码
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }

        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    /**
     * 字节数组转DataBuffer
     *
     * @param bytes 字节数组
     * @return DataBuffer
     */
    private DataBuffer toDataBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    public String[] getSqlinjectionHttpUrls() {
        return sqlinjectionHttpUrls;
    }

    public void setSqlinjectionHttpUrls(String[] sqlinjectionHttpUrls) {
        this.sqlinjectionHttpUrls = sqlinjectionHttpUrls;
    }
}