package com.arthor.core.integration.route.constants;

import java.util.Arrays;
import java.util.List;

public class RouteConstants {

    public final static String GET_METHOD = "GET";
    public final static String POST_ROUTE_METHOD = "POST";
    public final static String PUT_ROUTE_METHOD = "PUT";
    public final static String DELETE_ROUTE_METHOD = "DELETE";
    public final static String PATCH_ROUTE_METHOD = "PATCH";
    public final static String HEAD_ROUTE_METHOD = "HEAD";
    public final static String OPTIONS_ROUTE_METHOD = "OPTIONS";
    public final static String CONNECT_ROUTE_METHOD = "CONNECT";
    public final static String TRACE_ROUTE_METHOD = "TRACE";
    public final static String DEFAULT_FORWARDING_PATH_TEMPLATE = "/$2";
    public final static String PROXY_REWRITE_PLUGIN_NAME = "proxy-rewrite";
    public final static String TRAFFIC_SPLIT_PLUGIN_NAME = "traffic-split";
    public final static Integer DEFAULT_WEIGHT_VALUE = 1;
    public final static String DEFAULT_DISCOVERY_TYPE = "roundrobin";

    public final static String GRAY_VARIABLES = "http_gray";

    public final static List<String> ALL_ROUTE_METHOD = Arrays.asList(GET_METHOD, POST_ROUTE_METHOD, PUT_ROUTE_METHOD,
            PATCH_ROUTE_METHOD, DELETE_ROUTE_METHOD, HEAD_ROUTE_METHOD, OPTIONS_ROUTE_METHOD, CONNECT_ROUTE_METHOD,
            TRACE_ROUTE_METHOD);

    public final static Integer ENABLE_ROUTE_STATUS = 1;
    public final static Integer DISABLE_ROUTE_STATUS = 0;

}
