package com.arthor.server.mvc;

import com.alibaba.fastjson.JSON;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.user.model.LoginUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.arthor.core.common.utils.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String token = httpServletRequest.getHeader("token");
            Assert.isTrue(StringUtils.isNotBlank(token), "请登录");
            LoginUser user = decodedJWT(token);
            Assert.notNull(user, "请登录");
            WebContext.set(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        WebContext.remove();
    }

    private LoginUser decodedJWT(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ALL.SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(StringUtils.removeStart(token, ALL.JWT_TOKEN_PRE));
            String subject = decodedJWT.getSubject();
            return JSON.parseObject(subject, LoginUser.class);
        } catch (Exception e) {
            log.error("Failed to decodedJWT error:[{}]", token, e);
            return null;
        }
    }

}