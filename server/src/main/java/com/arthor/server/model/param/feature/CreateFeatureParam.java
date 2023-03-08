package com.arthor.server.model.param.feature;

import com.arthor.core.common.enumeration.RepositoryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeatureParam {
    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    private Long applicationId;
    /**
     * 功能名称
     */
    @NotBlank(message = "功能名称不能为空")
    private String featureName;
    /**
     * 仓库类型
     */
    @NotNull(message = "仓库类型不能为空")
    private RepositoryTypeEnum repositoryTpe;
    /**
     * 仓库地址
     */
    @NotBlank(message = "仓库地址不能为空")
    private String repositoryUrl;
    /**
     * 分支
     */
    @NotBlank(message = "分支不能为空")
    private String branch;

}
