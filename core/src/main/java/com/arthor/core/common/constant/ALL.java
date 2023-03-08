package com.arthor.core.common.constant;

public abstract class ALL {

    public final static String JWT_TOKEN_PRE = "Bearer ";
    public final static String SECRET = "arthor-secret";
    public final static int MAX_NUMBER_OF_CHECK = 10;
    public final static String KUBERNETES_KIND_NAMESPACE = "Namespace";
    public final static String KUBERNETES_KIND_DEPLOYMENT = "Deployment";
    public final static String KUBERNETES_KIND_SERVICE = "Service";
    public final static String KUBERNETES_KIND_INGRESS = "Ingress";
    public final static String POD = "pod";
    public final static String CANARY_HEADER_KEY = "gray";
    public final static String STABLE_SYMBOL = "stable";
    public final static String INGRESS_CLASS_NAME = "nginx";
    public final static String UNKNOWN = "unknown";
    public final static String REWRITE_TARGET_ANNOTATION = "nginx.ingress.kubernetes.io/rewrite-target";
    public final static String CANARY_ANNOTATION = "nginx.ingress.kubernetes.io/canary";
    public final static String CANARY_BY_HEADER_ANNOTATION = "nginx.ingress.kubernetes.io/canary-by-header";
    public final static String CANARY_BY_HEADER_VALUE_ANNOTATION = "nginx.ingress.kubernetes.io/canary-by-header-value";
    public final static String CANARY_WEIGHT_ANNOTATION = "nginx.ingress.kubernetes.io/canary-weight";
    public final static String DEFAULT_VALUE = "DEFAULT";
    public final static int ONE = 1;
    public final static int TWO = 2;
    public final static int THREE = 3;
    public static String buildSymbol(String app, String symbol) {
        final String format = "%s.%s";
        return String.format(format, app, symbol);
    }

    public static String resolveSymbol(String app, String symbol) {
        return symbol
                .replace(app + ".", "")
                .replace(".canary", "");
    }

    public static boolean isStable(String symbol) {
        return !symbol.contains(".canary");
    }

}
