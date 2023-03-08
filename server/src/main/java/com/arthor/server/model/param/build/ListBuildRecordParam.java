package com.arthor.server.model.param.build;

import com.arthor.core.common.enumeration.BuildRecordStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListBuildRecordParam {

    /**
     * 环境id
     */
    private Long envId;
    /**
     * 功能id
     */
    private Long featureId;
    /**
     * 构建记录状态
     */
    private BuildRecordStatusEnum status;

}
