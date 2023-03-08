package com.arthor.core.pipeline.store;

import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.common.enumeration.PipelineTypeEnum;

import java.io.Serializable;

/**
 * <p>
 * 流水线,暂时没有区分开发,目前定义通过不同的Dockerfile进行不同环境参数的镜像构建
 * </p>
 *
 * @author 
 * @since 2022-12-01
 */
public class PipelineDO implements Serializable {

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
