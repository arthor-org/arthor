package com.arthor.server.configuration;

import com.arthor.server.mvc.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${mvc.authen.excludes}")
    private List<String> excludes;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(authenticationInterceptor())
                .excludePathPatterns(excludes);
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}
