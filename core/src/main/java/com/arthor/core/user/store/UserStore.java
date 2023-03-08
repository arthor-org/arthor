package com.arthor.core.user.store;

import com.arthor.core.user.model.UserLoginRequest;

public interface UserStore {

    UserDO login(UserLoginRequest request);
}
