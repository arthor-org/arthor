package com.arthor.core.spring.boot.stater.route;

import com.arthor.core.common.utils.Assert;
import com.arthor.core.integration.route.apisix.ApisixRouteOpenApiService;
import com.arthor.core.integration.route.apisix.ApisixRouteProperties;
import com.arthor.core.spring.boot.stater.properties.ApisixRouteAutoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteAutoConfiguration {


    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties({ ApisixRouteAutoProperties.class })
    @ConditionalOnProperty(value = "route.apisix.enabled", matchIfMissing = true)
    public static class ApisixAutoConfiguration implements InitializingBean {
        
        private final ApisixRouteAutoProperties apisixRouteAutoProperties;

        @Bean
        public ApisixRouteOpenApiService apisixRouteOpenApiService() {
            return new ApisixRouteOpenApiService(convert());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(apisixRouteAutoProperties.getScheme()
                    , "请配置apisix.scheme");
            Assert.notNull(apisixRouteAutoProperties.getHost()
                    , "请配置apisix.host");
            Assert.notNull(apisixRouteAutoProperties.getPort()
                    , "请配置apisix.port");
            Assert.notNull(apisixRouteAutoProperties.getAuthKey()
                    , "请配置apisix.authKey");
            Assert.notNull(apisixRouteAutoProperties.getToken()
                    , "请配置apisix.token");
        }

        private ApisixRouteProperties convert() {
            ApisixRouteProperties apisixRouteProperties = new ApisixRouteProperties();
            apisixRouteProperties.setScheme(apisixRouteAutoProperties.getScheme());
            apisixRouteProperties.setHost(apisixRouteAutoProperties.getHost());
            apisixRouteProperties.setPort(apisixRouteAutoProperties.getPort());
            apisixRouteProperties.setAuthKey(apisixRouteAutoProperties.getAuthKey());
            apisixRouteProperties.setToken(apisixRouteAutoProperties.getToken());
            return apisixRouteProperties;
        }
    }
    
}
