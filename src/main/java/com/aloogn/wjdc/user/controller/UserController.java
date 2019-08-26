package com.aloogn.wjdc.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.aloogn.wjdc.redis.service.exception.RedisException;
import com.aloogn.wjdc.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.service.UserService;
import com.mysql.cj.util.StringUtils;
import com.aloogn.wjdc.common.JSONUtil;
import com.aloogn.wjdc.common.Tools;
import com.aloogn.wjdc.redis.service.RedisService;
import com.nimbusds.jose.JOSEException;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	UserService userService;

	@Resource(name="myRedisTakes")
	private RedisService redisBaiseTakes;
	
	/**
	 * login
	 * @return
	 */
	@PostMapping("signIn")
	public @ResponseBody JSONUtil signIn(String account, String password) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		if(StringUtils.isNullOrEmpty(account)) {
			info.setMsg("帐号不能为空");
			return info;
		}

		if(StringUtils.isNullOrEmpty(password)) {
			info.setMsg("密码不能为空");
			return info;
		}

		try{
			User user = userService.signIn(account,password);
			String userId = user.getId()+"";

			String common = request.getHeader("common");
			TreeMap commonTreeMap = Tools.getTreeMapByJsonstr(common);
			String uid = (String)commonTreeMap.get("uid");

			redisBaiseTakes.addObj(Tools.REDIS_LOGIN_ID_KEY,userId,uid);
			//httpSession.setAttribute("id",user.getId());

			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(userId);

//			// 获取生成token
//			Map<String, Object> map = new HashMap<String, Object>();
//			// 建立载荷
//			map.put("uid", request.getHeader("uid")); // 设备id
//			map.put("sign", request.getHeader("sign")); // 签名。
//			map.put("id", user.getId()+""); // 用户id
//			map.put("sta", new Date().getTime());// 生成时间
//			map.put("exp", new Date().getTime() + 30*24*60*6*1000); // 过期时间
//
//			String token = TokenUtil.creatToken(map);
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("token", token);

			//将用户登录token存入绥存
//			redisBaiseTakes.addObj(Tools.REDIS_TOKEN_KEY,user.getId()+"",token);
//			httpSession.setAttribute("id",user.getId());
//
//			info.setCode(Tools.CODE_SUCCESS);
//			info.setMsg(Tools.SUCCESS_MSG);
//			info.setData(dataMap);
		}catch (UserException ue){
			log.error("------------验证错误-----------"+ue.getMessage());
			info.setMsg(ue.getMessage());
		}
//		catch (JOSEException e) {
//			log.error("------------生成token失败-----------"+e.getMessage());
//			info.setMsg(e.getMessage());
//		}
		catch (RedisException e){
			log.error("------------缓存错误-----------"+e.getMessage());
			info.setMsg("后台维护中");
		}catch (Exception e){
			log.error("------------未知错误-----------"+e.getCause().getMessage());
			info.setMsg("未知错误");
		}

		return info;
	}

	/**
	 * signOut
	 * @return
	 */
	@PostMapping("/signOut")
	public @ResponseBody JSONUtil signOut() {
		String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
		log.debug("----------"+strId+"------------------------");
		Integer id = Integer.parseInt(strId);
		try{
			redisBaiseTakes.deletObj(Tools.REDIS_TOKEN_KEY,id+"");
		}catch (Exception e){

		}

		JSONUtil info = new JSONUtil();
	    info.setCode(Tools.CODE_SUCCESS);
		info.setMsg(Tools.SUCCESS_MSG);

		return info;
	}
	
	/**
	 * 获取验证码
	 *
	 * @param tel 手机号
	 * @return 将手机号与验证码签名返回给客户端，并设置有效时间
	 */
	@PostMapping("/getCode")
	public @ResponseBody JSONUtil getCode(String tel, String type) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		if(StringUtils.isNullOrEmpty(tel) || Tools.isMobile(tel)) {
			info.setMsg("请输入正确手机号");
			return info;
		}

		try{
			int flag = userService.getCode(tel,type);
			if(flag > 0){
				info.setCode(Tools.CODE_SUCCESS);
				info.setMsg("获取验证码成功");
			}else{
				info.setMsg("获取验证码失败");
			}
		}catch (UserException e){
			info.setMsg(e.getMessage());
		}

		return info;
	}

	@PostMapping("/signUp")
	public	@ResponseBody JSONUtil signUp(String tel, String password, String vcode) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		if(StringUtils.isNullOrEmpty(tel)) {
			info.setMsg("请输入正确手机号");
			return info;
		}

		if(StringUtils.isNullOrEmpty(password)) {
			info.setMsg("请输入密码");
			return info;
		}

		if(StringUtils.isNullOrEmpty(vcode)) {
			info.setMsg("请输入验证码");
			return info;
		}

		try{
			int flag = userService.signUp(tel,password,vcode);
			if(flag > 0){
				info.setCode(Tools.CODE_SUCCESS);
				info.setMsg("注册成功");
			}else{
				info.setMsg("注册失败");
			}
		}catch (UserException e){
			info.setMsg(e.getMessage());
		}

		return info;
	}

	@PostMapping("/findPassword")
	public	@ResponseBody JSONUtil findPassword(String tel, String password, String vcode) {

		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		if(StringUtils.isNullOrEmpty(tel) || Tools.isMobile(tel)) {
			info.setMsg("请输入正确的手机号");
			return info;
		}

		if(StringUtils.isNullOrEmpty(password)) {
			info.setMsg("请输入密码");
			return info;
		}

		try {
			String key = tel + "findPassword" + vcode;

			long nowTIme = new Date().getTime();
			long validTime = (long)redisBaiseTakes.getObj(Tools.REDIS_LOGIN_ID_KEY,key);

			//判空
			if(validTime == 0){
				info.setMsg("验证码错误");
			}else if(nowTIme > validTime){//是否过期
				info.setMsg("验证码过期");
			}else{
				// 插入数据
				int flag = userService.findPassword(tel,password);
				if (flag > 0) {
					info.setCode(Tools.CODE_SUCCESS);
					info.setMsg("找回密码成功");
				} else {// 注册失败
					info.setMsg("找回密码失败");
				}
			}

		} catch (UserException e){
			info.setMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			info.setMsg("未知错误");
		}

		return info;
	}

	@PostMapping("/modifyPassword")
	public	@ResponseBody JSONUtil modifyPassword(String oldPassword, String newPassword) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		if(StringUtils.isNullOrEmpty(oldPassword)) {
			info.setMsg("请输入原密码");
			return info;
		}

		if(StringUtils.isNullOrEmpty(newPassword)) {
			info.setMsg("请输入新密码");
			return info;
		}

		// 解析token
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();

			int flag = userService.modifyPassword(Integer.parseInt(strId),oldPassword,newPassword);
			if (flag > 0) {
				info.setCode(Tools.CODE_SUCCESS);
				info.setMsg("修改密码成功");
			} else {// 注册失败
				info.setMsg("修改密码失败");
			}
		}catch (UserException e){
			log.error("------------修改密码错误-----------"+e.getMessage());
			info.setMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			info.setMsg("未知错误");
		}

		return info;
	}


}
