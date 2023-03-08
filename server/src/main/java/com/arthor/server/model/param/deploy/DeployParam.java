package com.arthor.server.model.param.deploy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeployParam {

    /**
     * 构建id
     */
    @NotNull(message = "构建id不能为空")
    private Long buildRecordId;
    /**
     * 副本数
     */
    @NotNull(message = "副本数不能为空")
    private Integer replicas;
    /**
     * 部署限定符
     */
    private String symbol;
    /**
     * 外部服务标识
     */
    @NotNull(message = "外部服务标识不能为空")
    private Boolean external;

}
