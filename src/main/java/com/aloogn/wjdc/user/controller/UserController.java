package com.aloogn.wjdc.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aloogn.webapp.utils.Constant;
import com.aloogn.webapp.utils.JSONUtil;
import com.aloogn.webapp.utils.StringUtils;
import com.aloogn.webapp.utils.TokenUtil;
import com.aloogn.wjdc.redis.service.RedisService;
import com.aloogn.wjdc.user.bean.AuthUser;
import com.aloogn.wjdc.user.bean.UserInfo;
import com.aloogn.wjdc.user.exception.UserException;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.aloogn.webapp.utils.Constant.REDIS_LOGIN_USER_UID_KEY;
import static com.aloogn.webapp.utils.StringUtils.isMobile;

@Controller
@RequestMapping("/")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

	@GetMapping("test")
	public	@ResponseBody JSONUtil test() {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);
		jsonUtil.setMsg("注册成功");
		return jsonUtil;
	}

	@PostMapping("signUp")
	public	@ResponseBody JSONUtil signUp(UserInfo userInfo) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		if(StringUtils.isNullOrEmpty(userInfo.getTel())) {
			jsonUtil.setMsg("请输入正确手机号");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(userInfo.getPassword())) {
			jsonUtil.setMsg("请输入密码");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(userInfo.getVcode())) {
			jsonUtil.setMsg("请输入验证码");
			return jsonUtil;
		}

		try{
			userService.signUp(userInfo);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("注册成功");
		}catch (UserException e){
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

	@PostMapping("signIn")
	public @ResponseBody JSONUtil signIn(String account, String password, HttpSession httpSession) {

		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		if(StringUtils.isNullOrEmpty(account)) {
			jsonUtil.setMsg("帐号不能为空");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(password)) {
			jsonUtil.setMsg("密码不能为空");
			return jsonUtil;
		}

		try{
			User user = userService.signIn(account,password);
			String uid = request.getHeader("uid");
			String version = request.getHeader("version");

			try{

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", user.getId()+""); // 用户id
				map.put("uid", uid); // app uid
				map.put("version", version); // app version
				map.put("sta", new Date().getTime());// 生成时间
				map.put("exp", new Date().getTime() + 30*24*60*6*1000); // 过期时间

				//生成token
				String token = TokenUtil.creatToken(map);

				//将登录用户的uid存入缓存,
				redisService.hashSet(Constant.REDIS_TOKEN_KEY,user.getId()+"",token);

				AuthUser authUser = new AuthUser();
				authUser.setUserId(user.getId());
				authUser.setUserName(user.getName());
				authUser.setUserNickname(user.getNikename());
				authUser.setToken(token);

				jsonUtil.setCode(200);
				jsonUtil.setMsg("登录成功");
				jsonUtil.setData(authUser);
			}catch (JOSEException e){
				jsonUtil.setMsg("登录错误");
			}
		}catch (UserException e){
			log.error("------------验证错误-----------"+e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}
		return jsonUtil;
	}

	@PostMapping("signOut")
	public @ResponseBody JSONUtil signOut(String userId) {
		userService.signOut(userId);
		return new JSONUtil(200,"成功");
	}
	
	@PostMapping("sendCode")
	public @ResponseBody JSONUtil sendCode(String tel, String type) {

		if(StringUtils.isNullOrEmpty(tel) || !isMobile(tel)) {
			return new JSONUtil(400,"请输入正确手机号");
		}

		try{
			userService.sendCode(tel,type);
			return new JSONUtil(200,"验证码己发送，请注意查收");
		}catch (UserException e){
			return new JSONUtil(400,e.getMessage());
		}
	}

	@PostMapping("findPassword")
	public	@ResponseBody JSONUtil findPassword(String tel,String password,String vcode) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		if(StringUtils.isNullOrEmpty(tel)){
			jsonUtil.setMsg("手机号不能为空");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(password)) {
			jsonUtil.setMsg("密码不能为空");
			return jsonUtil;
		}

		try{
			userService.findPassword(tel,password,vcode);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (UserException e){
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

	@PostMapping("updatePassword")
	public	@ResponseBody JSONUtil updatePassword(Integer userId, String oldPassword, String newPassword) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		if(StringUtils.isNullOrEmpty(oldPassword)) {
			jsonUtil.setMsg("原密码不能为空");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(newPassword)) {
			jsonUtil.setMsg("新密码不能为空");
			return jsonUtil;
		}

		try {
			userService.updatePassword(userId,oldPassword,newPassword);
			jsonUtil.setMsg("成功");
			jsonUtil.setCode(200);
		}catch (UserException e){
			log.error("------------修改密码错误-----------"+e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

	@PostMapping("updateTel")
	public	@ResponseBody JSONUtil updateTel(Integer userId,String tel,String password,String vcode) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		if(StringUtils.isNullOrEmpty(tel)){
			jsonUtil.setMsg("请输入手机号");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(password)){
			jsonUtil.setMsg("请输入密码");
			return jsonUtil;
		}

		if(StringUtils.isNullOrEmpty(vcode)){
			jsonUtil.setMsg("请输入验证码");
			return jsonUtil;
		}

		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setPassword(password);
			userInfo.setTel(tel);
			userInfo.setVcode(vcode);

			userService.updateTel(userInfo);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (UserException e){
			log.error("------------修改密码错误-----------"+e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

}
