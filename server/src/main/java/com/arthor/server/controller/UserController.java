package com.arthor.server.controller;

import com.arthor.server.common.R;
import com.arthor.server.facade.UserFacade;
import com.arthor.server.model.param.user.UserLoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("login")
    public R<String> login(@RequestBody @Valid UserLoginParam param) {
        return R.success(userFacade.login(param));
    }

}
