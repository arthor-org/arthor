package com.arthor.server.controller;

import com.arthor.server.common.R;
import com.arthor.server.facade.ApplicationFacade;
import com.arthor.server.model.dto.ApplicationDTO;
import com.arthor.server.model.param.application.ListApplicationParam;
import com.arthor.server.model.param.application.CreateApplicationParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 应用控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("application")
public class ApplicationController {
    private final ApplicationFacade applicationFacade;

    /**
     * 新增应用
     *
     * @param param
     * @return
     */
    @PostMapping
    public R<Boolean> createApplication(@Valid @RequestBody CreateApplicationParam param) {
        return R.success(applicationFacade.createApplication(param));
    }
    /**
     * 列表
     *
     * @param param
     * @return
     */
    @PostMapping("list")
    public R<List<ApplicationDTO>> listApplication(@RequestBody ListApplicationParam param) {
        return R.success(applicationFacade.listApplication(param));
    }

}
