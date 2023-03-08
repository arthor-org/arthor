package com.arthor.server.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一Result数据结构对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final int DEFAULT_HTTP_CODE = 400;
    private static final String SUCCESS_ERROR_CODE = "200";
    private static final String DEFAULT_ERROR_CODE = "201";

    protected T content;
    protected String errorCode;
    protected String errorMsg;


    public static R success() {
        return R.builder()
                .errorCode(SUCCESS_ERROR_CODE)
                .build();
    }

    public static <T> R success(T data) {
        return R.builder()
                .errorCode(SUCCESS_ERROR_CODE)
                .content(data)
                .build();
    }

    public static R error() {
        return R.builder()
                .errorCode(DEFAULT_ERROR_CODE)
                .build();
    }

    public static R error(String errorMsg) {
        return R.builder()
                .errorCode(DEFAULT_ERROR_CODE)
                .errorMsg(errorMsg)
                .build();
    }

    public static R error(String errorCode, String errorMsg) {
        return R.builder()
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .build();
    }

}