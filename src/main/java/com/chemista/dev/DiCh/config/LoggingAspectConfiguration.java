package com.chemista.dev.DiCh.config;

import com.chemista.dev.DiCh.aop.logging.LoggingAspect;

import com.chemista.dev.DiCh.framework.config.ServletTunerConstants;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(ServletTunerConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
