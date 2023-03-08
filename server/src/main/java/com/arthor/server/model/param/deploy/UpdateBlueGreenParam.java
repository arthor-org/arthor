package com.arthor.server.model.param.deploy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlueGreenParam {

    /**
     * 绿部署id
     */
    @NotNull(message = "绿部署id不能为空")
    private Long greenDeploymentId;
    /**
     * 蓝绿权重
     */
    @NotNull(message = "权重不能为空")
    private String weight;

}
