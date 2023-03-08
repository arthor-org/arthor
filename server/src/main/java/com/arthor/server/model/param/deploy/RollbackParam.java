package com.arthor.server.model.param.deploy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackParam {

    /**
     * 目标部署id
     */
    @NotNull(message = "目标部署id不能为空")
    private Long targetId;

}
