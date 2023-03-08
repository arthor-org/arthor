package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSON;
import com.arthor.core.integration.route.model.UpdateRouteRequest;

import java.util.List;

public class ApisixUpdateCanaryRouteRequest extends UpdateRouteRequest {

    private List<TrafficSplitRuleDTO> rules;

    @Override
    public String subPath() {
        return "/plugins/traffic-split";
    }

    @Override
    public String content() {
        PatchBody patchBody = new PatchBody();
        patchBody.setRules(getRules());
        return JSON.toJSONString(patchBody);
    }

    public List<TrafficSplitRuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<TrafficSplitRuleDTO> rules) {
        this.rules = rules;
    }

    public static class PatchBody {

        private List<TrafficSplitRuleDTO> rules;

        public List<TrafficSplitRuleDTO> getRules() {
            return rules;
        }

        public void setRules(List<TrafficSplitRuleDTO> rules) {
            this.rules = rules;
        }
    }

}
