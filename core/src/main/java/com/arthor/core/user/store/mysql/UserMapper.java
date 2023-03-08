package com.arthor.core.user.store.mysql;

import com.arthor.core.user.model.UserLoginRequest;
import com.arthor.core.user.store.UserDO;

public interface UserMapper {
    UserDO login(UserLoginRequest request);
}
