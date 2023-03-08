package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.integration.pipeline.model.PipelineBuildInfoDTO;

public class JenkinsPipelineBuildInfoDTO extends PipelineBuildInfoDTO {

    private boolean building;
    private String result;
    private String description;

    public JenkinsPipelineBuildInfoDTO(boolean building, String result, String description) {
        this.building = building;
        this.result = result;
        this.description = description;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
