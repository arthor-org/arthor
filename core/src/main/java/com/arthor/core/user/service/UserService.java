package com.arthor.core.user.service;

import com.arthor.core.user.model.UserLoginRequest;

public interface UserService {
    /**
     * 登陆
     *
     * @param request
     * @return
     */
    String login(UserLoginRequest request);
}
