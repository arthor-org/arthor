package com.arthor.server.facade.impl;

import com.arthor.core.user.model.UserLoginRequest;
import com.arthor.core.user.service.UserService;
import com.arthor.server.facade.UserFacade;
import com.arthor.server.model.param.user.UserLoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public String login(UserLoginParam param) {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername(param.getUsername());
        userLoginRequest.setPassword(param.getPassword());
        return userService.login(userLoginRequest);
    }
}
