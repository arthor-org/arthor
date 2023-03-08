package com.arthor.core.integration.route;

public interface RouteProperties {
    String getScheme();

    void setScheme(String scheme);

    String getHost();

    void setHost(String host);

    Integer getPort();

    void setPort(Integer port);

    void setAuthKey(String key);

    String getAuthKey();

    void setToken(String token);

    String getToken();

}
