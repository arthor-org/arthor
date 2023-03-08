package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthor.core.integration.route.constants.RouteConstants;
import com.arthor.core.integration.route.model.UpdateRouteRequest;

import java.util.List;

public class ApisixDeleteCanaryRouteRequest extends UpdateRouteRequest {

    private ProxyRewritePluginDTO proxyRewritePlugin;

    @Override
    public String subPath() {
        return "/plugins";
    }

    @Override
    public String content() {
        JSONObject patchBody = new JSONObject();
        patchBody.put(RouteConstants.TRAFFIC_SPLIT_PLUGIN_NAME, new JSONObject());
        List<String> regexUri = proxyRewritePlugin.buildRegexUri();
        proxyRewritePlugin.setRegexUri(regexUri);
        patchBody.put(RouteConstants.PROXY_REWRITE_PLUGIN_NAME, proxyRewritePlugin);
        return JSON.toJSONString(patchBody);
    }

    public void setProxyRewritePlugin(ProxyRewritePluginDTO proxyRewritePlugin) {
        this.proxyRewritePlugin = proxyRewritePlugin;
    }

    public ProxyRewritePluginDTO getProxyRewritePlugin() {
        return proxyRewritePlugin;
    }
}
