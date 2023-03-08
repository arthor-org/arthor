package com.arthor.core.integration.route.apisix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arthor.core.common.utils.OkHttpClientUtils;
import com.arthor.core.integration.route.RouteProperties;
import com.arthor.core.integration.route.constants.RouteConstants;
import com.arthor.core.integration.route.exception.RouteOpenApiException;
import com.arthor.core.integration.route.model.CreateRouteRequest;
import com.arthor.core.integration.route.model.DeleteRouteRequest;
import com.arthor.core.integration.route.model.DetailRouteRequest;
import com.arthor.core.integration.route.model.UpdateRouteRequest;
import com.arthor.core.integration.route.service.RouteOpenApiService;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApisixRouteOpenApiService implements RouteOpenApiService {

    private final static Logger log = LoggerFactory.getLogger(ApisixRouteOpenApiService.class);
    private final OkHttpClient okHttpClient;
    private final RouteProperties routeProperties;
    public ApisixRouteOpenApiService(RouteProperties routeProperties) {
        this.routeProperties = routeProperties;
        this.okHttpClient = OkHttpClientUtils.instance();
    }

    @Override
    public void createRoute(CreateRouteRequest request) {
        ApisixCreateRouteRequest apisixCreateRouteRequest = buildApisixCreateRouteRequest(request);
        try {
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), JSON.toJSONString(apisixCreateRouteRequest,
                    SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames));
            HttpUrl url = new HttpUrl.Builder()
                    .scheme(routeProperties.getScheme())
                    .host(routeProperties.getHost())
                    .port(routeProperties.getPort())
                    .addPathSegments("/apisix/admin/routes").build();
            Request nacosUpdateRequest = new Request.Builder()
                    .addHeader(routeProperties.getAuthKey(), routeProperties.getToken())
                    .url(url)
                    .post(requestBody).build();
            Response response = okHttpClient.newCall(nacosUpdateRequest).execute();
            if (!response.isSuccessful()) {
                throw new RouteOpenApiException(response.message());
            }
        } catch (IOException e) {
            log.error("Failed to create route, request:[{}]", request, e);
            throw new RouteOpenApiException(e);
        }
    }

    @Override
    public void updateRoute(UpdateRouteRequest request) {
        try {
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), request.content());
            HttpUrl url = new HttpUrl.Builder()
                    .scheme(routeProperties.getScheme())
                    .host(routeProperties.getHost())
                    .port(routeProperties.getPort())
                    .addPathSegments("/apisix/admin/routes/" + request.getId() + "/" + request.subPath()).build();
            Request nacosUpdateRequest = new Request.Builder()
                    .addHeader(routeProperties.getAuthKey(), routeProperties.getToken())
                    .url(url)
                    .patch(requestBody).build();
            Response response = okHttpClient.newCall(nacosUpdateRequest).execute();
            if (!response.isSuccessful()) {
                throw new RouteOpenApiException(response.message());
            }
        } catch (IOException e) {
            log.error("Failed to create route, request:[{}]", request, e);
            throw new RouteOpenApiException(e);
        }
    }

    @Override
    public void deleteRoute(DeleteRouteRequest request) {
        try {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme(routeProperties.getScheme())
                    .host(routeProperties.getHost())
                    .port(routeProperties.getPort())
                    .addPathSegments("/apisix/admin/routes/" + request.getId()).build();
            Request nacosUpdateRequest = new Request.Builder()
                    .addHeader(routeProperties.getAuthKey(), routeProperties.getToken())
                    .url(url).delete().build();
            Response response = okHttpClient.newCall(nacosUpdateRequest).execute();
            if (!response.isSuccessful()) {
                throw new RouteOpenApiException(response.message());
            }
        } catch (IOException e) {
            log.error("Failed to delete route, request:[{}]", request, e);
            throw new RouteOpenApiException(e);
        }
    }

    @Override
    public RouteDetailDTO detailRoute(DetailRouteRequest request) {
        ListRouteDTO route = listRoute();
        if (Objects.isNull(route)) {
            return null;
        }
        if (CollectionUtils.isNotEmpty(route.getList())) {
            for (ListRouteDTO.ListRouteItemDTO routeItemDTO : route.getList()) {
                if (Objects.nonNull(routeItemDTO.getValue())) {
                    if (routeItemDTO.getValue().getName().equals(request.getRouteName())) {
                        return buildRouteDetailDTO(routeItemDTO);
                    }
                }
            }
        }
        return null;
    }

    private RouteDetailDTO buildRouteDetailDTO(ListRouteDTO.ListRouteItemDTO routeItemDTO) {
        RouteDetailDTO routeDetailDTO = new RouteDetailDTO();
        routeDetailDTO.setId(routeItemDTO.getKey().replace("/apisix/routes/", ""));
        routeDetailDTO.setUpstream(routeItemDTO.getValue().getUpstream());
        routeDetailDTO.setUri(routeItemDTO.getValue().getUri());
        routeDetailDTO.setMethods(routeItemDTO.getValue().getMethods());
        routeDetailDTO.setPlugins(routeItemDTO.getValue().getPlugins());
        routeDetailDTO.setName(routeItemDTO.getValue().getName());
        routeDetailDTO.setHost(routeItemDTO.getValue().getHost());
        return routeDetailDTO;
    }

    /**
     * 路由列表
     *
     * @return
     */
    private ListRouteDTO listRoute() {
        try {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme(routeProperties.getScheme())
                    .host(routeProperties.getHost())
                    .port(routeProperties.getPort())
                    .addPathSegments("/apisix/admin/routes").build();
            Request nacosUpdateRequest = new Request.Builder()
                    .addHeader(routeProperties.getAuthKey(), routeProperties.getToken())
                    .url(url)
                    .get().build();
            Response response = okHttpClient.newCall(nacosUpdateRequest).execute();
            if (!response.isSuccessful()) {
                throw new RouteOpenApiException(response.message());
            }
            return Objects.nonNull(response.body()) ?
                    JSON.parseObject(response.body().string(), ListRouteDTO.class) : null;
        } catch (Exception e) {
            log.error("Failed to list route", e);
            throw new RouteOpenApiException(e);
        }
    }

    private ApisixCreateRouteRequest buildApisixCreateRouteRequest(CreateRouteRequest request) {
        ApisixCreateRouteRequest apisixCreateRouteRequest = new ApisixCreateRouteRequest();
        apisixCreateRouteRequest.setName(request.getName());
        apisixCreateRouteRequest.setHost(request.getHost());
        apisixCreateRouteRequest.setUri(request.getRoutePath());
        apisixCreateRouteRequest.setMethods(RouteConstants.ALL_ROUTE_METHOD);
        // 改写插件是必须的
        ProxyRewritePluginDTO proxyRewritePlugin = new ProxyRewritePluginDTO();
        proxyRewritePlugin.setRegularExpression(request.getRegularExpression());
        proxyRewritePlugin.setTemplate(RouteConstants.DEFAULT_FORWARDING_PATH_TEMPLATE);
        List<String> regexUri = proxyRewritePlugin.buildRegexUri();
        proxyRewritePlugin.setRegexUri(regexUri);
        JSONObject plugins = new JSONObject();
        plugins.put(RouteConstants.PROXY_REWRITE_PLUGIN_NAME, proxyRewritePlugin);
        apisixCreateRouteRequest.setPlugins(plugins);
        apisixCreateRouteRequest.setStatus(RouteConstants.ENABLE_ROUTE_STATUS);
        UpstreamDTO upstream = new UpstreamDTO();
        ArrayList<NodeDTO> nodes = Lists.newArrayList();
        for (ResourceDTO resource : request.getResources()) {
            NodeDTO node = new NodeDTO();
            node.setHost(resource.getIp());
            node.setPort(resource.getPort());
            node.setWeight(RouteConstants.DEFAULT_WEIGHT_VALUE);
            nodes.add(node);
        }
        upstream.setNodes(nodes);
        apisixCreateRouteRequest.setUpstream(upstream);
        return apisixCreateRouteRequest;
    }

}
