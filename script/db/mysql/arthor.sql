SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_application
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '应用主键',
  `name` varchar(255) NOT NULL COMMENT '应用名称',
  `description` varchar(255) NOT NULL COMMENT '应用描述',
  `pipeline_id` bigint(20) NOT NULL COMMENT '流水线id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- ----------------------------
-- Table structure for t_build_record
-- ----------------------------
DROP TABLE IF EXISTS `t_build_record`;
CREATE TABLE `t_build_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '构建记录主键',
  `application_id` bigint(20) NOT NULL COMMENT '应用主键',
  `application_name` varchar(255) NOT NULL COMMENT '应用名称',
  `feature_id` bigint(20) NOT NULL COMMENT '功能主键',
  `feature_name` varchar(255) NOT NULL COMMENT '功能名称',
  `commit_id` varchar(255) DEFAULT NULL COMMENT '本次构建对应的提交id',
  `image_id` varchar(255) DEFAULT NULL COMMENT '构建完成的镜像id',
  `build_number` varchar(255) NOT NULL COMMENT '构建编号',
  `job_name` varchar(255) NOT NULL COMMENT '流水线名称',
  `env_id` bigint(20) NOT NULL COMMENT '环境id',
  `status` varchar(255) NOT NULL COMMENT '构建状态「BUILDING构建中,SUCCESS构建成功,FAILURE构建失败」',
  `number_of_check` int(11) NOT NULL COMMENT '检查次数「超过限制则直接失败」',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '构建完成时间',
  PRIMARY KEY (`id`),
  KEY `application_id_feature_id` (`application_id`,`feature_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='构建记录表';

-- ----------------------------
-- Table structure for t_counter
-- ----------------------------
DROP TABLE IF EXISTS `t_counter`;
CREATE TABLE `t_counter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自增计数器';

-- ----------------------------
-- Table structure for t_deploy_record
-- ----------------------------
DROP TABLE IF EXISTS `t_deploy_record`;
CREATE TABLE `t_deploy_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operate` varchar(255) NOT NULL COMMENT '操作类型[REPLACED,DEPLOY,UPDATE,DELETE,PROMOTE,ROLLBACK]',
  `target_id` bigint(20) NOT NULL COMMENT '操作部署',
  `target_snapshot` json NOT NULL COMMENT '部署快照',
  `related_id` bigint(20) DEFAULT NULL COMMENT '关联部署',
  `related_snapshot` json DEFAULT NULL COMMENT '关联快照',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_deployment
-- ----------------------------
DROP TABLE IF EXISTS `t_deployment`;
CREATE TABLE `t_deployment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `application_id` bigint(20) NOT NULL COMMENT '应用主键',
  `application_name` varchar(255) NOT NULL COMMENT '应用名称',
  `feature_id` bigint(20) NOT NULL COMMENT '功能主键',
  `build_record_id` bigint(20) NOT NULL COMMENT '构建记录主键',
  `env` varchar(255) NOT NULL COMMENT '环境',
  `namespace` varchar(255) NOT NULL COMMENT '命名空间',
  `host` varchar(255) NOT NULL COMMENT 'host',
  `deploy_mode` varchar(255) NOT NULL COMMENT '部署模式',
  `related_deployment_id` bigint(20) DEFAULT NULL COMMENT '关联的部署id',
  `canary` int(11) NOT NULL COMMENT 'canary标识',
  `symbol` varchar(255) NOT NULL COMMENT '部署的限定符',
  `tick` bigint(20) NOT NULL COMMENT '逻辑时钟',
  `external` int(11) NOT NULL COMMENT '外部服务标识「是否需要路由」',
  `status` varchar(255) NOT NULL COMMENT '整体的状态',
  `deployment_name` varchar(255) NOT NULL COMMENT '部署name',
  `deployment_image_id` varchar(255) NOT NULL COMMENT '镜像id',
  `deployment_image_pull_policy` varchar(255) NOT NULL COMMENT '镜像拉取策略',
  `deployment_replicas` int(20) NOT NULL COMMENT '副本数',
  `deployment_updated_replicas` int(20) DEFAULT NULL COMMENT '已更新副本数',
  `deployment_ready_replicas` int(20) DEFAULT NULL COMMENT '准备好的副本数',
  `deployment_available_replicas` int(20) DEFAULT NULL COMMENT '可用副本数',
  `deployment_unavailable_replicas` int(20) DEFAULT NULL COMMENT '不可用副本数',
  `deployment_pod_label_name` varchar(255) NOT NULL COMMENT 'pod标签名称',
  `deployment_container_name` varchar(255) NOT NULL COMMENT '容器名称',
  `deployment_container_port` varchar(255) NOT NULL COMMENT '容器端口',
  `deployment_ext` json DEFAULT NULL COMMENT '部署扩展',
  `pods` json DEFAULT NULL COMMENT 'pod数组',
  `deployment_status` varchar(255) NOT NULL,
  `service_name` varchar(255) DEFAULT NULL COMMENT '服务name',
  `service_ext` json DEFAULT NULL COMMENT '服务扩展',
  `service_status` varchar(255) DEFAULT NULL,
  `route_name` varchar(255) DEFAULT NULL COMMENT '路由名称',
  `route_path` varchar(255) DEFAULT NULL COMMENT '路径「部署限定符」',
  `route_ext` json DEFAULT NULL COMMENT '路由扩展',
  `route_status` varchar(255) DEFAULT NULL COMMENT '路由状态',
  `canary_type` varchar(255) DEFAULT NULL COMMENT 'canary类型',
  `canary_value` varchar(255) DEFAULT NULL COMMENT 'canary值',
  `lifecycle` varchar(255) NOT NULL COMMENT '生命周期',
  `shutdown_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `deployment_name` (`deployment_name`),
  KEY `application_id` (`application_id`,`feature_id`,`build_record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部署对象';

-- ----------------------------
-- Table structure for t_env
-- ----------------------------
DROP TABLE IF EXISTS `t_env`;
CREATE TABLE `t_env` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '环境名称',
  `description` varchar(255) NOT NULL COMMENT '环境描述',
  `host` varchar(255) NOT NULL COMMENT '环境host',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境';

-- ----------------------------
-- Table structure for t_feature
-- ----------------------------
DROP TABLE IF EXISTS `t_feature`;
CREATE TABLE `t_feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '功能主键',
  `name` varchar(255) NOT NULL COMMENT '功能名称',
  `application_name` varchar(255) NOT NULL COMMENT '应用名称',
  `application_id` bigint(20) NOT NULL COMMENT '应用主键',
  `repository_type` varchar(255) NOT NULL COMMENT '仓库类型',
  `repository_url` varchar(255) NOT NULL COMMENT '仓库地址',
  `branch` varchar(255) NOT NULL COMMENT '分支',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- ----------------------------
-- Table structure for t_lock
-- ----------------------------
DROP TABLE IF EXISTS `t_lock`;
CREATE TABLE `t_lock` (
  `lock_entry` varchar(255) NOT NULL COMMENT '锁',
  PRIMARY KEY (`lock_entry`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='锁表';

-- ----------------------------
-- Table structure for t_pipeline
-- ----------------------------
DROP TABLE IF EXISTS `t_pipeline`;
CREATE TABLE `t_pipeline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_name` varchar(255) NOT NULL COMMENT '名称',
  `type` varchar(255) NOT NULL COMMENT '流水线类型',
  `status` varchar(255) NOT NULL COMMENT '流水线状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`job_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水线';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
