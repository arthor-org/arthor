package com.arthor.core.spring.boot.stater.properties;

import com.arthor.core.common.enumeration.DeployModeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "command")
public class CommandAutoProperties {

    private DeployModeEnum deployMode;

}
