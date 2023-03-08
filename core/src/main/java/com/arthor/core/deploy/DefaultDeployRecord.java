package com.arthor.core.deploy;

import com.arthor.core.common.enumeration.OperateEnum;

import java.time.LocalDateTime;

public class DefaultDeployRecord implements DeployRecord {

    /**
     * id
     */
    private Long id;
    /**
     * 操作类型
     */
    private OperateEnum operate;
    /**
     * 操作部署id
     */
    private Long targetId;
    /**
     * 操作部署快照
     */
    private String targetSnapshot;
    /**
     * 关联部署id
     */
    private Long relatedId;
    /**
     * 关联部署快照
     */
    private String relatedSnapshot;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperateEnum getOperate() {
        return operate;
    }

    public void setOperate(OperateEnum operate) {
        this.operate = operate;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetSnapshot() {
        return targetSnapshot;
    }

    public void setTargetSnapshot(String targetSnapshot) {
        this.targetSnapshot = targetSnapshot;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public String getRelatedSnapshot() {
        return relatedSnapshot;
    }

    public void setRelatedSnapshot(String relatedSnapshot) {
        this.relatedSnapshot = relatedSnapshot;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}
