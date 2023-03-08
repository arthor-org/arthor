package com.arthor.core.integration.kubernetes.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.arthor.core.common.constant.ALL.*;

public class IngressPatchJson {

    private IngressMetadata metadata;
    private IngressSpec spec;

    public static class IngressMetadata {

        private Map<String, String> annotations;

        public IngressMetadata(Map<String, String> annotations) {
            this.annotations = annotations;
        }

        public Map<String, String> getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Map<String, String> annotations) {
            this.annotations = annotations;
        }
    }

    public static class IngressSpec {

        private String ingressClassName;
        private List<IngressRule> rules;

        public IngressSpec(String ingressClassName, List<IngressRule> rules) {
            this.ingressClassName = ingressClassName;
            this.rules = rules;
        }

        public String getIngressClassName() {
            return ingressClassName;
        }

        public void setIngressClassName(String ingressClassName) {
            this.ingressClassName = ingressClassName;
        }

        public List<IngressRule> getRules() {
            return rules;
        }

        public void setRules(List<IngressRule> rules) {
            this.rules = rules;
        }
    }

    public static class IngressRule {

        private String host;
        private IngressHttp http;

        public IngressRule(String host, IngressHttp http) {
            this.host = host;
            this.http = http;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public IngressHttp getHttp() {
            return http;
        }

        public void setHttp(IngressHttp http) {
            this.http = http;
        }
    }

    public static class IngressHttp {

        private List<IngressPath> paths;

        public IngressHttp(List<IngressPath> paths) {
            this.paths = paths;
        }

        public List<IngressPath> getPaths() {
            return paths;
        }

        public void setPaths(List<IngressPath> paths) {
            this.paths = paths;
        }
    }

    public static class IngressPath {

        private String path;
        private String pathType;
        private IngressBackend backend;

        public IngressPath(String path, String pathType, IngressBackend backend) {
            this.path = path;
            this.pathType = pathType;
            this.backend = backend;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPathType() {
            return pathType;
        }

        public void setPathType(String pathType) {
            this.pathType = pathType;
        }

        public IngressBackend getBackend() {
            return backend;
        }

        public void setBackend(IngressBackend backend) {
            this.backend = backend;
        }
    }

    public static class IngressBackend {

        private IngressService service;

        public IngressBackend(IngressService service) {
            this.service = service;
        }

        public IngressService getService() {
            return service;
        }

        public void setService(IngressService service) {
            this.service = service;
        }
    }



    public static class IngressService {
        /**
         * 服务名称
         */
        private String name;
        /**
         * 服务端口
         */
        private ServicePort port;

        public IngressService(String name, ServicePort port) {
            this.name = name;
            this.port = port;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ServicePort getPort() {
            return port;
        }

        public void setPort(ServicePort port) {
            this.port = port;
        }
    }

    public static class ServicePort {

        private Integer number;

        public ServicePort(Integer number) {
            this.number = number;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    /**
     * 该Patch基于host-path作为纬度的
     *
     * @return
     */
    public static String patchString(String host, String path, String pathType, String serviceName, Integer servicePortNumber,
                                     boolean canary, CanaryTypeEnum canaryType, String canaryValue,
                                     List<String> deleteAnnotations) {
        List<IngressPath> paths = Lists.newArrayList();
        ServicePort servicePort = new ServicePort(servicePortNumber);
        IngressService ingressService = new IngressService(serviceName, servicePort);
        IngressBackend ingressBackend = new IngressBackend(ingressService);
        IngressPath ingressPath = new IngressPath(path, pathType, ingressBackend);
        paths.add(ingressPath);
        HashMap<String, String> annotations = Maps.newHashMap();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/$2");
        if (canary) {
            annotations.put("nginx.ingress.kubernetes.io/canary", "true");
            if (CanaryTypeEnum.HEADER == canaryType) {
                annotations.put("nginx.ingress.kubernetes.io/canary-by-header", CANARY_HEADER_KEY);
                annotations.put("nginx.ingress.kubernetes.io/canary-by-header-value", canaryValue);
            } else if (CanaryTypeEnum.WEIGHT == canaryType) {
                annotations.put("nginx.ingress.kubernetes.io/canary-weight", canaryValue);
            }
            deleteAnnotations.forEach(a -> annotations.put(a, null));
        }
        IngressHttp ingressHttp = new IngressHttp(paths);
        IngressRule ingressRule = new IngressRule(host, ingressHttp);
        IngressSpec ingressSpec = new IngressSpec(INGRESS_CLASS_NAME, List.of(ingressRule));
        IngressMetadata ingressMetadata = new IngressMetadata(annotations);
        IngressPatchJson ingressPatchJson = new IngressPatchJson();
        ingressPatchJson.setSpec(ingressSpec);
        ingressPatchJson.setMetadata(ingressMetadata);
        return JSON.toJSONString(ingressPatchJson,
                SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
    }

    public IngressMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(IngressMetadata metadata) {
        this.metadata = metadata;
    }

    public IngressSpec getSpec() {
        return spec;
    }

    public void setSpec(IngressSpec spec) {
        this.spec = spec;
    }
}
