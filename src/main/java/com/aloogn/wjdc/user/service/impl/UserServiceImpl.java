package com.aloogn.wjdc.user.service.impl;

import java.util.*;

import com.aloogn.webapp.utils.TokenUtil;
import com.aloogn.wjdc.redis.service.RedisService;
import com.aloogn.webapp.utils.Constant;
import com.aloogn.webapp.utils.ConvertUtil;
import com.aloogn.webapp.utils.StringUtils;
import com.aloogn.wjdc.user.bean.UserInfo;
import com.aloogn.wjdc.user.exception.UserException;
import com.nimbusds.jose.JOSEException;
import com.oracle.javafx.jmx.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;
import com.aloogn.wjdc.user.mapper.UserMapper;
import com.aloogn.wjdc.user.service.UserService;

import static com.aloogn.webapp.utils.SecureUtil.MD5;


@Service
public class UserServiceImpl implements UserService{
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserMapper mapper;

	@Autowired
	private RedisService redisService;

	@Override
	public void signUp(UserInfo userInfo) throws UserException{
		//检查手机号是否占用
		List<User> list = selectUserByTel(userInfo.getTel());
		if(list.size()>0){
			throw new UserException("手机号己注册");
		}

		//检查验证码
		checkCode(userInfo.getTel(),"signUp",userInfo.getVcode());

		mapper.insertSelective(userInfo);
	}

	@Override
	public User signIn(String account,String password) throws UserException{
		//分别检查tel,name，一个成功就可以
		List<User> list = selectUserByTel(account);
		if(list.size() <= 0){
			list = selectUserByName(account);
			if(list.size() <= 0){
				throw new UserException("帐号不存在");
			}
		}

		if(list.size() > 1){
			throw new UserException("存在多个帐号,请联系管理员");
		}

		//检查密码
		User user = list.get(0);
		if(!user.getPassword().equals(password)){
			throw new UserException("密码错误");
		}

		//获取登录平台的uid
//		TreeMap commonTreeMap = ConvertUtil.getTreeMapByJsonstr(common);
//		String uid = (String)commonTreeMap.get("uid");
		//存入登录uid，主要是区分不同平台登录的，弹下线
//		redisService.hashSet(Constant.REDIS_LOGIN_USER_UID_KEY,user.getId()+"",uid);

		return user;
	}

	@Override
	public void signOut(String userId){
		redisService.hashDelete(Constant.REDIS_TOKEN_KEY,userId);
	}

	@Override
	public void findPassword(String tel,String password,String vcode) throws UserException{
		//检查该手机号是否注册
		List<User> list = selectUserByTel(tel);
		if(list.size()==0){
			throw new UserException("手机号错误");
		}

		if(list.size() > 1){
			throw new UserException("多个帐号绑定该手机号,请联系管理员");
		}

		//检查验证码
		checkCode(tel,"findPassword",vcode);

		//更改密码
		User user = new User();
		user.setTel(tel);
		user.setPassword(password);
		mapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void updatePassword(Integer userId,String oldPassword,String newPassword) throws UserException{
		//检查原密码是否正确
		User user = mapper.selectByPrimaryKey(userId);
		if(null == user || !user.getPassword().equals(oldPassword)){
			throw new UserException("原密码不正确");
		}

		//删除token，需要重新登录
		redisService.hashDelete(Constant.REDIS_TOKEN_KEY,userId+"");

		//更改密码
		user = new User();
		user.setId(userId);
		user.setPassword(newPassword);
		mapper.updateByPrimaryKeySelective(user);
	}

	@Override
    public void updateTel(UserInfo userInfo) throws UserException{
		User user = mapper.selectByPrimaryKey(userInfo.getId());
		if(!user.getPassword().equals(userInfo.getPassword())){
			throw new UserException("密码错误");
		}

		//判断手机号是否注册
		List<User> list = selectUserByTel(userInfo.getTel());
		if(list.size() > 0){
			throw new UserException("该手机号己注册");
		}

		//检查验证码
		checkCode(userInfo.getTel(),"updateTel",userInfo.getVcode());

		//删除token，需要重新登录
		redisService.hashDelete(Constant.REDIS_TOKEN_KEY,userInfo.getId()+"");

		mapper.updateByPrimaryKeySelective(userInfo);
    }

	@Override
	public void sendCode(String tel,String type) throws UserException{
		String key = "code:"+tel+":"+type;
		String rCode = (String) redisService.get(key);
		if(!StringUtils.isNullOrEmpty(rCode)){
			throw new UserException("验证码己发送，请注意查收");
		}

		// 获取验证码
		String vcode = String.valueOf((int)(Math.random()*900000)+100000);
		String msg = String.format("【爱生活】，验证码：{%s},五分钟后失效，如果不是你请求请忽略", vcode);
		log.debug(msg);
		//sendSMS(tel,msg);
		redisService.set(key,MD5(vcode),5L);
	}

	private void checkCode(String tel,String type,String vCode) throws UserException{
		String key = "code:"+tel+":"+type;
		String rCode = (String)redisService.get(key);

		if(StringUtils.isNullOrEmpty(rCode)){
			throw new UserException("还未获取验证码或验证码过期");
		}

		if(!rCode.equals(MD5(vCode))){
			throw new UserException("验证码错误");
		}

		//删除缓存验证码
		redisService.remove(key);
	}

	private List<User> selectUserByTel(String tel){
		UserCriteria example = new UserCriteria();
		example.createCriteria().andTelEqualTo(tel);

		return mapper.selectByExample(example);
	}

	private List<User> selectUserByName(String name){
		UserCriteria example = new UserCriteria();
		example.createCriteria().andNameEqualTo(name);

		return mapper.selectByExample(example);
	}

	private List<User> selectUserByEmail(String email){
		UserCriteria example = new UserCriteria();
		example.createCriteria().andEmailEqualTo(email);

		return mapper.selectByExample(example);
	}




}
