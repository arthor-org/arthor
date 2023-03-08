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
//                    , "请配置jenkins.jenkinsFolderPath");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getEndPoint())
                    , "请配置jenkins.endPoint");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getUsername())
                    , "请配置jenkins.username");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getApiToken())
                    , "请配置jenkins.apiToken");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryHost())
                    , "请配置jenkins.imageRepositoryHost");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryOrg())
                    , "请配置jenkins.imageRepositoryOrg");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryUsername())
                    , "请配置jenkins.imageRepositoryUsername");
            Assert.isTrue(StringUtils.isNotBlank(jenkinsAutoProperties.getImageRepositoryPassword())
                    , "请配置jenkins.imageRepositoryPassword");
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
