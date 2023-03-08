package com.arthor.core.integration.route.apisix;

import java.util.List;

/**
 * 流量切分插件
 */
public class TrafficSplitPluginDTO {

    private List<TrafficSplitRuleDTO> rules;

    public List<TrafficSplitRuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<TrafficSplitRuleDTO> rules) {
        this.rules = rules;
    }


}