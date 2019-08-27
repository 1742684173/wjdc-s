package com.aloogn.wjdc.user.bean;

public class UserCard {
    private Integer userId;
    private String nickName;
    private Integer role;

    public UserCard(Integer userId) {
        this.userId = userId;
    }

    public UserCard(Integer userId, String nickName) {
        this.userId = userId;
        this.nickName = nickName;
    }

    public UserCard(Integer userId, String nickName, Integer role) {
        this.userId = userId;
        this.nickName = nickName;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
