package com.github.iweinzierl.moviedatabase.backend.config;

import com.github.iweinzierl.moviedatabase.backend.config.logging.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingFilterConfig.class);

    @Bean
    public LoggingFilter loggingFilter() {
        LOG.info("Setup LoggingFilter for HTTP requests");
        return new LoggingFilter();
    }
}
