package com.arthor.server.model.dto;

import com.arthor.core.build.BuildRecord;
import com.arthor.core.common.enumeration.BuildRecordStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildRecordDTO {

    /**
     * 构建记录主键
     */
    private Long id;
    /**
     * 应用主键
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 功能主键
     */
    private Long featureId;
    /**
     * 功能名称
     */
    private String featureName;
    /**
     * 本次构建对应的提交id
     */
    private String commitId;
    /**
     * 构建完成的镜像id
     */
    private String imageId;
    /**
     * 构建编号
     */
    private String buildNumber;
    /**
     * 流水线名称
     */
    private String jobName;
    /**
     * 环境id
     */
    private Long envId;
    /**
     * 构建状态「BUILDING构建中,SUCCESS构建成功,FAILURE构建失败」
     */
    private BuildRecordStatusEnum status;
    /**
     * 检查次数
     */
    private Integer numberOfCheck;
    /**
     * 构建时间
     */
    private LocalDateTime createTime;
    /**
     * 构建完成时间
     */
    private LocalDateTime finishTime;
    public static BuildRecordDTO valueOf(BuildRecord entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return BuildRecordDTO.builder().id(entity.getId()).applicationId(entity.getApplicationId())
                .featureId(entity.getFeatureId()).applicationName(entity.getApplicationName())
                .featureName(entity.getFeatureName()).commitId(entity.getCommitId()).imageId(entity.getImageId())
                .buildNumber(entity.getBuildNumber()).jobName(entity.getJobName()).envId(entity.getEnvId())
                .status(entity.getStatus()).numberOfCheck(entity.getNumberOfCheck())
                .createTime(entity.getCreateTime()).finishTime(entity.getFinishTime()).build();
    }

}
