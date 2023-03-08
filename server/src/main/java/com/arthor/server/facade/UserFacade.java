package com.arthor.server.facade;

import com.arthor.server.model.param.user.UserLoginParam;

public interface UserFacade {

    String login(UserLoginParam param);

}
