package com.arthor.core.spring.boot.stater.repository;

import com.arthor.core.integration.repository.gitlab.GitlabOpenApiService;
import com.arthor.core.integration.repository.gitlab.GitlabProperties;
import com.arthor.core.spring.boot.stater.properties.JenkinsAutoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryAutoConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties({ JenkinsAutoProperties.class })
    @ConditionalOnProperty(value = "pipeline.jenkins.enabled", matchIfMissing = true)
    public static class GitlabAutoConfiguration implements InitializingBean {

        private final GitlabProperties gitlabProperties;

        @Bean
        public GitlabOpenApiService gitlabOpenApiService(GitlabProperties gitlabProperties) {
            return new GitlabOpenApiService(gitlabProperties);
        }

        @Override
        public void afterPropertiesSet() throws Exception {
        }

    }

}
