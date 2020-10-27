package com.legends.cloud.zuul.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author herion
 * @Description //TODO 
 * @Date  2019/1/29
 * @Param 
 * @return 
 **/
public class CoreHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CoreHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

        String header = StringUtils.collectionToDelimitedString(CoreHeaderInterceptor.LABEL.get(), CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
        logger.info("label: "+header);
        requestWrapper.getHeaders().add(CoreHeaderInterceptor.HEADER_LABEL, header);

        return execution.execute(requestWrapper, body);
    }
}
