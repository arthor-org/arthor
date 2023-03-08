package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ListRouteDTO {

    private List<ListRouteItemDTO> list;

    public static class ListRouteItemDTO {

        private String key;
        private ListRouteItemValueDTO value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ListRouteItemValueDTO getValue() {
            return value;
        }

        public void setValue(ListRouteItemValueDTO value) {
            this.value = value;
        }
    }

    public static class ListRouteItemValueDTO {

        private UpstreamDTO upstream;
        private String uri;
        private List<String> methods;
        private JSONObject plugins;
        private String name;
        private String host;

        public JSONObject getPlugins() {
            return plugins;
        }

        public void setPlugins(JSONObject plugins) {
            this.plugins = plugins;
        }

        public UpstreamDTO getUpstream() {
            return upstream;
        }

        public void setUpstream(UpstreamDTO upstream) {
            this.upstream = upstream;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

    public List<ListRouteItemDTO> getList() {
        return list;
    }

    public void setList(List<ListRouteItemDTO> list) {
        this.list = list;
    }
}
