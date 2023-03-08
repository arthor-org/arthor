package com.arthor.core.build;

import com.arthor.core.common.enumeration.BuildRecordStatusEnum;

import java.time.LocalDateTime;

public interface BuildRecord {


    Long getId();

    void setId(Long id);

    Long getApplicationId();

    void setApplicationId(Long applicationId);

    Long getFeatureId();

    void setFeatureId(Long featureId);

    String getApplicationName();

    void setApplicationName(String applicationName);

    String getFeatureName();

    void setFeatureName(String featureName);

    String getCommitId();

    void setCommitId(String commitId);

    String getImageId();

    void setImageId(String imageId);

    String getBuildNumber();

    void setBuildNumber(String buildNumber);

    String getJobName();

    void setJobName(String jobName);

    Long getEnvId();

    void setEnvId(Long envId);

    BuildRecordStatusEnum getStatus();

    void setStatus(BuildRecordStatusEnum status);

    Integer getNumberOfCheck();

    void setNumberOfCheck(Integer numberOfCheck);

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    LocalDateTime getFinishTime();

    void setFinishTime(LocalDateTime finishTime);
}
