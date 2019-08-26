package com.aloogn.wjdc.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.common.Tools;
import com.aloogn.wjdc.redis.service.RedisService;
import com.aloogn.wjdc.redis.service.exception.RedisException;
import com.aloogn.wjdc.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;
import com.aloogn.wjdc.user.mapper.UserMapper;
import com.aloogn.wjdc.user.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserMapper mapper;

//	@Resource(name="myRedisTakes")
	@Autowired
	private RedisService redisService;

	/**
	 * 注册
	 * @param tel
	 * @param password
	 * @return
	 * @throws UserException
	 */
	public int signUp(String tel,String password,String vcode)  throws UserException {
		checkCode(tel,"signUp",vcode);

		//根据手机号查询用户
		UserCriteria example = new UserCriteria();
		UserCriteria.Criteria criteria = example.createCriteria();
		criteria.andTelEqualTo(tel);

		List<User> list = mapper.selectByExample(example);

		//判断手机号是否己注册
		if(list.size()>0){
			throw new UserException("该手机号己注册");
		}

		User user = new User();
		user.setTel(tel);
		user.setPassword(password);
		return mapper.insertSelective(user);
	}

	/**
	 * 登录
	 * @param account： 可是用户名或手机号
	 * @param password
	 * @return
	 */
	public User signIn(String account,String password) throws UserException{

		UserCriteria example = new UserCriteria();
		UserCriteria.Criteria criteriaName = example.createCriteria();
		criteriaName.andNameEqualTo(account);
		criteriaName.andPasswordEqualTo(password);

		UserCriteria.Criteria criteriaTel = example.createCriteria();
		criteriaTel.andTelEqualTo(account);
		criteriaTel.andPasswordEqualTo(password);
		example.or(criteriaName);

		List<User> list = mapper.selectByExample(example);
		User user = null;
		if(list.size() == 0) {
			throw new UserException("帐号与密码不匹配");
		}else{
			user = list.get(0);
		}

		return user;
	}

	public int deleteByPrimaryKey(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}

	public int findPassword(String tel,String password) throws UserException{
		//根据手机号查询用户
		UserCriteria example = new UserCriteria();
		UserCriteria.Criteria criteria = example.createCriteria();
		criteria.andTelEqualTo(tel);

		List<User> list = mapper.selectByExample(example);

		//判断手机号是否注册
		if(list.size()==0){
			throw new UserException("该手机号未注册");
		}

		User user = new User();
		user.setPassword(password);
		user.setId(list.get(0).getId());
		return mapper.updateByPrimaryKeySelective(user);
	}

	public int modifyPassword(Integer userId,String oldPassword,String newPassword) throws UserException{
		User user = mapper.selectByPrimaryKey(userId);

		//判断手机号是否注册
		if(null == user || oldPassword.equals(user.getPassword())){
			throw new UserException("原密码不正确");
		}

		user = new User();
		user.setPassword(newPassword);
		user.setId(userId);
		return mapper.updateByPrimaryKeySelective(user);
	}

	public int getCode(String tel,String type) throws UserException{
		try {
			String key = tel+type;
			Map<String,String> map = (Map<String,String>)redisService.getObj(Tools.REDIS_GET_CODE_KEY,key);

			long nowTime = new Date().getTime();
			if(null != map){
				long rTime = Long.parseLong(map.get("time"));
				if(rTime > nowTime){
					throw new UserException("你的验证码己发送，请注意查收");
				}
			}

			// 获取验证码
			String vcode = Tools.getCode();
			String msg = String.format("【爱生活】，验证码：{0},五分钟后失效，如果不是你请求请忽略", vcode);
			System.out.println(type+"验证码是："+msg);

			map = new HashMap<>();
			map.put("vcode",vcode);
			map.put("time",(nowTime + 5*6*1000)+"");
			redisService.addObj(Tools.REDIS_GET_CODE_KEY,key,map);
		} catch (UserException e){
			throw new UserException(e.getMessage());
		}catch (RedisException e){
			log.error("------------缓存错误-----------"+e.getMessage());
			throw new UserException("缓存错误");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("未知错误");
		}

		return 1;
	}

	public boolean checkCode(String tel,String type,String vcode) throws UserException{
		String key = tel+type;
		Map<String,String> map = null;

		try {
			map = (Map<String,String>)redisService.getObj(Tools.REDIS_GET_CODE_KEY,key);
		} catch (RedisException e) {
			e.printStackTrace();
			throw new UserException("缓存错误");
		}

		if(null == map){
			throw new UserException("验证码错误");
		}

		long rTime = Long.parseLong(map.get("time"));
		String rVcode = (String)map.get("vcode");
		long nowTime = new Date().getTime();

		if(rTime < nowTime){
			throw new UserException("验证码过期");
		}

		if(!vcode.equals(rVcode)){
			throw new UserException("验证码错误");
		}

		return true;
	}
}
