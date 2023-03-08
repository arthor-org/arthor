package com.arthor.server.controller;

import com.arthor.server.common.R;
import com.arthor.server.facade.FeatureFacade;
import com.arthor.server.model.dto.FeatureDTO;
import com.arthor.server.model.param.feature.ListFeatureParam;
import com.arthor.server.model.param.feature.CreateFeatureParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 功能控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("feature")
public class FeatureController {

    private final FeatureFacade featureFacade;

    /**
     * 新增功能
     *
     * @param param
     * @return
     */
    @PostMapping
    public R<Boolean> createFeature(@Valid @RequestBody CreateFeatureParam param) {
        return R.success(featureFacade.createFeature(param));
    }

    /**
     * 功能列表
     *
     * @param param
     * @return
     */
    @PostMapping("list")
    public R<List<FeatureDTO>> list(@Valid @RequestBody ListFeatureParam param) {
        return R.success(featureFacade.listByCondition(param));
    }

}
