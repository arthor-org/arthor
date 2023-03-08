package com.arthor.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${cors.pathPattern:/**}")
    private String pathPattern;

    @Value("${cors.origins:*}")
    private String origins;

    @Value("${cors.allowCredentials:true}")
    private boolean allowCredentials;

    @Value("${cors.methods:GET,POST,PUT,DELETE}")
    private String[] methods;

    @Value("${cors.maxAge:3600}")
    private long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(pathPattern).allowedOrigins(origins).allowCredentials(allowCredentials)
                .allowedMethods(methods).maxAge(maxAge);
    }

}

