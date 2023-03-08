package com.arthor.server.model.param.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationParam {
    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String name;
    /**
     * 应用描述
     */
    @NotBlank(message = "应用描述不能为空")
    private String description;
}
