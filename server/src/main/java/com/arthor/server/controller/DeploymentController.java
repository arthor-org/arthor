package com.arthor.server.controller;

import com.arthor.server.common.R;
import com.arthor.server.facade.DeploymentFacade;
import com.arthor.server.model.dto.DeploymentDTO;
import com.arthor.server.model.param.IdParam;
import com.arthor.server.model.param.ListDeploymentParam;
import com.arthor.server.model.param.deploy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 部署控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("deployment")
public class DeploymentController {

    private final DeploymentFacade deploymentFacade;

    /**
     * 部署,存在symbol则替换
     *
     * @param param
     * @return
     */
    @PostMapping("deploy")
    public R<Boolean> deploy(@Valid @RequestBody DeployParam param) {
        return R.success(deploymentFacade.deploy(param));
    }

    /**
     * 灰度部署
     *
     * @param param
     * @return
     */
    @PostMapping("gray")
    public R<Boolean> gray(@Valid @RequestBody GrayParam param) {
        return R.success(deploymentFacade.gray(param));
    }

    /**
     * 提升
     *
     * @param param
     * @return
     */
    @PostMapping("promote")
    public R<Boolean> promote(@Valid @RequestBody IdParam param) {
        return R.success(deploymentFacade.promote(param));
    }

    /**
     * 蓝绿部署
     *
     * @param param
     * @return
     */
    @PostMapping("blue-green")
    public R<Boolean> blueGreen(@Valid @RequestBody BlueGreenParam param) {
        return R.success(deploymentFacade.blueGreen(param));
    }

    /**
     * 蓝绿部署比例修改
     *
     * @param param
     * @return
     */
    @PostMapping("update-blue-green")
    public R<Boolean> updateBlueGreen(@Valid @RequestBody UpdateBlueGreenParam param) {
        return R.success(deploymentFacade.updateBlueGreen(param));
    }

    /**
     * 下线
     *
     * @param param
     * @return
     */
    @PostMapping("delete")
    public R<Boolean> delete(@Valid @RequestBody IdParam param) {
        return R.success(deploymentFacade.delete(param));
    }

    /**
     * 回滚
     *
     * @param param
     * @return
     */
    @PostMapping("rollback")
    public R<Boolean> rollback(@Valid @RequestBody RollbackParam param) {
        return R.success(deploymentFacade.rollback(param));
    }

    /**
     * 扩缩容
     *
     * @param param
     * @return
     */
    @PostMapping("scale")
    public R<Boolean> scale(@Valid @RequestBody ScaleParam param) {
        return R.success(deploymentFacade.scale(param));
    }

    /**
     * 列表
     *
     * @param param
     * @return
     */
    @PostMapping("list")
    public R<List<DeploymentDTO>> list(@Valid @RequestBody ListDeploymentParam param) {
        return R.success(deploymentFacade.listByCondition(param));
    }

}
