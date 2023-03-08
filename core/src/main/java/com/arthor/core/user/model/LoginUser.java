package com.arthor.core.user.model;

public class LoginUser {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户id昵称
     */
    private String nickname;

    public LoginUser() {

    }
    public LoginUser(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
