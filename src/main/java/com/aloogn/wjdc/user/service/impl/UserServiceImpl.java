package com.aloogn.wjdc.user.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;
import com.aloogn.wjdc.user.mapper.UserMapper;
import com.aloogn.wjdc.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    UserMapper userMapper;

	public List<User> signIn(String account,String password) throws Exception {
				
		UserCriteria example = new UserCriteria();
		UserCriteria.Criteria criteriaName = example.createCriteria();
		criteriaName.andNameEqualTo(account);
		criteriaName.andPasswordEqualTo(password);
		
		UserCriteria.Criteria criteriaTel = example.createCriteria();
		criteriaTel.andTelEqualTo(account);
		criteriaTel.andPasswordEqualTo(password);
		example.or(criteriaName);
		
		UserCriteria.Criteria criteriaEmail = example.createCriteria();
		criteriaEmail.andTelEqualTo(account);
		criteriaEmail.andPasswordEqualTo(password);
		example.or(criteriaEmail);
		
		
		return userMapper.selectByExample(example);
	}

	public long countByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insertSelective(User record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<User> selectByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return null;
	}

	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int updateByExampleSelective(User record, UserCriteria example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByExample(User record, UserCriteria example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKeySelective(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKey(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
    
    
}
