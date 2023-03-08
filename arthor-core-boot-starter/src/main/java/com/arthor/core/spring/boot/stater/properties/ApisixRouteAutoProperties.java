package com.arthor.core.spring.boot.stater.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "apisix")
public class ApisixRouteAutoProperties {

    private String scheme;
    private String authKey;
    private String token;
    private String host;
    private Integer port;

}
