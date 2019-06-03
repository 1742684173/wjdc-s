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
    UserMapper mapper;

	public long countByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return mapper.countByExample(example);
	}

	public int deleteByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return mapper.deleteByExample(example);
	}

	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	public int insert(User record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	public int insertSelective(User record) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	public List<User> selectByExample(UserCriteria example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	public int updateByExampleSelective(User record, UserCriteria example) {
		// TODO Auto-generated method stub
		return mapper.updateByExample(record, example);
	}

	public int updateByExample(User record, UserCriteria example) {
		// TODO Auto-generated method stub
		return mapper.updateByExample(record, example);
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
