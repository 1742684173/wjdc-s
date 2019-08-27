package com.aloogn.wjdc.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.aloogn.wjdc.user.bean.UserCard;
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

import org.springframework.web.bind.annotation.*;

import static com.aloogn.wjdc.common.Tools.*;

@Controller
@RequestMapping("/")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	UserService userService;

//	@Resource(name="myRedisTakes")
//	private RedisService redisBaiseTakes;

	@PostMapping("signIn")
	public @ResponseBody JSONUtil signIn(String account, String password) {

		if(StringUtils.isNullOrEmpty(account)) {
			return new JSONUtil(CODE_ERROR,"帐号不能为空");
		}

		if(StringUtils.isNullOrEmpty(password)) {
			return new JSONUtil(CODE_ERROR,"帐号不能为空");
		}

		try{
			String common = request.getHeader("common");
			User user = userService.signIn(account,password,common);
			//httpSession.setAttribute("id",user.getId());

			return new JSONUtil(CODE_SUCCESS,"登录成功",user.getId().toString());
		}catch (UserException ue){
			log.error("------------验证错误-----------"+ue.getMessage());
			return new JSONUtil(CODE_ERROR,ue.getMessage());
		}
	}

	@PostMapping("/signOut")
	public @ResponseBody JSONUtil signOut() {
		String userId = (String)request.getAttribute(REQUEST_USER_ID_KEY);
		userService.signOut(userId);
		return new JSONUtil(CODE_SUCCESS,"成功");
	}
	
	@PostMapping("/getCode")
	public @ResponseBody JSONUtil getCode(String tel, String type) {

		if(StringUtils.isNullOrEmpty(tel) || !Tools.isMobile(tel)) {
			return new JSONUtil(CODE_ERROR,"请输入正确手机号");
		}

		try{
			int flag = userService.getCode(tel,type);
			if(flag > 0){
				return new JSONUtil(CODE_SUCCESS,"验证码己发送，请注意查收");
			}else{
				return new JSONUtil(CODE_ERROR,"获取验证码失败");
			}
		}catch (UserException e){
			return new JSONUtil(CODE_ERROR,e.getMessage());
		}
	}

	@PostMapping("/signUp")
	public	@ResponseBody JSONUtil signUp(String tel, String password, String vcode) {

		if(StringUtils.isNullOrEmpty(tel) || !isMobile(tel)) return new JSONUtil(CODE_ERROR,"请输入正确手机号");

		if(StringUtils.isNullOrEmpty(password)) return new JSONUtil(CODE_ERROR,"请输入密码");

		if(StringUtils.isNullOrEmpty(vcode)) return new JSONUtil(CODE_ERROR,"请输入验证码");

		try{
			int flag = userService.signUp(tel,password,vcode);
			if(flag > 0){
				return new JSONUtil(CODE_SUCCESS,"注册成功");
			}else{
				return new JSONUtil(CODE_ERROR,"注册失败");
			}
		}catch (UserException e){
			return new JSONUtil(CODE_ERROR,e.getMessage());
		}
	}

	@PostMapping("/findPassword")
	public	@ResponseBody JSONUtil findPassword(String tel, String password, String vcode) {

		if(StringUtils.isNullOrEmpty(tel) || !Tools.isMobile(tel))return new JSONUtil(CODE_ERROR,"请输入正确手机号");

		if(StringUtils.isNullOrEmpty(password)) return new JSONUtil(CODE_ERROR,"请输入密码");

		try{
			int flag = userService.findPassword(tel,password,vcode);
			if(flag > 0){
				return new JSONUtil(CODE_SUCCESS,"成功");
			}else{
				return new JSONUtil(CODE_ERROR,"失败");
			}
		}catch (UserException e){
			return new JSONUtil(CODE_ERROR,e.getMessage());
		}

	}

	@PostMapping("/updatePassword")
	public	@ResponseBody JSONUtil updatePassword(String oldPassword, String newPassword) {
		if(StringUtils.isNullOrEmpty(oldPassword)) return new JSONUtil(CODE_ERROR,"请输入原密码");

		if(StringUtils.isNullOrEmpty(newPassword)) return new JSONUtil(CODE_ERROR,"请输入新密码");

		// 解析token
		try {
			String strId = (String)request.getAttribute(REQUEST_USER_ID_KEY);

			int flag = userService.updatePassword(Integer.parseInt(strId),oldPassword,newPassword);
			if (flag > 0) {
				return new JSONUtil(CODE_SUCCESS,"修改成功");
			} else {// 注册失败
				return new JSONUtil(CODE_ERROR,"修改失败");
			}
		}catch (UserException e){
			log.error("------------修改密码错误-----------"+e.getMessage());
			return new JSONUtil(CODE_ERROR,e.getMessage());
		}

	}

	@PostMapping("/updateTel")
	public	@ResponseBody JSONUtil updateTel(String tel, String vcode) {
		if(StringUtils.isNullOrEmpty(tel) || !isMobile(tel)) return new JSONUtil(CODE_ERROR,"请输入手机号");

		if(StringUtils.isNullOrEmpty(vcode)) return new JSONUtil(CODE_ERROR,"请输入验证码");

		try {
			String strId = (String)request.getAttribute(REQUEST_USER_ID_KEY);

			int flag = userService.updateTel(Integer.parseInt(strId),tel,vcode);
			if (flag > 0) {
				return new JSONUtil(CODE_SUCCESS,"修改成功");
			} else {// 注册失败
				return new JSONUtil(CODE_ERROR,"修改失败");
			}
		}catch (UserException e){
			log.error("------------修改密码错误-----------"+e.getMessage());
			return new JSONUtil(CODE_ERROR,e.getMessage());
		}

	}

}
