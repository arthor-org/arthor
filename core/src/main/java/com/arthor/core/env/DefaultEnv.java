package com.arthor.core.env;

public class DefaultEnv implements Env {

    /**
     * id
     */
    private Long id;
    /**
     * 环境名称
     */
    private String name;
    /**
     * 环境描述
     */
    private String description;
    /**
     * 环境host
     */
    private String host;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
