package com.arthor.server.facade;

import com.arthor.server.model.dto.ApplicationDTO;
import com.arthor.server.model.param.application.ListApplicationParam;
import com.arthor.server.model.param.application.CreateApplicationParam;

import java.util.List;

public interface ApplicationFacade {

    /**
     * 应用保存
     *
     * @param param
     * @return
     */
    Boolean createApplication(CreateApplicationParam param);
    /**
     * 应用列表
     *
     * @param param
     * @return
     */
    List<ApplicationDTO> listApplication(ListApplicationParam param);

    void checkPipelineInfo();

}
