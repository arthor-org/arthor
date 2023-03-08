package com.arthor.server.model.param.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListApplicationParam {

    /**
     * 应用名称
     */
    private String name;

}
