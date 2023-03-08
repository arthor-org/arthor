package com.arthor.core.pipeline;

import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class DefaultPipeline implements Pipeline {

    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String jobName;
    /**
     * 名称
     */
    private PipelineTypeEnum type;
    /**
     * 状态
     */
    private PipelineStatusEnum status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public PipelineTypeEnum getType() {
        return type;
    }

    public void setType(PipelineTypeEnum type) {
        this.type = type;
    }

    public PipelineStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PipelineStatusEnum status) {
        this.status = status;
    }

}
