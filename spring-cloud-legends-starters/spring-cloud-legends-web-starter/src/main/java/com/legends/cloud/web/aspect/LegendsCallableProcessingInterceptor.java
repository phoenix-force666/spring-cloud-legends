package com.legends.cloud.web.aspect;

import com.alibaba.fastjson.JSON;
import com.legends.cloud.web.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.Callable;

@Slf4j
@Configuration
public class LegendsCallableProcessingInterceptor extends CallableProcessingInterceptorAdapter {

//    @Autowired
//    private PrometheusMetricsBiz prometheusMetricsBiz;

	@Override
	public <T> void postProcess(NativeWebRequest req, Callable<T> task, Object concurrentResult) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) req.getNativeRequest();

//        prometheusMetricsBiz.afterCompletion(httpServletRequest,(HttpServletResponse) req.getNativeResponse());

        if(Objects.nonNull(concurrentResult)){
            boolean isLogs= (boolean) httpServletRequest.getAttribute(SysConstant.IS_LOGS);
            if(isLogs){
                log.info("响应信息：{}", JSON.toJSONString(concurrentResult));
            }else{
                log.info("响应信息，忽略输出！");
            }

        }else{
            log.info("响应信息为空！！！");
        }

        Object startTime=httpServletRequest.getAttribute(SysConstant.START_TIME);
        long beginTime=0L;
        if(Objects.nonNull(startTime)){
            beginTime=Long.valueOf(startTime.toString());
        }
        long costMs = System.currentTimeMillis() - beginTime;
        log.debug("》》===== 方法：{}请求结束，耗时：{}ms =====《《", httpServletRequest.getMethod(), costMs);
	}

}
