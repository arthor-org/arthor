package com.arthor.core.registry.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arthor.core.common.utils.OkHttpClientUtils;
import com.arthor.core.registry.exception.RegistryOpenApiException;
import com.arthor.core.registry.model.UpdateMetadataRequest;
import com.arthor.core.registry.service.RegistryOpenApiService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class NacosRegistryOpenApiService implements RegistryOpenApiService {

    private final static Logger log = LoggerFactory.getLogger(NacosRegistryOpenApiService.class);
    private final NacosProperties nacosProperties;
    private final OkHttpClient okHttpClient;
    public NacosRegistryOpenApiService(NacosProperties nacosProperties) {
        this.nacosProperties = nacosProperties;
        this.okHttpClient = OkHttpClientUtils.instance();
    }

    @Override
    public void update(UpdateMetadataRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", request.getName());
        jsonObject.put("deploymentName", request.getDeploymentName());
        jsonObject.put("symbol", request.getSymbol());
        jsonObject.put("group", request.getGroup());
        jsonObject.put("ip", request.getIp());
        jsonObject.put("port", request.getPort().toString());
        jsonObject.put("lifecycle", request.getLifecycle());
        jsonObject.put("canary", request.isCanary());
        jsonObject.put("canaryType", request.getCanaryType());
        jsonObject.put("canaryValue", request.getCanaryValue());
        jsonObject.put("latestTime", LocalDateTime.now());
        jsonObject.put("future", request.getFuture());
        jsonObject.put("preserved.register.source", "SPRING_CLOUD");
        try {
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), JSON.toJSONString(jsonObject,
                    SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames));
            HttpUrl url = new HttpUrl.Builder()
                .scheme(nacosProperties.getScheme())
                .host(nacosProperties.getHost())
                .port(nacosProperties.getPort())
                .addPathSegments("nacos/v1/ns/instance")
                .addQueryParameter("serviceName", request.getName())
                .addQueryParameter("ip", request.getIp())
                .addQueryParameter("port", request.getPort().toString())
                .addQueryParameter("metadata", JSON.toJSONString(jsonObject)).build();
            Request nacosUpdateRequest = new Request.Builder()
                .url(url)
                .put(requestBody).build();
            Response response = okHttpClient.newCall(nacosUpdateRequest).execute();
            if (!response.isSuccessful()) {
                throw new RegistryOpenApiException(response.message());
            }
        } catch (Exception e) {
            log.error("Failed to update nacosMetadata, metadata:[{}]", jsonObject, e);
            throw new RegistryOpenApiException(e);
        }
    }

}
