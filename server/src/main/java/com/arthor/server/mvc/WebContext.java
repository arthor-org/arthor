package com.arthor.server.mvc;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.arthor.core.user.model.LoginUser;
import com.arthor.core.common.utils.Assert;

public class WebContext {

    private static final TransmittableThreadLocal<LoginUser> CONTEXT = new TransmittableThreadLocal();

    public static LoginUser get() {
        return CONTEXT.get();
    }

    public static void set(LoginUser loginUser) {
        CONTEXT.set(loginUser);
    }

    public static void remove() {
        CONTEXT.remove();
    }

    public static Long getUserId() {
        LoginUser loginUser = CONTEXT.get();
        Assert.notNull(loginUser, "请登录");
        return loginUser.getId();
    }

    public static String getNickname() {
        LoginUser loginUser = CONTEXT.get();
        Assert.notNull(loginUser, "请登录");
        return loginUser.getNickname();
    }

    public static LoginUser loginUser() {
        LoginUser loginUser = CONTEXT.get();
        Assert.notNull(loginUser, "请登录");
        return loginUser;
    }

}
