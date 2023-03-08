package com.arthor.core.deploy.store.ext;

public class NativeServiceExt {

    private Integer servicePort;

    private Integer serviceTargetPort;

    private String servicePodLabelName;

    public Integer getServicePort() {
        return servicePort;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public Integer getServiceTargetPort() {
        return serviceTargetPort;
    }

    public void setServiceTargetPort(Integer serviceTargetPort) {
        this.serviceTargetPort = serviceTargetPort;
    }

    public String getServicePodLabelName() {
        return servicePodLabelName;
    }

    public void setServicePodLabelName(String servicePodLabelName) {
        this.servicePodLabelName = servicePodLabelName;
    }
}
