package com.arthor.core.integration.route.apisix;

public class TrafficSplitRuleWeightedUpstreamDTO {

    private UpstreamDTO upstream;

    private Integer weight;

    public UpstreamDTO getUpstream() {
        return upstream;
    }

    public void setUpstream(UpstreamDTO upstream) {
        this.upstream = upstream;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
