package com.arthor.core.integration.route.apisix;

import java.util.List;

public class TrafficSplitRuleMatchDTO {

    private List<List<String>> vars;

    public List<List<String>> getVars() {
        return vars;
    }

    public void setVars(List<List<String>> vars) {
        this.vars = vars;
    }
}