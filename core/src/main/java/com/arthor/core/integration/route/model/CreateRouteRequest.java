package com.arthor.core.integration.route.model;


import com.arthor.core.integration.route.apisix.ResourceDTO;

import java.util.List;

public class CreateRouteRequest {

    private String routeId;
    /**
     * 路由名称
     */
    private String name;
    /**
     * host
     */
    private String host;
    /**
     * routePath
     */
    private String routePath;
    /**
     * 转发正则
     */
    private String regularExpression;
    /**
     * 需要路由的资源
     */
    private List<ResourceDTO> resources;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }
    public List<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDTO> resources) {
        this.resources = resources;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }
}
