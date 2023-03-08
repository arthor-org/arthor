package com.arthor.core.application.store;

public class ApplicationDO {

    /**
     * id
     */
    private Long id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 流水线id
     */
    private Long pipelineId;

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

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

}
