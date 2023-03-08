package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class RouteDetailDTO {

    private String id;
    private UpstreamDTO upstream;
    private String uri;
    private List<String> methods;
    private JSONObject plugins;
    private String name;
    private String host;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpstreamDTO getUpstream() {
        return upstream;
    }

    public void setUpstream(UpstreamDTO upstream) {
        this.upstream = upstream;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
