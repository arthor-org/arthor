package com.arthor.core.application;

public class DefaultApplication implements Application {

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getPipelineId() {
        return pipelineId;
    }

    @Override
    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }


}
