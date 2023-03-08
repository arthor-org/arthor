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
@ConfigurationProperties(prefix = "kubernetes")
public class KubernetesAutoProperties {

    private Boolean debugging;
    private String defaultImagePullPolicy;
    private String imagePullSecrets;
    private Integer defaultPodPort;
    private Integer defaultServicePort;
    private Integer gracePeriodSeconds;
    private String defaultPathType;

    public Boolean getDebugging() {
        return debugging;
    }

    public void setDebugging(Boolean debugging) {
        this.debugging = debugging;
    }

    public String getDefaultImagePullPolicy() {
        return defaultImagePullPolicy;
    }

    public void setDefaultImagePullPolicy(String defaultImagePullPolicy) {
        this.defaultImagePullPolicy = defaultImagePullPolicy;
    }

    public String getImagePullSecrets() {
        return imagePullSecrets;
    }

    public void setImagePullSecrets(String imagePullSecrets) {
        this.imagePullSecrets = imagePullSecrets;
    }

    public Integer getDefaultPodPort() {
        return defaultPodPort;
    }

    public void setDefaultPodPort(Integer defaultPodPort) {
        this.defaultPodPort = defaultPodPort;
    }

    public Integer getDefaultServicePort() {
        return defaultServicePort;
    }

    public void setDefaultServicePort(Integer defaultServicePort) {
        this.defaultServicePort = defaultServicePort;
    }

    public Integer getGracePeriodSeconds() {
        return gracePeriodSeconds;
    }

    public void setGracePeriodSeconds(Integer gracePeriodSeconds) {
        this.gracePeriodSeconds = gracePeriodSeconds;
    }

    public String getDefaultPathType() {
        return defaultPathType;
    }

    public void setDefaultPathType(String defaultPathType) {
        this.defaultPathType = defaultPathType;
    }
}
