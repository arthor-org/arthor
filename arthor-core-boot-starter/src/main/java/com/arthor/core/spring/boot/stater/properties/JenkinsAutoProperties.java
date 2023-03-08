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
@ConfigurationProperties(prefix = "jenkins")
public class JenkinsAutoProperties {

    private String folderPath;
    private String endPoint;
    private String username;
    private String apiToken;

    private String imageRepositoryHost;
    private String imageRepositoryOrg;
    private String imageRepositoryUsername;
    private String imageRepositoryPassword;


}
