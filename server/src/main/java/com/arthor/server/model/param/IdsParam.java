package com.arthor.server.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class IdsParam {

    @Size(message = "ids不能为空,并且最多5个", max = 5, min = 1)
    @NotNull(message = "ids不能为空")
    private List<Long> ids;

}
