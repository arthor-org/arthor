package com.arthor.server.controller;

import com.arthor.server.common.R;
import com.arthor.server.facade.BuildFacade;
import com.arthor.server.model.dto.BuildRecordDTO;
import com.arthor.server.model.param.build.BuildParam;
import com.arthor.server.model.param.build.ListBuildRecordParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 构建控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("build")
public class BuildController {

    private final BuildFacade buildFacade;

    /**
     * 构建
     * 注意在构建的时候,就需要区分环境了,至少不同环境的运行参数可能不同三,例如nacos不同
     *
     * @param param
     * @return
     */
    @PostMapping
    public R<Boolean> build(@Valid @RequestBody BuildParam param) {
        return R.success(buildFacade.build(param));
    }

    /**
     * 构建记录列表
     *
     * @param param
     * @return
     */
    @PostMapping("list")
    public R<BuildRecordDTO> list(@Valid @RequestBody ListBuildRecordParam param) {
        return R.success(buildFacade.listByCondition(param));
    }

}
