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
public class BlueGreenParam extends DeployParam {

    /**
     * 蓝绿权重,[0%, 10%, 20%, 40%, 60%, 80%, 100%]
     */
    @NotNull(message = "权重不能为空")
    private String weight;

}
