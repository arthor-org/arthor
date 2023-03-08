package com.arthor.server.model.param;

import com.arthor.core.common.enumeration.DeployStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListDeploymentParam {

    /**
     * 构建id
     */
    private Long buildId;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 部署状态
     */
    private List<DeployStatusEnum> statusList;
    /**
     * 部署限定符
     */
    private String symbol;
    /**
     * canary标识
     */
    private Boolean canary;
    /**
     * 逻辑时钟阈值
     */
    private Long tickThreshold;
}
