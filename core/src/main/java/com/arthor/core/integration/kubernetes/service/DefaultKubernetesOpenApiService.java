package com.arthor.core.integration.kubernetes.service;

import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.integration.kubernetes.KubernetesProperties;
import com.arthor.core.integration.kubernetes.exception.KubernetesOpenApiException;
import com.arthor.core.integration.kubernetes.model.IngressPatchJson;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.NetworkingV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.PatchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.arthor.core.common.constant.ALL.*;

/**
 * Kubernetes集成
 */
public class DefaultKubernetesOpenApiService implements KubernetesOpenApiService{

    private final static Logger log = LoggerFactory.getLogger(DefaultKubernetesOpenApiService.class);
    private final static String CONFIG_FILE = "kubernetes/config";
    private final KubernetesProperties kubernetesProperties;

    public DefaultKubernetesOpenApiService(KubernetesProperties kubernetesProperties) throws IOException {
        this.kubernetesProperties = kubernetesProperties;
        apiClient();
    }

    private ApiClient apiClient() throws IOException {
        ApiClient apiClient =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new InputStreamReader(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE))))).build();
        apiClient.setDebugging(kubernetesProperties.getDebugging());
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(apiClient);
        return apiClient;
    }

    /**
     * 创建命名空间
     *
     * @param name
     * @throws ApiException
     */
    public void createNamespace(String name) {
        CoreV1Api coreV1Api = new CoreV1Api();
        V1Namespace v1Namespace = new V1NamespaceBuilder()
                .withKind(ALL.KUBERNETES_KIND_NAMESPACE)
                .withMetadata(new V1ObjectMetaBuilder().withName(name).build()).build();
        try {
            coreV1Api.createNamespace(v1Namespace, null, null, null);
        } catch (ApiException e) {
            log.error("Failed to createNamespace, namespace:[{}]", name, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    /**
     * 获取命名空间详情
     *
     * @param name
     * @return
     */
    public V1Namespace namespaceDetail(String name) {
        CoreV1Api coreV1Api = new CoreV1Api();
        try {
            return coreV1Api.readNamespace(name, null, null, null);
        } catch (ApiException e) {
            if (e.getCode() == 404) {
                return null;
            }
            log.error("Failed to namespaceDetail, name:[{}]", name, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    @Override
    public void createDeployment(String namespace, String deploymentName, String imageId, Integer replicas) {
        try {
            createDeployment(namespace, deploymentName, replicas, deploymentName, deploymentName,
                    imageId, kubernetesProperties.getDefaultImagePullPolicy(), kubernetesProperties.getDefaultPodPort());
        } catch (ApiException e) {
            log.error("Failed to createDeployment, namespace:[{}], deploymentName:[{}], imageId:[{}], replicas:[{}]",
                    namespace, deploymentName, imageId, replicas, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    @Override
    public void createDeployment(String namespace, String deploymentName, Integer replicas, String podLabelName,
                                 String containerName, String image, String imagePullPolicy, Integer port) throws ApiException {
        AppsV1Api appsV1Api = new AppsV1Api();
        V1Deployment v1Deployment = new V1Deployment();
        V1ObjectMeta deploymentMeta = new V1ObjectMeta();
        deploymentMeta.name(deploymentName);
        V1DeploymentSpec v1DeploymentSpec = new V1DeploymentSpec();
        V1LabelSelector v1LabelSelector = new V1LabelSelector();
        HashMap<String, String> matchLabels = Maps.newHashMap();
        matchLabels.put(ALL.POD, podLabelName);
        v1LabelSelector.matchLabels(matchLabels);
        V1PodTemplateSpec v1PodTemplateSpec = new V1PodTemplateSpec();
        V1ObjectMeta podMeta = new V1ObjectMeta();
        Map<String, String> labels = Maps.newHashMap();
        labels.put(ALL.POD, podLabelName);
        podMeta.labels(labels);
        V1PodSpec v1PodSpec = new V1PodSpec();
        V1Container v1Container = new V1Container();
        V1ContainerPort v1ContainerPort = new V1ContainerPort();
        v1ContainerPort.containerPort(port);
        v1Container.name(containerName).image(image).imagePullPolicy(imagePullPolicy).ports(Lists.newArrayList(v1ContainerPort));
        v1PodSpec.containers(Lists.newArrayList(v1Container));
        V1LocalObjectReference v1LocalObjectReference = new V1LocalObjectReference();
        v1LocalObjectReference.name(kubernetesProperties.getImagePullSecrets());
        v1PodSpec.imagePullSecrets(Lists.newArrayList(v1LocalObjectReference));
        v1PodTemplateSpec.metadata(podMeta).spec(v1PodSpec);
        v1DeploymentSpec.replicas(replicas).selector(v1LabelSelector).template(v1PodTemplateSpec);
        v1Deployment.kind(KUBERNETES_KIND_DEPLOYMENT).metadata(deploymentMeta).spec(v1DeploymentSpec);
        appsV1Api.createNamespacedDeployment(namespace, v1Deployment, null, null, null);
    }

    public V1Deployment deploymentDetail(String namespace, String deploymentName) {
        try {
            AppsV1Api appsV1Api = new AppsV1Api();
            return appsV1Api.readNamespacedDeployment(deploymentName, namespace, null, null, null);
        } catch (ApiException e) {
            if (e.getCode() == 404) {
                return null;
            }
            log.error("Failed to deploymentDetail, namespace:[{}], deploymentName:[{}]", namespace, deploymentName, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    public List<V1Pod> listPods(String namespace, String deploymentName) {
        CoreV1Api api = new CoreV1Api();
        try {
            V1PodList v1PodList =
                    api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);
            return v1PodList.getItems().stream()
                    .filter(p -> p.getMetadata().getName().startsWith(deploymentName + "-"))
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            log.error("Failed to listPods, namespace:[{}]", namespace, e);
            throw new RuntimeException(e);
        }
    }

    public V1Service serviceDetail(String namespace, String serviceName) {
        try {
            CoreV1Api coreV1Api = new CoreV1Api();
            return coreV1Api.readNamespacedService(serviceName, namespace, null, null, null);
        } catch (ApiException e) {
            if (e.getCode() == 404) {
                return null;
            }
            log.error("Failed to serviceDetail, namespace:[{}], serviceName:[{}]", namespace, serviceName, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    public V1Ingress ingressDetail(String namespace, String ingressName) {
        try {
            NetworkingV1Api networkingV1Api = new NetworkingV1Api();
            return networkingV1Api.readNamespacedIngress(ingressName, namespace, null, null, null);
        } catch (ApiException e) {
            if (e.getCode() == 404) {
                return null;
            }
            log.error("Failed to ingressDetail, namespace:[{}]", namespace, e);
            throw new KubernetesOpenApiException(e);
        }
    }


    public void deleteDeployment(String namespace, String deploymentName) {
        try {
            AppsV1Api appsV1Api = new AppsV1Api();
            V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();
            appsV1Api.deleteNamespacedDeployment(deploymentName, namespace, null, null, kubernetesProperties.getGracePeriodSeconds(),
                    null, null, v1DeleteOptions);
        } catch (ApiException e) {
            log.error("Failed to deleteDeployment, namespace:[{}], deploymentName:[{}]", namespace, deploymentName, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    @Override
    public void createService(String namespace, String applicationNme) {
        try {
            createService(namespace, applicationNme, applicationNme, kubernetesProperties.getDefaultServicePort(), kubernetesProperties.getDefaultServicePort());
        } catch (ApiException e) {
            log.error("Failed to createService, namespace:[{}], applicationNme:[{}]", namespace, applicationNme, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    @Override
    public void createService(String namespace, String serviceName, String podLabelName,
                              Integer servicePort, Integer targetPort) throws ApiException {
        CoreV1Api coreV1Api = new CoreV1Api();
        V1Service v1Service = new V1Service();
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        v1ObjectMeta.name(serviceName);
        V1ServiceSpec v1ServiceSpec = new V1ServiceSpec();
        Map<String, String> selector = Maps.newHashMap();
        selector.put(ALL.POD, podLabelName);
        V1ServicePort v1ServicePort = new V1ServicePort();
        IntOrString targetPortObj = new IntOrString(targetPort);
        v1ServicePort.port(servicePort).targetPort(targetPortObj);
        v1ServiceSpec.selector(selector).ports(Lists.newArrayList(v1ServicePort));
        v1Service.kind(ALL.KUBERNETES_KIND_SERVICE).metadata(v1ObjectMeta).spec(v1ServiceSpec);
        coreV1Api.createNamespacedService(namespace, v1Service, null, null, null);
    }

    public void deleteService(String namespace, String serviceName) {
        try {
            CoreV1Api coreV1Api = new CoreV1Api();
            V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();
            coreV1Api.deleteNamespacedService(serviceName, namespace, null, null,
                    kubernetesProperties.getGracePeriodSeconds(), null, null, v1DeleteOptions);
        } catch (ApiException e) {
            log.error("Failed to deleteService, namespace:[{}], serviceName:[{}]", namespace, serviceName, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    public void listPodForAllNamespaces() throws ApiException {
        CoreV1Api api = new CoreV1Api();
        V1PodList list =
                api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
    }

    /**
     *
     * @param namespace 命名空间
     * @param ingressName ingress名称
     * @param serviceName 服务名称
     * @param servicePort 服务端口
     * @param host
     * @param path
     * @param pathType
     */
    public void createIngress(String namespace, String ingressName,
                              String serviceName, Integer servicePort,
                              String host, String path, String pathType,
                              boolean canary, CanaryTypeEnum canaryType, String canaryValue) {
        try {
            HashMap<String, String> annotations = Maps.newHashMap();
            annotations.put(REWRITE_TARGET_ANNOTATION, "/$2");
            if (canary) {
                annotations.put(CANARY_ANNOTATION, "true");
                if (CanaryTypeEnum.HEADER == canaryType) {
                    annotations.put(CANARY_BY_HEADER_ANNOTATION, CANARY_HEADER_KEY);
                    annotations.put(CANARY_BY_HEADER_VALUE_ANNOTATION, canaryValue);
                } else if (CanaryTypeEnum.WEIGHT == canaryType) {
                    annotations.put(CANARY_WEIGHT_ANNOTATION, canaryValue);
                }
            }
            NetworkingV1Api networkingV1Api = new NetworkingV1Api();
            V1Ingress ingress = new V1IngressBuilder()
                    .withKind(ALL.KUBERNETES_KIND_INGRESS)
                    .withMetadata(new V1ObjectMetaBuilder()
                            .withName(ingressName)
                            .withAnnotations(annotations).build())
                    .withSpec(new V1IngressSpecBuilder()
                            .withIngressClassName(INGRESS_CLASS_NAME)
                            .withRules(Lists.newArrayList(new V1IngressRuleBuilder()
                                    .withHost(host)
                                    .withHttp(new V1HTTPIngressRuleValueBuilder()
                                            .withPaths(new V1HTTPIngressPathBuilder()
                                                    .withPath(path)
                                                    .withPathType(pathType)
                                                    .withBackend(new V1IngressBackendBuilder()
                                                            .withService(new V1IngressServiceBackendBuilder()
                                                                    .withName(serviceName)
                                                                    .withPort(new V1ServiceBackendPortBuilder()
                                                                            .withNumber(servicePort).build()).build())
                                                            .build()).build()).build()).build())).build()).build();
            networkingV1Api.createNamespacedIngress(namespace, ingress, null, null, null);
        } catch (ApiException e) {
            log.error("Failed to createIngress host:[{}], namespace:[{}], path:[{}], serviceName:[{}]"
                    , host, namespace, path, serviceName, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    public void updateIngress(String namespace, String ingressName, String host, String path,
                              String serviceName, Integer servicePort,
                              boolean canary, CanaryTypeEnum canaryType, String canaryValue,
                              List<String> deleteAnnotations) {
        try {
            NetworkingV1Api networkingV1Api = new NetworkingV1Api();
            V1Patch v1Patch = new V1Patch(IngressPatchJson.patchString(host, path, getProperties().getDefaultPathType(),
                    serviceName, servicePort, canary, canaryType, canaryValue, deleteAnnotations));
            PatchUtils.patch(
                    Object.class,
                    () -> networkingV1Api.patchNamespacedIngressCall(ingressName, namespace, v1Patch, null,
                            null, null, null, null),
                    V1Patch.PATCH_FORMAT_STRATEGIC_MERGE_PATCH,
                    networkingV1Api.getApiClient());
        } catch (ApiException e) {
            log.error("Failed to updateIngress, namespace:[{}], ingressName:[{}], host:[{}], path:[{}], serviceName:[{}], servicePort:[{}]",
                    namespace, ingressName, host, path, serviceName, servicePort, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    public void deleteIngress(String namespace, String name) {
        try {
            NetworkingV1Api networkingV1Api = new NetworkingV1Api();
            V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();
            networkingV1Api.deleteNamespacedIngress(name, namespace, null, null,
                    kubernetesProperties.getGracePeriodSeconds(), null, null, v1DeleteOptions);
        } catch (ApiException e) {
            log.error("Failed to deleteIngress namespace:[{}], name:[{}]", namespace, name, e);
            throw new KubernetesOpenApiException(e);
        }
    }

    @Override
    public KubernetesProperties getProperties() {
        return this.kubernetesProperties;
    }

}
