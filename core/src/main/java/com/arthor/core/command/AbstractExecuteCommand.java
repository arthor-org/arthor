package com.arthor.core.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthor.core.command.context.CommandContext;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.*;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.Pod;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.store.ext.NativeRouteExt;
import com.arthor.core.deploy.store.ext.NativeServiceExt;
import com.arthor.core.deploy.store.ext.UncoupledRouteExt;
import com.arthor.core.integration.route.apisix.*;
import com.arthor.core.integration.route.constants.RouteConstants;
import com.arthor.core.integration.route.model.CreateRouteRequest;
import com.arthor.core.integration.route.model.DeleteRouteRequest;
import com.arthor.core.integration.route.model.DetailRouteRequest;
import com.arthor.core.registry.model.UpdateMetadataRequest;
import io.kubernetes.client.openapi.models.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.arthor.core.common.constant.ALL.*;

public abstract class AbstractExecuteCommand<I, O> implements Command<I, O> {

    private final static Logger log = LoggerFactory.getLogger(AbstractExecuteCommand.class);

    /**
     * 检查Deployment是否被部署成功,replicas,imageId
     *
     * @param v1Deployment
     * @param replicas
     * @param imageId
     * @return
     */
    protected boolean checkDeployment(V1Deployment v1Deployment, Integer replicas, String imageId) {
        return Objects.nonNull(v1Deployment) && Objects.nonNull(v1Deployment.getStatus())
                // 对比更新副本数
                && replicas.equals(v1Deployment.getStatus().getUpdatedReplicas())
                && Objects.nonNull(v1Deployment.getSpec())
                && Objects.nonNull(v1Deployment.getSpec().getTemplate().getSpec())
                && CollectionUtils.isNotEmpty(v1Deployment.getSpec().getTemplate().getSpec().getContainers())
                // 对比镜像
                && imageId.equals(v1Deployment.getSpec().getTemplate().getSpec().getContainers().get(0).getImage());
    }

    /**
     * 检查Service的部署
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    protected boolean checkDeployService(CommandContext commandContext, Deployment deployment) {
        if (DeployModeEnum.NATIVE == deployment.getDeployMode()) {
            V1Service v1Service =
                    commandContext.getKubernetesOpenApiService().serviceDetail(deployment.getNamespace(), deployment.getServiceName());
            if (Objects.isNull(v1Service)) {
                commandContext.getKubernetesOpenApiService().createService(deployment.getNamespace(), deployment.getServiceName());
            } else {
                return Boolean.TRUE;
            }
        } else if (DeployModeEnum.UNCOUPLED == deployment.getDeployMode()) {
            // 暂时没有
        }
        return Boolean.FALSE;
    }

    /**
     * 检查服务的删除
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    protected boolean checkDeleteService(CommandContext commandContext, Deployment deployment) {
        if (DeployModeEnum.NATIVE == deployment.getDeployMode()) {
            V1Service v1Service =
                    commandContext.getKubernetesOpenApiService().serviceDetail(deployment.getNamespace(), deployment.getServiceName());
            if (Objects.isNull(v1Service)) {
                return Boolean.TRUE;
            } else {
                commandContext.getKubernetesOpenApiService().deleteService(deployment.getNamespace(), deployment.getServiceName());
            }
        } else if (DeployModeEnum.UNCOUPLED == deployment.getDeployMode()) {
            // 暂时没有
        }
        return Boolean.FALSE;
    }

    /**
     * 路由部署
     *
     * @param commandContext
     * @param deployment
     * @return true,路由部署成功
     */
    protected boolean deployRoute(CommandContext commandContext, Deployment deployment) {
        switch (deployment.getDeployMode()) {
            case NATIVE:
                return nativeDeployRoute(commandContext, deployment);
            case UNCOUPLED:
                return uncoupledDeployRoute(commandContext, deployment);
            default:
                log.error("Unknown deploy mode:[{}]", deployment.getDeployMode());
        }
        return Boolean.FALSE;
    }

