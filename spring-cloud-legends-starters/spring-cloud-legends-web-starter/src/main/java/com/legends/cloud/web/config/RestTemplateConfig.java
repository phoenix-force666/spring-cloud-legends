package com.legends.cloud.web.config;

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootConfiguration
@EnableCircuitBreaker
public class RestTemplateConfig {

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 10;
    
    private static final int DEFAULT_MAX_TIME_OUT = 10000;
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }


    @Bean("httpRestTemplate")
    public RestTemplate httpRestTemplate() {
        return new RestTemplate(httpRequestFactory());
    }
    
    @Bean
    public ClientHttpRequestFactory httpRequestFactory(){
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        SocketConfig socketConfig = SocketConfig
                .custom()
                .setSoKeepAlive(false)
                .setTcpNoDelay(true)
                .build();
        PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager();
        pm.setDefaultSocketConfig(socketConfig);
        pm.closeIdleConnections(DEFAULT_MAX_TIME_OUT, TimeUnit.MICROSECONDS);
        pm.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        pm.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(pm)
                .build();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }
}
