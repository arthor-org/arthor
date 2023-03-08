package com.arthor.server.model.param.feature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListFeatureParam {
    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    private Long applicationId;

}
