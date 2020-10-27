package com.legends.cloud.web.executor;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/17.
 */
@EnableAsync
@Component("executorConfig")
public class ExecutorConfig {
    @Value("${spring.pool.primary.corePoolSize}")
    private int primaryCorePoolSize;
    @Value("${spring.pool.primary.maxPoolSize}")
    private int primaryMaxPoolSize;
    @Value("${spring.pool.primary.queueCapacity}")
    private int primaryQueueCapacity;

    @Value("${spring.pool.secondary.corePoolSize}")
    private int secondaryCorePoolSize;
    @Value("${spring.pool.secondary.maxPoolSize}")
    private int secondaryMaxPoolSize;
    @Value("${spring.pool.secondary.queueCapacity}")
    private int secondaryQueueCapacity;

    @Bean(ExecutorNames.PRIMARY_EXECUTOR)
    public ThreadPoolTaskExecutor primaryThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(primaryCorePoolSize);
        executor.setMaxPoolSize(primaryMaxPoolSize);
        executor.setQueueCapacity(primaryQueueCapacity);
        executor.setThreadNamePrefix("primaryThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(ExecutorNames.SECONDARY_EXECUTOR)
    public ThreadPoolTaskExecutor secondaryThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(secondaryCorePoolSize);
        executor.setMaxPoolSize(secondaryMaxPoolSize);
        executor.setQueueCapacity(secondaryQueueCapacity);
        executor.setThreadNamePrefix("secondaryThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
