package com.arthor.server.model.param.build;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveBuildParam {

    /**
     * 应用主键
     */
    private Long applicationId;
    /**
     * 功能主键
     */
    private Long featureId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 功能名称
     */
    private String featureName;
    /**
     * 构建编号
     */
    private Integer buildNumber;
    /**
     * 环境id
     */
    private Long envId;

}
