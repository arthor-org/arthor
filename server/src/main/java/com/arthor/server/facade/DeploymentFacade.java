package com.arthor.server.facade;

import com.arthor.server.model.dto.DeploymentDTO;
import com.arthor.server.model.param.IdParam;
import com.arthor.server.model.param.ListDeploymentParam;
import com.arthor.server.model.param.deploy.*;

import java.util.List;

public interface DeploymentFacade {

    /**
     * 部署,如存在相同symbol,则进行替换
     *
     * @param param
     * @return
     */
    Boolean deploy(DeployParam param);

    /**
     * 灰度部署,允许多灰度部署,如果灰度标识一致的话,就需要进行替换,需要配合提升
     *
     * @param param

     * @return
     */
    Boolean gray(GrayParam param);

    /**
     * 下线
     *
     * @param param
     * @return
     */
    Boolean delete(IdParam param);

    /**
     * 提升
     * 通过id获取对应部署,尝试进行提升
     * 替换stable
     * 需要处理剩余资源,这步很重要「删除」
     *
     * @param param
     * @return
     */
    Boolean promote(IdParam param);

    /**
     * 蓝绿部署
     *
     * @param param
     * @return
     */
    Boolean blueGreen(BlueGreenParam param);

    /**
     * 蓝绿部署比例修改
     *
     * @param param
     * @return
     */
    Boolean updateBlueGreen(UpdateBlueGreenParam param);

    /**
     * 回滚
     * 回滚创建的部署与目标部署一致,不存在需要清理的资源.
     * 具体是否需要清理资源需要根据目标部署来决定
     *
     * @param param

     * @return
     */
    Boolean rollback(RollbackParam param);

    /**
     * 扩缩容
     *
     * @param param
     * @return
     */
    Boolean scale(ScaleParam param);

    /**
     * 列表
     *
     * @param param
     * @return
     */
    List<DeploymentDTO> listByCondition(ListDeploymentParam param);

    /**
     * 检查k8s逻辑
     */
    void check();

}
