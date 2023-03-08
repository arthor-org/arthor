package com.arthor.server.model.param.build;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildParam {

    /**
     * 功能id
     */
    @NotNull(message = "功能id不能为空")
    private Long featureId;
    /**
     * 环境id
     */
    @NotNull(message = "环境id不能为空")
    private Long envId;

}
