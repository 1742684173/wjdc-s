package com.aloogn.wjdc.user.service;

import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.user.exception.UserException;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;

@Service
public interface UserService{

    User signIn(String account,String password) throws UserException;

    int signUp(String tel,String password,String vcode) throws UserException;

    int findPassword(String tel,String password) throws UserException;

    int modifyPassword(Integer userId,String oldPassword,String newPassword) throws UserException;

    int deleteByPrimaryKey(Integer id) throws UserException;

    int getCode(String tel,String type) throws UserException;

    boolean checkCode(String tel,String type,String vcode) throws UserException;
}
