package com.aloogn.wjdc.user.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloogn.wjdc.user.bean.User;
import com.aloogn.wjdc.user.bean.UserCriteria;
import com.aloogn.wjdc.user.service.UserService;
import com.mysql.cj.util.StringUtils;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.TokenUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.redis.service.RedisService;
import com.nimbusds.jose.JOSEException;

import net.minidev.json.JSONObject;

@Controller
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
	@RequestMapping(value = "/user/signIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signIn(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			//登录帐号
			String account = (String)mapParams.get("account");
			//登录密码
			String password = (String)mapParams.get("password");
			
			if(StringUtils.isNullOrEmpty(account)) {
				throw new Exception("帐号不能为空");
			}
			
			if(StringUtils.isNullOrEmpty(password)) {
				throw new Exception("密码不能为空");
			}
			
			//根据帐户先查缓存
			List<User> users = userService.signIn(account,password);
			
			if (users.size() == 0) {
				info.setMsg("帐号与密码不匹配");
			} else{
				// 获取生成token
				Map<String, Object> map = new HashMap<String, Object>();
				// 建立载荷
				map.put("uid", request.getHeader("uid")); // 设备id
				map.put("sign", request.getHeader("sign")); // 签名。
				map.put("id", users.get(0).getId()+""); // 用户id
				map.put("sta", new Date().getTime());// 生成时间
				map.put("exp", new Date().getTime() + 30*24*60*6*1000); // 过期时间
				
				String token = TokenUtil.creatToken(map);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("token", token);
				
				//将用户登录token存入绥存
				redisBaiseTakes.addObj(Tools.REDIS_TOKEN_KEY,users.get(0).getId()+"",token);
				
				info.setCode(Tools.CODE_SUCCESS);
				info.setMsg(Tools.SUCCESS_MSG);
				info.setData(dataMap);
			}
		}catch (JOSEException e) {
			log.debug("------------生成token失败-----------");
			info.setMsg(e.getMessage());
		} catch (Exception e) {
			info.setMsg(e.getMessage());
		}

		return info.result();
	}

	/**
	 * signOut
	 * @return
	 */
	@RequestMapping(value = "/user/signOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signOut() {
		String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
		log.debug("----------"+strId+"------------------------");
		Integer id = Integer.parseInt(strId);
		redisBaiseTakes.deletObj(Tools.REDIS_TOKEN_KEY,id+"");
				
		JSONUtil info = new JSONUtil();
	    info.setCode(Tools.CODE_SUCCESS);
		info.setMsg(Tools.SUCCESS_MSG);

		return info.result();
	}
	
	/**
	 * 获取验证码
	 * 
	 * @param account 邮箱/手机号
	 * @param type    0:邮箱创建用户 1:手机号创始用户 2:邮箱找回密码 3:手机号找回密码
	 * @return
	 */
	@RequestMapping(value = "/user/getCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCode(String account, int type) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		// 获取验证码
		String vcode = Tools.getCode();
		String subject =  type>1?"找回密码":"创建用户";
		String msg = String.format("【爱生活】{0}，验证码：{1},五分钟失效，如果不是你请求请忽略", subject,vcode);
		try {
			if (type%2 == 0) {
				// 邮箱发送验证码
				Tools.sendEmail(account, Tools.COMPANY_NAME, msg);
			} else {
				// 发送短信
				Tools.sendSMS(account, msg);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", request.getHeader("uid"));
			map.put("account", account);
			map.put("type", type);
			map.put("vcode", vcode);// 验证码
			map.put("sta", new Date().getTime());// 生成时间
			map.put("exp", new Date().getTime() + 5 * 6000); // 过期时间

			// 获取生成token
			String token = TokenUtil.creatToken(map);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("token", token);

			// 返回信息
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(dataMap);

		} catch (EmailException e) {
			log.debug(e.getMessage());
			info.setMsg("发送邮件验证码错误，请检查你的邮箱是否正确");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
			info.setMsg("发送短信验证码错误，请检查你的手机号是否正确");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
			info.setMsg("发送短信验证码错误，请检查你的手机号是否正确");
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
			info.setMsg("签名错误");
		}

		return info.result();
	}

	/**
	 * 手机获取验证码
	 * 
	 * @param tel 手机号
	 * @return
	 */
	@RequestMapping(value = "/user/getCodeByTel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCodeByTel(String tel) {
		JSONUtil info = new JSONUtil();

		if (Tools.isMobile(tel)) {
			// 查询是否己注册
			UserCriteria example = new UserCriteria();
			UserCriteria.Criteria criteria = example.createCriteria();
			criteria.andTelEqualTo(tel);

			List<User> listUser = userService.selectByExample(example);
			if (listUser.size() > 0) {// 用户已注册
				log.debug("用户已注册");
				info.setCode(Tools.CODE_ERROR);
				info.setMsg("该手机号己注册");
			} else {// 用户未注册
				// 获取验证码
				String vcode = Tools.getCode();
				try {
					Tools.sendEmail(tel, Tools.COMPANY_NAME, "【爱生活】注册验证码：" + vcode + ",五分钟失效，如果不是你注册的请忽略");

					// 获取生成token
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("uid", request.getHeader("uid")); // 建立载荷，这些数据根据业务，自己定义。
					map.put("tel", tel);// 验证码
					map.put("vcode", vcode);// 验证码
					map.put("sta", new Date().getTime());// 生成时间
					map.put("exp", new Date().getTime() + 2 * 1000); // 过期时间
					String token = TokenUtil.creatToken(map);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);

					// 返回信息
					info.setCode(Tools.CODE_SUCCESS);
					info.setMsg(Tools.SUCCESS_MSG);
					info.setData(dataMap);

				} catch (EmailException e) {
					log.debug(e.getMessage());
					info.setCode(Tools.CODE_ERROR);
					info.setMsg("发送短信失败，请检查你的手机号是否正确");
				} catch (Exception e) {
					log.debug(e.getMessage());
					info.setCode(Tools.CODE_ERROR);
					info.setMsg(e.getMessage());
				}
			}
		} else {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg("手机号格式不正确");
		}

		return info.result();
	}

	/**
	 * 邮箱注册
	 * 
	 * @param record：email password
	 * @return
	 */
	@RequestMapping(value = "/user/signUpByEmail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signUpByEmail(User record) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		String token = request.getHeader("token").toString();
		// 解析token
		try {
			if (token != null) {

				Map<String, Object> validMap = TokenUtil.valid(token);
				Integer i = (Integer) validMap.get("Result");
				if (i == 0) {
					log.debug("token解析成功");
					JSONObject jsonObject = (JSONObject) validMap.get("data");
					// 进行解析判断
					if (jsonObject.get("email").equals(record.getEmail())) {// 解析匹配正确
						try {
							// 插入数据
							int flag = userService.insertSelective(record);
							if (flag > 0) {// 注册成功
								info.setCode(Tools.CODE_SUCCESS);
								info.setMsg("注册成功");
							} else {// 注册失败
								info.setMsg("注册失败");
							}
						} catch (Exception e) {
							e.printStackTrace();
							info.setMsg(e.getMessage());
						}
					} else {// 解析匹配错误
						info.setMsg("验证码与邮箱不匹配");
					}
				} else if (i == 2) {
					info.setMsg("验证码过期");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			info.setMsg(e.getMessage());
		} catch (JOSEException e) {
			e.printStackTrace();
			info.setMsg(e.getMessage());
		}

		return info.result();
	}

	@RequestMapping(value = "/user/signUpByTel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signUpByTel(User record) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		String token = request.getHeader("token").toString();
		// 解析token
		try {
			if (token != null) {

				Map<String, Object> validMap = TokenUtil.valid(token);
				Integer i = (Integer) validMap.get("Result");
				if (i == 0) {
					log.debug("token解析成功");
					JSONObject jsonObject = (JSONObject) validMap.get("data");
					// 进行解析判断
					if (jsonObject.get("tel").equals(record.getTel())) {// 解析匹配正确
						try {
							// 插入数据
							int flag = userService.insertSelective(record);
							if (flag > 0) {// 注册成功
								info.setCode(Tools.CODE_SUCCESS);
								info.setMsg("注册成功");
							} else {// 注册失败
								info.setMsg("注册失败");
							}
						} catch (Exception e) {
							e.printStackTrace();
							info.setMsg(e.getMessage());
						}
					} else {// 解析匹配错误
						info.setMsg("验证码与手机号不匹配");
					}
				} else if (i == 2) {
					info.setMsg("验证码过期");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			info.setMsg(e.getMessage());
		} catch (JOSEException e) {
			e.printStackTrace();
			info.setMsg(e.getMessage());
		}

		return info.result();
	}
}
