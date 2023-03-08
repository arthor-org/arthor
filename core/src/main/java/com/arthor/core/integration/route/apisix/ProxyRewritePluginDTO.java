package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 代理改写插件
 */
public class ProxyRewritePluginDTO {

    @JSONField(serialize = false, deserialize = false)
    private String regularExpression;
    @JSONField(serialize = false, deserialize = false)
    private String template;
    @JSONField(name = "regex_uri")
    private List<String> regexUri;

    public List<String> getRegexUri() {
        return regexUri;
    }

    public void setRegexUri(List<String> regexUri) {
        this.regexUri = regexUri;
    }

    public String getRegularExpression() {
        return this.regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<String> buildRegexUri() {
        List<String> ret = Lists.newArrayList();
        ret.add(regularExpression);
        ret.add(template);
        return ret;
    }

    public void loadFrom() {
        if (CollectionUtils.isNotEmpty(regexUri)) {
            setRegularExpression(regexUri.get(0));
            setTemplate(regexUri.get(1));
        }
    }

}
