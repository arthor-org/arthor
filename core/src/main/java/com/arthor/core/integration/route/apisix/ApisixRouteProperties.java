package com.arthor.core.integration.route.apisix;

import com.arthor.core.integration.route.RouteProperties;

public class ApisixRouteProperties implements RouteProperties {

    private String scheme;
    private String authKey;
    private String host;
    private String token;
    private Integer port;

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getAuthKey() {
        return authKey;
    }

    @Override
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
