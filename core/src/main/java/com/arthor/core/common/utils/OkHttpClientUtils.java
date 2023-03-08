package com.arthor.core.common.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public abstract class OkHttpClientUtils {

    private final OkHttpClient okHttpClient;

    private OkHttpClientUtils() {
        this.okHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
    }

    public void put(String url, Object parameter) {
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(parameter));
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive")
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OkHttpClient instance() {
        return new OkHttpClient();
    }



}
