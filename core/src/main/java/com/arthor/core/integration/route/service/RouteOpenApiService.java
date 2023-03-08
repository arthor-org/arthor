package com.arthor.core.integration.route.service;

import com.arthor.core.integration.route.apisix.RouteDetailDTO;
import com.arthor.core.integration.route.model.CreateRouteRequest;
import com.arthor.core.integration.route.model.DeleteRouteRequest;
import com.arthor.core.integration.route.model.DetailRouteRequest;
import com.arthor.core.integration.route.model.UpdateRouteRequest;

public interface RouteOpenApiService {

    /**
     * 创建路由
     *
     * @param request
     */
    void createRoute(CreateRouteRequest request);

    /**
     * 更新路由
     *
     * @param request
     */
    void updateRoute(UpdateRouteRequest request);

    /**
     * 删除路由
     *
     * @param request
     */
    void deleteRoute(DeleteRouteRequest request);

    /**
     * 路由详情
     *
     * @param request
     */
    RouteDetailDTO detailRoute(DetailRouteRequest request);

}
