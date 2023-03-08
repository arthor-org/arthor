package com.arthor.core.deploy;

import com.arthor.core.common.enumeration.OperateEnum;

import java.time.LocalDateTime;

public interface DeployRecord {

    Long getId();

    void setId(Long id);
    
    OperateEnum getOperate();

    void setOperate(OperateEnum operate);

    Long getTargetId();

    void setTargetId(Long targetId);

    String getTargetSnapshot();

    void setTargetSnapshot(String targetSnapshot);

    Long getRelatedId();

    void setRelatedId(Long relatedId);

    String getRelatedSnapshot();

    void setRelatedSnapshot(String relatedSnapshot);

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

}
