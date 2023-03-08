package com.arthor.server.model.param.deploy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GrayParam extends DeployParam {

    /**
     * 灰度匹配headerValue,对于相同symbol来说,这是替换的操作
     */
    @NotBlank(message = "灰度匹配headerValue不能为空")
    private String grayHeaderValue;

}
