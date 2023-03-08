package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TrafficSplitRuleDTO {

    @JSONField(name = "match")
    private List<TrafficSplitRuleMatchDTO> match;

    @JSONField(name = "weighted_upstreams")
    private List<TrafficSplitRuleWeightedUpstreamDTO> weightedUpstreams;

    public List<TrafficSplitRuleMatchDTO> getMatch() {
        return match;
    }

    public void setMatch(List<TrafficSplitRuleMatchDTO> match) {
        this.match = match;
    }

    public List<TrafficSplitRuleWeightedUpstreamDTO> getWeightedUpstreams() {
        return weightedUpstreams;
    }

    public void setWeightedUpstreams(List<TrafficSplitRuleWeightedUpstreamDTO> weightedUpstreams) {
        this.weightedUpstreams = weightedUpstreams;
    }
}
