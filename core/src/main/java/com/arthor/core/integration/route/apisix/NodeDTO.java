package com.arthor.core.integration.route.apisix;

public class NodeDTO {

    /**
     * host
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 权重
     */
    private Integer weight;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
