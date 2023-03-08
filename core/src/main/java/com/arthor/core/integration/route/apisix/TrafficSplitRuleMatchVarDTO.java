package com.arthor.core.integration.route.apisix;

import com.google.common.collect.Lists;

import java.util.List;

public class TrafficSplitRuleMatchVarDTO {

    /**
     * 变量
     */
    private String variable;
    /**
     * 操作符
     */
    private String operator;
    /**
     * 值
     */
    private String value;

    public TrafficSplitRuleMatchVarDTO() {

    }

    public TrafficSplitRuleMatchVarDTO(List<String> vars) {
        this.setVariable(vars.get(0));
        this.setOperator(vars.get(1));
        this.setValue(vars.get(2));
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> build() {
        List<String> stringList = Lists.newArrayList();
        stringList.add(variable);
        stringList.add(operator);
        stringList.add(value);
        return stringList;
    }

    @Override
    public String toString() {
        return build().toString();
    }


}
