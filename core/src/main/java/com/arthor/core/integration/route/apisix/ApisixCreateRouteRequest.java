package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class ApisixCreateRouteRequest {

    /**
     * 路由名称
     */
    private String name;
    /**
     * uri
     */
    private String uri;
    /**
     * host
     */
    private String host;
    /**
     * 支持的方法
     */
    private List<String> methods;
    /**
     * 插件
     */
    private JSONObject plugins;
    /**
     * 上游
     */
    private UpstreamDTO upstream;
    /**
     * 是否启用
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public JSONObject getPlugins() {
        return plugins;
    }

    public void setPlugins(JSONObject plugins) {
        this.plugins = plugins;
    }

    public UpstreamDTO getUpstream() {
        return upstream;
    }

    public void setUpstream(UpstreamDTO upstream) {
        this.upstream = upstream;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
