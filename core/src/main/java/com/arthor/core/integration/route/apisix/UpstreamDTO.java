package com.arthor.core.integration.route.apisix;

import java.util.List;

public class UpstreamDTO {

    private List<NodeDTO> nodes;

    public List<NodeDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDTO> nodes) {
        this.nodes = nodes;
    }

}
