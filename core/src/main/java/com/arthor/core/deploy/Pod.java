package com.arthor.core.deploy;

public interface Pod {
    String getName();

    void setName(String name);

    String getIp();

    void setIp(String ip);

    Integer getPort();

    void setPort(Integer port);

}
