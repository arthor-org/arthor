package com.arthor.core.user.service;

import com.alibaba.fastjson.JSON;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.user.DefaultUser;
import com.arthor.core.user.User;
import com.arthor.core.user.model.UserLoginRequest;
import com.arthor.core.user.store.UserDO;
import com.arthor.core.user.store.UserStore;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.arthor.core.common.utils.Assert;

import java.util.Calendar;

public class DefaultUserService implements UserService {

    private final UserStore userStore;

    public DefaultUserService(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public String login(UserLoginRequest request) {
        UserDO entity = userStore.login(request);
        Assert.notNull(entity, "账号密码错误");
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        return getToken(convert(entity));
    }

    private String getToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 10);
        return ALL.JWT_TOKEN_PRE.concat(JWT.create()
                .withSubject(tokenString(user))
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(ALL.SECRET)));
    }

    public String tokenString(User user) {
        DefaultUser target = new DefaultUser();
        target.setId(user.getId());
        target.setUsername(user.getUsername());
        return JSON.toJSONString(target);
    }

    private User convert(UserDO entity) {
        DefaultUser user = new DefaultUser();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setNickname(entity.getNickname());
        user.setCreateTime(entity.getCreateTime());
        return user;
    }

}