    /**
     * 以NATIVE方法部署路由
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    protected boolean nativeDeployRoute(CommandContext commandContext, Deployment deployment) {
        NativeServiceExt nativeServiceExt = JSON.parseObject(deployment.getServiceExt(), NativeServiceExt.class);
        NativeRouteExt routeExt = JSON.parseObject(deployment.getRouteExt(), NativeRouteExt.class);
        if (Objects.isNull(nativeServiceExt) || Objects.isNull(routeExt)) {
            log.error("No extInfo for nativeDeployRoute, deploymentId:[{}]", deployment.getId());
            return Boolean.FALSE;
        }
        // 获取路由详情
        V1Ingress v1Ingress =
                commandContext.getKubernetesOpenApiService().ingressDetail(deployment.getNamespace(), deployment.getRouteName());
        // 没有则创建
        if (Objects.isNull(v1Ingress)) {
            commandContext.getKubernetesOpenApiService().createIngress(deployment.getNamespace(), deployment.getRouteName(),
                    deployment.getServiceName(), nativeServiceExt.getServicePort(), deployment.getHost(),
                    deployment.getRoutePath(), routeExt.getIngressPathType(),
                    deployment.getCanary(), deployment.getCanaryType(), deployment.getCanaryValue());
        } else {
            // 检查路由是否匹配
            if (checkIngress(v1Ingress, deployment.getHost(), deployment.getRoutePath(),
                    routeExt.getIngressPathType(), deployment.getServiceName(),
                    nativeServiceExt.getServicePort(), deployment.getCanary(),
                    deployment.getCanaryType(), deployment.getCanaryValue())) {
                return Boolean.TRUE;
            } else {
                // 更新路由
                List<String> deleteAnnotations = getDeleteAnnotations(v1Ingress, deployment.getCanary(),
                        deployment.getCanaryType(), deployment.getCanaryValue());
                commandContext.getKubernetesOpenApiService().updateIngress(deployment.getNamespace(), deployment.getRouteName(), deployment.getHost(),
                        deployment.getRoutePath(), deployment.getServiceName(), nativeServiceExt.getServicePort(),
                        deployment.getCanary(), deployment.getCanaryType(), deployment.getCanaryValue(), deleteAnnotations);
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 以UNCOUPLED方法部署路由
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    protected boolean uncoupledDeployRoute(CommandContext commandContext, Deployment deployment) {
        UncoupledRouteExt uncoupledRouteExt = JSON.parseObject(deployment.getRouteExt(), UncoupledRouteExt.class);
        if (Objects.isNull(uncoupledRouteExt)) {
            log.error("No extInfo for uncoupledDeployRoute, deploymentId:[{}]", deployment.getId());
            return Boolean.FALSE;
        }
        List<Pod> pods = JSON.parseArray(deployment.getPods(), Pod.class);
        if (CollectionUtils.isEmpty(pods)) {
            log.error("No podsInfo for uncoupledDeployRoute, deploymentId:[{}]", deployment.getId());
            return Boolean.FALSE;
        }
        // 获取路由详情
        DetailRouteRequest detailRouteRequest = new DetailRouteRequest();
        detailRouteRequest.setRouteName(deployment.getRouteName());
        RouteDetailDTO routeDetail = commandContext.getRouteOpenApiService().detailRoute(detailRouteRequest);
        // 不存在则直接创建「隐藏逻辑,灰度部署不会导致创建路由」
        if (Objects.isNull(routeDetail)) {
            CreateRouteRequest createRouteRequest = new CreateRouteRequest();
            createRouteRequest.setHost(deployment.getHost());
            createRouteRequest.setName(deployment.getRouteName());
            createRouteRequest.setRoutePath(deployment.getRoutePath());
            createRouteRequest.setRegularExpression(uncoupledRouteExt.getRegularExpression());
            List<ResourceDTO> resources = Lists.newArrayList();
            for (Pod pod : pods) {
                ResourceDTO resource = new ResourceDTO();
                resource.setIp(pod.getIp());
                resource.setPort(pod.getPort());
                resources.add(resource);
            }
            createRouteRequest.setResources(resources);
            commandContext.getRouteOpenApiService().createRoute(createRouteRequest);
        } else {
            // 校验路由
            if (checkApisix(routeDetail, uncoupledRouteExt, deployment, pods)) {
                return Boolean.TRUE;
            } else {
                // 更新路由
                if (deployment.getCanary()) {
                    // 灰度更新路由,直接更新traffic-split插件即可
                    ApisixUpdateCanaryRouteRequest updateCanaryRouteRequest = new ApisixUpdateCanaryRouteRequest();
                    updateCanaryRouteRequest.setId(routeDetail.getId());
                    if (CanaryTypeEnum.HEADER == deployment.getCanaryType()) {
                        // traffic-split.rules[0]
                        TrafficSplitRuleDTO trafficSplitRule = new TrafficSplitRuleDTO();
                        // traffic-split.rules[0].match
                        TrafficSplitRuleMatchDTO trafficSplitRuleMatch = new TrafficSplitRuleMatchDTO();
                        // traffic-split.rules[0].match.vars[0]
                        TrafficSplitRuleMatchVarDTO trafficSplitRuleMatchVar = new TrafficSplitRuleMatchVarDTO();
                        trafficSplitRuleMatchVar.setVariable("http_gray");
                        trafficSplitRuleMatchVar.setOperator("==");
                        trafficSplitRuleMatchVar.setValue(deployment.getCanaryValue());
                        trafficSplitRuleMatch.setVars(List.of(trafficSplitRuleMatchVar.build()));
                        trafficSplitRule.setMatch(List.of(trafficSplitRuleMatch));
                        // traffic-split.rules[0].weighted_upstreams[0]
                        TrafficSplitRuleWeightedUpstreamDTO trafficSplitRuleWeightedUpstream = new TrafficSplitRuleWeightedUpstreamDTO();
                        // traffic-split.rules[0].weighted_upstreams[0].upstream.nodes
                        UpstreamDTO upstream = new UpstreamDTO();
                        List<NodeDTO> nodes = pods.stream().map(p -> {
                            NodeDTO node = new NodeDTO();
                            node.setHost(p.getIp());
                            node.setPort(p.getPort());
                            node.setWeight(RouteConstants.DEFAULT_WEIGHT_VALUE);
                            return node;
                        }).collect(Collectors.toList());
                        upstream.setNodes(nodes);
                        trafficSplitRuleWeightedUpstream.setUpstream(upstream);
                        // traffic-split.rules.weighted_upstreams[0].weight
                        trafficSplitRuleWeightedUpstream.setWeight(RouteConstants.DEFAULT_WEIGHT_VALUE);
                        trafficSplitRule.setWeightedUpstreams(List.of(trafficSplitRuleWeightedUpstream));
                        updateCanaryRouteRequest.setRules(List.of(trafficSplitRule));
                    } else if (CanaryTypeEnum.WEIGHT == deployment.getCanaryType()) {
                        int percentage = Integer.parseInt(deployment.getCanaryValue());
                        int weight = percentage / 20;
                        // traffic-split.rules[0]
                        TrafficSplitRuleDTO trafficSplitRule = new TrafficSplitRuleDTO();
                        // traffic-split.rules[0].weighted_upstreams[0]
                        TrafficSplitRuleWeightedUpstreamDTO specificWeighted = new TrafficSplitRuleWeightedUpstreamDTO();
                        // traffic-split.rules[0].weighted_upstreams[0].upstream
                        UpstreamDTO upstream = new UpstreamDTO();
                        // traffic-split.rules[0].weighted_upstreams[0].upstream.nodes
                        List<NodeDTO> nodes = pods.stream().map(p -> {
                            NodeDTO node = new NodeDTO();
                            node.setHost(p.getIp());
                            node.setPort(p.getPort());
                            node.setWeight(RouteConstants.DEFAULT_WEIGHT_VALUE);
                            return node;
                        }).collect(Collectors.toList());
                        upstream.setNodes(nodes);
                        specificWeighted.setUpstream(upstream);
                        specificWeighted.setWeight(weight);
                        // traffic-split.rules[0].weighted_upstreams[1]
                        TrafficSplitRuleWeightedUpstreamDTO normalWeighted = new TrafficSplitRuleWeightedUpstreamDTO();
                        // traffic-split.rules[0].weighted_upstreams[1].weight
                        normalWeighted.setWeight(5 - weight);
                        trafficSplitRule.setWeightedUpstreams(List.of(specificWeighted, normalWeighted));
                        updateCanaryRouteRequest.setRules(List.of(trafficSplitRule));
                    }
                    commandContext.getRouteOpenApiService().updateRoute(updateCanaryRouteRequest);
                } else {
                    ApisixUpdateNormalRouteRequest updateNormalRouteRequest = new ApisixUpdateNormalRouteRequest();
                    updateNormalRouteRequest.setId(routeDetail.getId());
                    // upstream.nodes
                    List<NodeDTO> nodes = pods.stream().map(p -> {
                        NodeDTO node = new NodeDTO();
                        node.setHost(p.getIp());
                        node.setPort(p.getPort());
                        node.setWeight(RouteConstants.DEFAULT_WEIGHT_VALUE);
                        return node;
                    }).collect(Collectors.toList());
                    updateNormalRouteRequest.setNodes(nodes);
                    commandContext.getRouteOpenApiService().updateRoute(updateNormalRouteRequest);
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 检查路由是否已经成功被设置
     * 这里的检查逻辑和ingress有所不一样,ingress对于canary和非canary是两个,而apisix都是一样
     *
     * @return
     */
    private boolean checkApisix(RouteDetailDTO routeDetail, UncoupledRouteExt uncoupledRouteExt,
                                Deployment deployment, List<Pod> pods) {
        if (deployment.getRoutePath().equals(routeDetail.getUri())
                && deployment.getHost().equals(routeDetail.getHost())) {
            // 检查插件
            JSONObject plugins = routeDetail.getPlugins();
            if (Objects.isNull(plugins)
                    // proxy-rewrite,插件必有
                    || !plugins.containsKey(RouteConstants.PROXY_REWRITE_PLUGIN_NAME)
                    // traffic-split,灰度必有
                    || (deployment.getCanary() && !plugins.containsKey(RouteConstants.TRAFFIC_SPLIT_PLUGIN_NAME))) {
                return Boolean.FALSE;
            }
            // 检查转发path
            ProxyRewritePluginDTO rewritePlugin = plugins.getObject(RouteConstants.PROXY_REWRITE_PLUGIN_NAME, ProxyRewritePluginDTO.class);
            rewritePlugin.loadFrom();
            if (StringUtils.isBlank(uncoupledRouteExt.getRegularExpression())
                    && !uncoupledRouteExt.getRegularExpression().equals(rewritePlugin.getRegularExpression())) {
                return Boolean.FALSE;
            }
            // 检查路由上注册的Pod是否匹配
            if (deployment.getCanary()) {
                TrafficSplitPluginDTO trafficSplitPlugin = plugins.getObject(RouteConstants.TRAFFIC_SPLIT_PLUGIN_NAME, TrafficSplitPluginDTO.class);
                if (Objects.isNull(trafficSplitPlugin)
                        || CollectionUtils.isEmpty(trafficSplitPlugin.getRules())
                        // 目前只有一个
                        || ONE != trafficSplitPlugin.getRules().size()) {
                    return Boolean.FALSE;
                }
                TrafficSplitRuleDTO trafficSplitRule = trafficSplitPlugin.getRules().get(0);
                if (CanaryTypeEnum.HEADER == deployment.getCanaryType()) {
                    // 灰度,检查灰度规则
                    if (CollectionUtils.isEmpty(trafficSplitRule.getMatch())
                            || ONE != trafficSplitRule.getMatch().size()) {
                        return Boolean.FALSE;
                    }
                    TrafficSplitRuleMatchDTO splitRuleMatch = trafficSplitRule.getMatch().get(0);
                    if (CollectionUtils.isEmpty(splitRuleMatch.getVars())
                            || ONE != splitRuleMatch.getVars().size()) {
                        return Boolean.FALSE;
                    }
                    List<String> vars = splitRuleMatch.getVars().get(0);
                    if (CollectionUtils.isEmpty(vars)
                            || THREE != vars.size()) {
                        return Boolean.FALSE;
                    }
                    TrafficSplitRuleMatchVarDTO trafficSplitRuleMatchVar = new TrafficSplitRuleMatchVarDTO(vars);
                    if (!trafficSplitRuleMatchVar.getVariable().equals(RouteConstants.GRAY_VARIABLES)
                            || !trafficSplitRuleMatchVar.getValue().equals(deployment.getCanaryValue())) {
                        return Boolean.FALSE;
                    }
                    if (CollectionUtils.isEmpty(trafficSplitRule.getWeightedUpstreams())
                            || ONE != trafficSplitRule.getWeightedUpstreams().size()) {
                        return Boolean.FALSE;
                    }
                    // 灰度,检查灰度注册的Pods
                    TrafficSplitRuleWeightedUpstreamDTO trafficSplitRuleWeightedUpstream = trafficSplitRule.getWeightedUpstreams().get(0);
                    if (Objects.isNull(trafficSplitRuleWeightedUpstream)
                            || CollectionUtils.isEmpty(trafficSplitRuleWeightedUpstream.getUpstream().getNodes())
                            || pods.size() != trafficSplitRuleWeightedUpstream.getUpstream().getNodes().size()) {
                        return Boolean.FALSE;
                    }
                    for (Pod pod : pods) {
                        boolean found = false;
                        for (NodeDTO node : trafficSplitRuleWeightedUpstream.getUpstream().getNodes()) {
                            if (pod.getIp().equals(node.getHost())
                                    && pod.getPort().equals(node.getPort())) {
                                found = true;
                                break;
                            }
                        }
                        // 部署与路由中注册的Pod不匹配
                        if (!found) {
                            return Boolean.FALSE;
                        }
                    }
                } else if (CanaryTypeEnum.WEIGHT == deployment.getCanaryType()) {
                    if (CollectionUtils.isNotEmpty(trafficSplitRule.getMatch())
                            || CollectionUtils.isEmpty(trafficSplitRule.getWeightedUpstreams())
                            || TWO != trafficSplitRule.getWeightedUpstreams().size()) {
                        return Boolean.FALSE;
                    }
                    // 对于蓝绿来说,存在两个权重,普通权重和特殊权重
                    TrafficSplitRuleWeightedUpstreamDTO normalWeighted = null;
                    TrafficSplitRuleWeightedUpstreamDTO specificWeighted = null;
                    // 找到对应的权重
                    for (TrafficSplitRuleWeightedUpstreamDTO weightedUpstream : trafficSplitRule.getWeightedUpstreams()) {
                        if (Objects.isNull(weightedUpstream.getUpstream())
                                && Objects.nonNull(weightedUpstream.getWeight())) {
                            normalWeighted = weightedUpstream;
                        } else if (Objects.nonNull(weightedUpstream.getUpstream())
                                && Objects.nonNull(weightedUpstream.getWeight())) {
                            specificWeighted = weightedUpstream;
                        }
                    }
                    if (Objects.isNull(normalWeighted)
                            || Objects.isNull(specificWeighted)) {
                        return Boolean.FALSE;
                    }
                    int percentage = Integer.parseInt(deployment.getCanaryValue());
                    int weight = percentage / 20;
                    // 比较权重是否匹配
                    if (weight != specificWeighted.getWeight()) {
                        return Boolean.FALSE;
                    }
                    if (Objects.isNull(specificWeighted.getUpstream())
                            || CollectionUtils.isEmpty(specificWeighted.getUpstream().getNodes())) {
                        return Boolean.FALSE;
                    }
                    // 比较权重注册的Pod是否匹配
                    List<NodeDTO> nodes = specificWeighted.getUpstream().getNodes();
                    for (Pod pod : pods) {
                        boolean found = false;
                        for (NodeDTO node : nodes) {
                            if (pod.getIp().equals(node.getHost())
                                    && pod.getPort().equals(node.getPort())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            return Boolean.FALSE;
                        }
                    }
                }
            } else {
                if (Objects.isNull(routeDetail.getUpstream())
                        || CollectionUtils.isEmpty(routeDetail.getUpstream().getNodes())) {
                    return Boolean.FALSE;
                }
                // 检查upstream,非灰度
                List<NodeDTO> nodes = routeDetail.getUpstream().getNodes();
                for (Pod pod : pods) {
                    boolean found = false;
                    for (NodeDTO node : nodes) {
                        if (pod.getIp().equals(node.getHost())
                                && pod.getPort().equals(node.getPort())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        return Boolean.FALSE;
                    }
                }
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 路由的删除
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    protected boolean deleteRoute(CommandContext commandContext, Deployment deployment) {
        switch (deployment.getDeployMode()) {
            case NATIVE:
                return nativeDeleteRoute(commandContext, deployment);
            case UNCOUPLED:
                return uncoupledDeleteRoute(commandContext, deployment);
            default:
                log.error("Unknown deploy mode:[{}]", deployment.getDeployMode());
        }
        return Boolean.FALSE;
    }

    /**
     * 以NATIVE方法删除路由
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    private boolean nativeDeleteRoute(CommandContext commandContext, Deployment deployment) {
        NativeServiceExt nativeServiceExt = JSON.parseObject(deployment.getServiceExt(), NativeServiceExt.class);
        NativeRouteExt routeExt = JSON.parseObject(deployment.getRouteExt(), NativeRouteExt.class);
        // 路由详情
        V1Ingress v1Ingress =
                commandContext.getKubernetesOpenApiService().ingressDetail(deployment.getNamespace(), deployment.getRouteName());
        // 不存在则删除成功
        if (Objects.isNull(v1Ingress)) {
            return Boolean.TRUE;
        } else {
            // 存在则判断是否与当前删除路由匹配
            if (checkIngress(v1Ingress, deployment.getHost(), deployment.getRoutePath(),
                    routeExt.getIngressPathType(), deployment.getServiceName(),
                    nativeServiceExt.getServicePort(), deployment.getCanary(),
                    deployment.getCanaryType(), deployment.getCanaryValue())) {
                // 删除路由
                commandContext.getKubernetesOpenApiService().deleteIngress(deployment.getNamespace(), deployment.getRouteName());
            } else {
                // 避免误删除
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 以UNCOUPLED方法删除路由
     *
     * @param commandContext
     * @param deployment
     * @return
     */
    private boolean uncoupledDeleteRoute(CommandContext commandContext, Deployment deployment) {
        UncoupledRouteExt uncoupledRouteExt = JSON.parseObject(deployment.getRouteExt(), UncoupledRouteExt.class);
        if (Objects.isNull(uncoupledRouteExt)) {
            log.error("No extInfo for uncoupledDeleteRoute, deploymentId:[{}]", deployment.getId());
            return Boolean.FALSE;
        }
        // 路由详情
        DetailRouteRequest detailRouteRequest = new DetailRouteRequest();
        detailRouteRequest.setRouteName(deployment.getRouteName());
        RouteDetailDTO routeDetail = commandContext.getRouteOpenApiService().detailRoute(detailRouteRequest);
        // 没有则删除成功
        if (Objects.isNull(routeDetail)) {
            return Boolean.TRUE;
        } else {
            List<Pod> pods = JSON.parseArray(deployment.getPods(), Pod.class);
            if (CollectionUtils.isEmpty(pods)) {
                log.error("No podsInfo for uncoupledDeleteRoute, deploymentId:[{}]", deployment.getId());
                return Boolean.FALSE;
            }
            if (checkApisix(routeDetail, uncoupledRouteExt, deployment, pods)) {
                // 删除
                if (deployment.getCanary()) {
                    ApisixDeleteCanaryRouteRequest apisixDeleteCanaryRouteRequest = new ApisixDeleteCanaryRouteRequest();
                    apisixDeleteCanaryRouteRequest.setId(routeDetail.getId());
                    ProxyRewritePluginDTO proxyRewritePlugin = new ProxyRewritePluginDTO();
                    proxyRewritePlugin.setRegularExpression(uncoupledRouteExt.getRegularExpression());
                    proxyRewritePlugin.setTemplate(RouteConstants.DEFAULT_FORWARDING_PATH_TEMPLATE);
                    apisixDeleteCanaryRouteRequest.setProxyRewritePlugin(proxyRewritePlugin);
                    commandContext.getRouteOpenApiService().updateRoute(apisixDeleteCanaryRouteRequest);
                } else {
                    // 直接删除路由「隐藏逻辑,如果存在灰度部署的话,是不允许删除非灰度的」
                    DeleteRouteRequest deleteRouteRequest = new DeleteRouteRequest();
                    deleteRouteRequest.setId(routeDetail.getId());
                    commandContext.getRouteOpenApiService().deleteRoute(deleteRouteRequest);
                }
            } else {
                // 避免误删除
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 校验Ingress是否已经成功被设置,host,path,pathType
     *
     * @param v1Ingress
     * @param host
     * @param path
     * @param serviceName
     * @param servicePort
     * @return
     */
    protected boolean checkIngress(V1Ingress v1Ingress, String host, String path, String pathType,
                                   String serviceName, Integer servicePort,
                                   Boolean canary, CanaryTypeEnum canaryType, String canaryValue) {
        // 灰度检查,对于Ingress来说就是检查注解
        if (Objects.nonNull(v1Ingress.getMetadata())
                && Objects.nonNull(v1Ingress.getMetadata().getAnnotations())) {
            Map<String, String> annotations = v1Ingress.getMetadata().getAnnotations();
            if (canary) {
                if (!annotations.containsKey(ALL.CANARY_ANNOTATION)) {
                    return Boolean.FALSE;
                } else if (CanaryTypeEnum.WEIGHT == canaryType
                        && (!annotations.containsKey(ALL.CANARY_WEIGHT_ANNOTATION)
                        || !annotations.get(ALL.CANARY_WEIGHT_ANNOTATION).equals(canaryValue))) {
                    return Boolean.FALSE;
                } else if (CanaryTypeEnum.HEADER == canaryType
                        && (!annotations.containsKey(ALL.CANARY_BY_HEADER_ANNOTATION)
                        || !annotations.containsKey(ALL.CANARY_BY_HEADER_VALUE_ANNOTATION)
                        || !annotations.get(ALL.CANARY_BY_HEADER_VALUE_ANNOTATION).equals(canaryValue))) {
                    return Boolean.FALSE;
                }
            }
        }
        // 检验Ingress路由
        if (Objects.nonNull(v1Ingress.getSpec())
                && CollectionUtils.isNotEmpty(v1Ingress.getSpec().getRules())) {
            for (V1IngressRule ingressRule : v1Ingress.getSpec().getRules()) {
                if (host.equals(ingressRule.getHost())) {
                    V1HTTPIngressRuleValue http = ingressRule.getHttp();
                    if (Objects.nonNull(http)) {
                        List<V1HTTPIngressPath> paths = http.getPaths();
                        if (CollectionUtils.isNotEmpty(paths)) {
                            V1HTTPIngressPath ingressPath = paths.get(0);
                            if (path.equals(ingressPath.getPath())
                                    && pathType.equals(ingressPath.getPathType())
                                    && Objects.nonNull(ingressPath.getBackend())
                                    && Objects.nonNull(ingressPath.getBackend().getService())) {
                                if (serviceName.equals(ingressPath.getBackend().getService().getName())
                                        && Objects.nonNull(ingressPath.getBackend().getService().getPort())
                                        && servicePort.equals(ingressPath.getBackend().getService().getPort().getNumber())) {
                                    return Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
            }
        }
        return Boolean.FALSE;
    }


    /**
     * 提取出要删除的Ingress注解
     *
     * @param v1Ingress
     * @param canary
     * @param canaryType
     * @param canaryValue
     * @return
     */
    private List<String> getDeleteAnnotations(V1Ingress v1Ingress, Boolean canary,
                                              CanaryTypeEnum canaryType, String canaryValue) {
        List<String> ret = Lists.newArrayList();
        if (canary
                && Objects.nonNull(v1Ingress.getMetadata())
                && Objects.nonNull(v1Ingress.getMetadata().getAnnotations())) {
            Map<String, String> annotations = v1Ingress.getMetadata().getAnnotations();
            if (CanaryTypeEnum.HEADER == canaryType
                    && annotations.containsKey(CANARY_WEIGHT_ANNOTATION)) {
                ret.add(CANARY_WEIGHT_ANNOTATION);
            } else if (CanaryTypeEnum.WEIGHT == canaryType
                    && (annotations.containsKey(CANARY_BY_HEADER_ANNOTATION)
                    || annotations.containsKey(CANARY_BY_HEADER_VALUE_ANNOTATION))) {
                ret.add(CANARY_BY_HEADER_ANNOTATION);
                ret.add(CANARY_BY_HEADER_VALUE_ANNOTATION);
            }
        }
        return ret;
    }

    protected CreateDeploymentRequest buildUpdateDeploymentInfoRequest(Long id, Integer readyReplicas,
                                                                     Integer updatedReplicas,
                                                                     Integer availableReplicas,
                                                                     Integer unavailableReplicas,
                                                                     DeployItemStatusEnum success) {
        CreateDeploymentRequest updateDeploymentInfoRequest = new CreateDeploymentRequest();
        updateDeploymentInfoRequest.setId(id);
        updateDeploymentInfoRequest.setDeploymentReadyReplicas(readyReplicas);
        updateDeploymentInfoRequest.setDeploymentUpdatedReplicas(updatedReplicas);
        updateDeploymentInfoRequest.setDeploymentAvailableReplicas(availableReplicas);
        updateDeploymentInfoRequest.setDeploymentUnavailableReplicas(unavailableReplicas);
        updateDeploymentInfoRequest.setDeploymentStatus(success);
        return updateDeploymentInfoRequest;
    }

    protected CreateDeploymentRequest buildUpdateLifecycleRequest(Long id, LifecycleEnum lifecycle) {
        CreateDeploymentRequest updateLifecycleRequest = new CreateDeploymentRequest();
        updateLifecycleRequest.setId(id);
        updateLifecycleRequest.setLifecycle(lifecycle);
        if (LifecycleEnum.CLOSING == lifecycle) {
            updateLifecycleRequest.setShutdownTime(LocalDateTime.now());
        }
        return updateLifecycleRequest;
    }

    protected CreateDeploymentRequest buildUpdateLifecycleRequest(Long id, String pods, LifecycleEnum lifecycle) {
        CreateDeploymentRequest updateLifecycleRequest = new CreateDeploymentRequest();
        updateLifecycleRequest.setId(id);
        updateLifecycleRequest.setPods(pods);
        updateLifecycleRequest.setLifecycle(lifecycle);
        if (LifecycleEnum.CLOSING == lifecycle) {
            updateLifecycleRequest.setShutdownTime(LocalDateTime.now());
        }
        return updateLifecycleRequest;
    }

    protected CreateDeploymentRequest buildUpdateDeployStatusRequest(Long id, DeployStatusEnum deployStatus) {
        CreateDeploymentRequest updateDeployStatusRequest = new CreateDeploymentRequest();
        updateDeployStatusRequest.setId(id);
        updateDeployStatusRequest.setStatus(deployStatus);
        return updateDeployStatusRequest;
    }

    protected CreateDeploymentRequest buildUpdateDeploymentStatusRequest(Long id, DeployItemStatusEnum deploymentStatus) {
        CreateDeploymentRequest updateDeploymentStatusRequest = new CreateDeploymentRequest();
        updateDeploymentStatusRequest.setId(id);
        updateDeploymentStatusRequest.setDeploymentStatus(deploymentStatus);
        return updateDeploymentStatusRequest;
    }

    protected CreateDeploymentRequest buildUpdateServiceStatusRequest(Long id, DeployItemStatusEnum serviceStatus) {
        CreateDeploymentRequest updateServiceStatusRequest = new CreateDeploymentRequest();
        updateServiceStatusRequest.setId(id);
        updateServiceStatusRequest.setServiceStatus(serviceStatus);
        return updateServiceStatusRequest;
    }

    protected CreateDeploymentRequest buildUpdateIngressStatusRequest(Long id, DeployItemStatusEnum ingressStatus) {
        CreateDeploymentRequest updateIngressStatusRequest = new CreateDeploymentRequest();
        updateIngressStatusRequest.setId(id);
        updateIngressStatusRequest.setRouteStatus(ingressStatus);
        return updateIngressStatusRequest;
    }

    protected UpdateMetadataRequest buildUpdateMetadataRequest(String name, String deploymentName, String symbol,
                                                               String ip, Integer port, LifecycleEnum lifecycle, boolean canary,
                                                               CanaryTypeEnum canaryType, String canaryValue) {
        UpdateMetadataRequest updateMetadataRequest = new UpdateMetadataRequest();
        updateMetadataRequest.setName(name);
        updateMetadataRequest.setDeploymentName(deploymentName);
        updateMetadataRequest.setSymbol(symbol);
        updateMetadataRequest.setGroup(ALL.DEFAULT_VALUE);
        updateMetadataRequest.setIp(ip);
        updateMetadataRequest.setPort(port);
        updateMetadataRequest.setLifecycle(lifecycle);
        updateMetadataRequest.setCanary(canary);
        updateMetadataRequest.setCanaryType(canaryType);
        updateMetadataRequest.setCanaryValue(canaryValue);
        updateMetadataRequest.setLatestTime(LocalDateTime.now());
        updateMetadataRequest.setFuture(null);
        return updateMetadataRequest;
    }

}
