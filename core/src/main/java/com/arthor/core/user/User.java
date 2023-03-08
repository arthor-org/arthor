package com.arthor.core.user;

import java.time.LocalDateTime;

public interface User {

    Long getId();

    void setId(Long id);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getNickname();

    void setNickname(String nickname);

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

}
