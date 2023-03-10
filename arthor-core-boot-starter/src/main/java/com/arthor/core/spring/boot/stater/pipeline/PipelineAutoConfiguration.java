package com.arthor.core.spring.boot.stater.pipeline;

import com.arthor.core.integration.pipeline.jenkins.JenkinsOpenApiService;
import com.arthor.core.integration.pipeline.jenkins.JenkinsProperties;
import com.arthor.core.integration.pipeline.service.PipelineOpenApiService;
import com.arthor.core.spring.boot.stater.properties.JenkinsAutoProperties;
import com.cdancy.jenkins.rest.JenkinsClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.arthor.core.common.utils.Assert;

@Configuration
public class PipelineAutoConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties({ JenkinsAutoProperties.class })
    @ConditionalOnProperty(value = "pipeline.jenkins.enabled", matchIfMissing = true)
    public static class JenkinsPipelineAutoConfiguration implements InitializingBean {

        private final JenkinsAutoProperties jenkinsAutoProperties;

        @Bean
        public JenkinsClient jenkinsClient() {
            String credentialsFormat = "%s:%s";
            return JenkinsClient.builder()
                    .endPoint(jenkinsAutoProperties.getEndPoint())
                    .credentials(String.format(credentialsFormat, jenkinsAutoProperties.getUsername(), jenkinsAutoProperties.getApiToken()))
                    .build();
        }

        @Bean
        @ConditionalOnMissingBean(value = PipelineOpenApiService.class)
        public PipelineOpenApiService pipelineOpenApiService(JenkinsClient jenkinsClient) {
            return new JenkinsOpenApiService(jenkinsClient, convert());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
//            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getFolderPath())
//                    , "?????????jenkins.jenkinsFolderPath");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getEndPoint())
                    , "?????????jenkins.endPoint");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getUsername())
                    , "?????????jenkins.username");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getApiToken())
                    , "?????????jenkins.apiToken");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryHost())
                    , "?????????jenkins.imageRepositoryHost");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryOrg())
                    , "?????????jenkins.imageRepositoryOrg");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryUsername())
                    , "?????????jenkins.imageRepositoryUsername");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryPassword())
                    , "?????????jenkins.imageRepositoryPassword");
        }

        private JenkinsProperties convert() {
            JenkinsProperties jenkinsProperties = new JenkinsProperties();
            jenkinsProperties.setFolderPath(jenkinsAutoProperties.getFolderPath());
            jenkinsProperties.setEndPoint(jenkinsAutoProperties.getEndPoint());
            jenkinsProperties.setUsername(jenkinsAutoProperties.getUsername());
            jenkinsProperties.setApiToken(jenkinsAutoProperties.getApiToken());
            jenkinsProperties.setImageRepositoryHost(jenkinsAutoProperties.getImageRepositoryHost());
            jenkinsProperties.setImageRepositoryOrg(jenkinsAutoProperties.getImageRepositoryOrg());
            jenkinsProperties.setImageRepositoryUsername(jenkinsAutoProperties.getImageRepositoryUsername());
            jenkinsProperties.setImageRepositoryPassword(jenkinsAutoProperties.getImageRepositoryPassword());
            return jenkinsProperties;
        }
    }


}
