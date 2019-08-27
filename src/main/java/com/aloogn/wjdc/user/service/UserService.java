package com.aloogn.wjdc.user.service;

import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.user.exception.UserException;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;

@Service
public interface UserService{

    User signIn(String account,String password,String common) throws UserException;

    int signUp(String tel,String password,String vcode) throws UserException;

    void signOut(String userId);

    int findPassword(String tel,String password,String vcode) throws UserException;

    int updatePassword(Integer userId,String oldPassword,String newPassword) throws UserException;

    int updateTel(Integer userId,String tel,String vcode) throws UserException;

    int deleteByPrimaryKey(Integer id) throws UserException;

    int getCode(String tel,String type) throws UserException;

    boolean checkCode(String tel,String type,String vcode) throws UserException;
}
