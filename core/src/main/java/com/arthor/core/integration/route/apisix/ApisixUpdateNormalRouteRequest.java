package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSON;
import com.arthor.core.integration.route.constants.RouteConstants;
import com.arthor.core.integration.route.model.UpdateRouteRequest;

import java.util.List;

public class ApisixUpdateNormalRouteRequest extends UpdateRouteRequest {

    private List<NodeDTO> nodes;

    @Override
    public String subPath() {
        return "/upstream";
    }

    @Override
    public String content() {
        PatchBody patchBody = new PatchBody();
        patchBody.setNodes(this.nodes);
        patchBody.setType(RouteConstants.DEFAULT_DISCOVERY_TYPE);
        return JSON.toJSONString(patchBody);
    }

    public List<NodeDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDTO> nodes) {
        this.nodes = nodes;
    }

    public static class PatchBody {

        private List<NodeDTO> nodes;

        private String type;

        public List<NodeDTO> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodeDTO> nodes) {
            this.nodes = nodes;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
