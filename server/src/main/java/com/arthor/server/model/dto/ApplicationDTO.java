package com.arthor.server.model.dto;

import com.arthor.core.application.Application;
import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.common.enumeration.PipelineTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 流水线id
     */
    private Long pipelineId;
    /**
     * 流水线类型
     */
    private PipelineTypeEnum pipelineType;
    /**
     * 流水线状态
     */
    private PipelineStatusEnum pipelineStatus;

    public static ApplicationDTO valueOf(Application application) {
        if (Objects.isNull(application)) {
            return null;
        }

        return ApplicationDTO.builder().id(application.getId()).name(application.getName())
                .description(application.getDescription()).pipelineId(application.getPipelineId()).build();
    }

}
