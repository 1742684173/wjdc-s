package com.aloogn.wjdc.user.service;

import com.aloogn.wjdc.user.bean.UserInfo;
import com.aloogn.wjdc.user.exception.UserException;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.user.bean.User;

@Service
public interface UserService{
    void signUp(UserInfo userInfo) throws UserException;

    User signIn(String account,String password) throws UserException;

    void signOut(String userId);

    void findPassword(String tel,String password,String vcode) throws UserException;

    void updatePassword(Integer userId,String oldPassword,String newPassword) throws UserException;

    void updateTel(UserInfo userInfo) throws UserException;

    void sendCode(String tel,String type) throws UserException;

}
