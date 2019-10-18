package com.chemista.dev.DiCh.config;

import com.chemista.dev.DiCh.framework.async.ExceptionHandlingAsyncTaskExecutor;
import com.chemista.dev.DiCh.framework.config.ServletTunerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    private final ServletTunerProperties servletTunerProperties;

    public AsyncConfiguration(ServletTunerProperties servletTunerProperties) {
        this.servletTunerProperties = servletTunerProperties;
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(servletTunerProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(servletTunerProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(servletTunerProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("DiCh-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
