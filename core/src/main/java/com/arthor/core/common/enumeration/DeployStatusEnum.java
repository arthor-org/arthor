package com.arthor.core.common.enumeration;

/**
 * 部署的状态
 * 中间态
 * DEPLOYING, REPLACING, PROMOTING, DELETING
 * 最终态
 * DEPLOYED, WITHDRAWN
 */
public enum DeployStatusEnum {

    /**
     * 部署中
     */
    DEPLOYING,
    /**
     * 替换中
     */
    REPLACING,
    /**
     * 删除中
     */
    DELETING,
    /**
     * 部署成功,生效中
     */
    DEPLOYED,
    /**
     * 撤销,未生效
     */
    WITHDRAWN,
}
