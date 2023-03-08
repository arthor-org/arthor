package com.arthor.core.user.store.mysql;

import com.arthor.core.user.model.UserLoginRequest;
import com.arthor.core.user.store.UserDO;
import com.arthor.core.user.store.UserStore;

public class MysqlUserStore implements UserStore {

    private final UserMapper userMapper;

    public MysqlUserStore(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDO login(UserLoginRequest request) {
        return userMapper.login(request);
    }
}
