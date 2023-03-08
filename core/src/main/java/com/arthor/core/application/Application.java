package com.arthor.core.application;

public interface Application {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Long getPipelineId();

    void setPipelineId(Long pipelineId);
    
}
