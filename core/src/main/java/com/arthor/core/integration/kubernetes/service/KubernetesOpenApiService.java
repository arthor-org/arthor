package com.arthor.core.integration.kubernetes.service;

import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.integration.kubernetes.KubernetesProperties;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.*;

import java.util.List;

public interface KubernetesOpenApiService {

    void createNamespace(String name);

    V1Namespace namespaceDetail(String name);

    void createDeployment(String namespace, String deploymentName, String imageId, Integer replicas);

    void createDeployment(String namespace, String deploymentName, Integer replicas, String podLabelName,
                                  String containerName, String image, String imagePullPolicy, Integer port) throws ApiException;

    // void updateDeployment(String namespace, String applicationName, String imageId, Integer replicas);


    V1Deployment deploymentDetail(String namespace, String deploymentName);

    List<V1Pod> listPods(String namespace, String deploymentName);

    V1Service serviceDetail(String namespace, String serviceName);

    V1Ingress ingressDetail(String namespace, String ingressName);


    void deleteDeployment(String namespace, String deploymentName);

    void createService(String namespace, String applicationNme);

    void createService(String namespace, String serviceName, String podLabelName,
                               Integer servicePort, Integer targetPort) throws ApiException;

    void deleteService(String namespace, String serviceName);

    void listPodForAllNamespaces() throws ApiException;
    void createIngress(String namespace, String ingressName,
                              String serviceName, Integer servicePort,
                              String host, String path, String pathType,
                              boolean canary, CanaryTypeEnum canaryType, String canaryValue);

    void updateIngress(String namespace, String ingressName, String host, String path,
                              String serviceName, Integer servicePort,
                              boolean canary, CanaryTypeEnum canaryType, String canaryValue,
                              List<String> deleteAnnotations);

    void deleteIngress(String namespace, String name);

    KubernetesProperties getProperties();

}
