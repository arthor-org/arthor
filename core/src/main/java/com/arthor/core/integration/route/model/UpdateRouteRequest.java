package com.arthor.core.integration.route.model;

public abstract class UpdateRouteRequest {

    private String id;

    public abstract String subPath();

    public abstract String content();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
