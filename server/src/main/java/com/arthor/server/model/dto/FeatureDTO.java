package com.arthor.server.model.dto;


import com.arthor.core.common.enumeration.RepositoryTypeEnum;
import com.arthor.core.feature.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 仓库类型
     */
    private RepositoryTypeEnum repositoryType;
    /**
     * 仓库地址
     */
    private String repositoryUrl;
    /**
     * 分支
     */
    private String branch;

    public static FeatureDTO valueOf(Feature feature) {
        if (Objects.isNull(feature)) {
            return null;
        }

        return FeatureDTO.builder().id(feature.getId())
                .name(feature.getName()).applicationId(feature.getApplicationId()).applicationName(feature.getApplicationName())
                .repositoryType(feature.getRepositoryType()).repositoryUrl(feature.getRepositoryUrl())
                .branch(feature.getBranch()).build();
    }

}
