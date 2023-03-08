package com.arthor.core.spring.boot.stater.kubernetes;

import com.arthor.core.common.utils.Assert;
import com.arthor.core.integration.kubernetes.KubernetesProperties;
import com.arthor.core.integration.kubernetes.service.DefaultKubernetesOpenApiService;
import com.arthor.core.spring.boot.stater.properties.KubernetesAutoProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class KubernetesAutoConfiguration {
    
    @Configuration
    @RequiredArgsConstructor
    @ConditionalOnProperty(value = "kubernetes.default.enabled", matchIfMissing = true)
    @EnableConfigurationProperties({ KubernetesAutoProperties.class })
    public static class DefaultKubernetesConfiguration implements InitializingBean {
        
        private final KubernetesAutoProperties kubernetesAutoProperties;

        @Bean
        public DefaultKubernetesOpenApiService kubernetesOpenApiService() throws IOException {
            return new DefaultKubernetesOpenApiService(convert());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(kubernetesAutoProperties.getDebugging()
                    , "请配置kubernetes.debugging");
            Assert.isTrue(StringUtils.isNotBlank(kubernetesAutoProperties.getDefaultImagePullPolicy())
                    , "请配置kubernetes.defaultImagePullPolicy");
            Assert.isTrue(StringUtils.isNotBlank(kubernetesAutoProperties.getImagePullSecrets())
                    , "请配置kubernetes.imagePullSecrets");
            Assert.notNull(kubernetesAutoProperties.getDefaultPodPort()
                    , "请配置kubernetes.defaultPodPort");
            Assert.notNull(kubernetesAutoProperties.getDefaultServicePort()
                    , "请配置kubernetes.defaultServicePort");
            Assert.notNull(kubernetesAutoProperties.getGracePeriodSeconds()
                    , "请配置kubernetes.gracePeriodSeconds");
            Assert.notNull(kubernetesAutoProperties.getDefaultPathType()
                    , "请配置kubernetes.defaultPathType");
        }

        private KubernetesProperties convert() {
            KubernetesProperties kubernetesProperties = new KubernetesProperties();
            kubernetesProperties.setDebugging(kubernetesAutoProperties.getDebugging());
            kubernetesProperties.setDefaultImagePullPolicy(kubernetesAutoProperties.getDefaultImagePullPolicy());
            kubernetesProperties.setImagePullSecrets(kubernetesAutoProperties.getImagePullSecrets());
            kubernetesProperties.setDefaultPodPort(kubernetesAutoProperties.getDefaultPodPort());
            kubernetesProperties.setDefaultServicePort(kubernetesAutoProperties.getDefaultServicePort());
            kubernetesProperties.setGracePeriodSeconds(kubernetesAutoProperties.getGracePeriodSeconds());
            kubernetesProperties.setDefaultPathType(kubernetesAutoProperties.getDefaultPathType());
            return kubernetesProperties;
        }

    }
    
}
