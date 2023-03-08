package com.arthor.core.spring.boot.stater.metadata;

import com.arthor.core.common.utils.Assert;
import com.arthor.core.registry.nacos.NacosRegistryOpenApiService;
import com.arthor.core.registry.nacos.NacosProperties;
import com.arthor.core.registry.service.RegistryOpenApiService;
import com.arthor.core.spring.boot.stater.properties.NacosAutoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetadataAutoConfiguration {

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties({ NacosAutoProperties.class })
    @ConditionalOnProperty(value = "metadata.nacos.enabled", matchIfMissing = true)
    public static class NacosMetadataAutoConfiguration implements InitializingBean {

        private final NacosAutoProperties nacosAutoProperties;

        @Bean
        public RegistryOpenApiService nacosMetadataOpenApiService() {
            return new NacosRegistryOpenApiService(convert());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(nacosAutoProperties.getScheme()
                    , "请配置nacos.scheme");
            Assert.notNull(nacosAutoProperties.getHost()
                    , "请配置nacos.host");
            Assert.notNull(nacosAutoProperties.getPort()
                    , "请配置nacos.port");
        }

        private NacosProperties convert() {
            NacosProperties nacosProperties = new NacosProperties();
            nacosProperties.setScheme(nacosAutoProperties.getScheme());
            nacosProperties.setHost(nacosAutoProperties.getHost());
            nacosProperties.setPort(nacosAutoProperties.getPort());
            return nacosProperties;
        }

    }
}
