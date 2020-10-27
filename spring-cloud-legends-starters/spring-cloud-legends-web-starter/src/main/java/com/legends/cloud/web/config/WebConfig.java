package com.legends.cloud.web.config;

import com.legends.cloud.web.aspect.LegendsCallableProcessingInterceptor;
import com.legends.cloud.web.executor.ExecutorNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.*;

@SpringBootConfiguration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    @Qualifier(ExecutorNames.PRIMARY_EXECUTOR)
    ThreadPoolTaskExecutor primaryExecutor;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        log.info("configureContentNegotiation-------->");
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        log.info("configureDefaultServletHandling-------->");
        configurer.enable();
    }


    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        log.info("configureAsyncSupport-------->");
        configurer.registerCallableInterceptors(myCallableProcessingInterceptor());
        configurer.setTaskExecutor(primaryExecutor);
    }

    @Bean
    public CallableProcessingInterceptor myCallableProcessingInterceptor() {
        log.info("myCallableProcessingInterceptor-------->");
        return new LegendsCallableProcessingInterceptor();
    }
}