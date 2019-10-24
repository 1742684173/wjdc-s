package com.aloogn.webapp.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aloogn.webapp.filter.RequestWrapper;
import com.aloogn.webapp.utils.*;
import com.aloogn.wjdc.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.minidev.json.JSONObject;

public class LoginInterceptor implements HandlerInterceptor{
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	private RedisService redisService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
//		RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
//		String bodyString = myRequestWrapper.getBody();
//		if(true) return true;

//		try {
//			//获取头部common参数
//			String common = request.getHeader("common");
//			//将头部common转换成TreeMap
//			TreeMap commonTreeMap = ConvertUtil.getTreeMapByJsonstr(common);
//			//获取提交类型
//			String contentType = request.getContentType();
//
//			//获取客户端签名
//			String clientSign = (String)commonTreeMap.get("sign");
//			System.out.println("客户的签名串："+clientSign);
//			//移去sign
//			commonTreeMap.remove("sign");
//
//
//			//获取body参数
//			RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
//			String bodyString = myRequestWrapper.getBody();
//			//区分form和json,转换成treeMap
//			if (!StringUtils.isNullOrEmpty(contentType) &&
//					(contentType.contains("multipart/form-data") ||
//							contentType.contains("x-www-form-urlencoded"))){
//
//				for (String str:bodyString.split("&")) {
//					String[] strArr = str.split("=");
//					if(strArr.length == 2){
//						String keyStr = strArr[0];
//						String valueStr = strArr[1];
//						if(!StringUtils.isNullOrEmpty(keyStr) && !StringUtils.isNullOrEmpty(valueStr)){
//							commonTreeMap.put(keyStr,valueStr);
//						}
//					}
//				}
//
//			}else{
//				commonTreeMap.putAll(ConvertUtil.getTreeMapByJsonstr(bodyString));
//			}
//
//			StringBuilder signStr = new StringBuilder();
//
//			//遍历所有的参数，组装成key1=value1&key2=value2&
//			Set<Map.Entry<String,String>> entrySet = commonTreeMap.entrySet();
//			Iterator<Map.Entry<String,String>> it = entrySet.iterator();
//			while (it.hasNext()){
//				Map.Entry<String,String> next = it.next();
//				String key = String.valueOf(next.getKey());
//				String value = String.valueOf(next.getValue());
//
//				signStr.append(key).append("=").append(value).append("&");
//
//			}
//			//加上服务端签名
//			signStr.append("key=").append(Constant.CLIENT_SIGN);
//
//			System.out.println("签名串："+signStr);
//			//MD5加密
//			String mySign = SecureUtil.MD5(signStr.toString());
//			System.out.println("我的签名串："+mySign);
//
//			//通过客户端加密和服务端加密来检测参数是否被修改
//			if(!clientSign.equals(mySign)) {
//				loginError(response,"签名不正确，参数被篡改");
//				return false;
//			}
//
//			//获取请求api
//			String servletPath = request.getServletPath();
//			//检测api是否需要登录（/getCode.do,signIn.do, signUp.do, findPassword.do 不需要登录)
//			if(StringUtils.isWhiteList(servletPath)){
//				return true;
//			}
//
//			//sid即登录用的userId
//			String sid = (String)commonTreeMap.get("sid");
//			String clientUid = (String)commonTreeMap.get("uid");//设备唯一
//			//String platform = (String)commonTreeMap.get("platform");//平台
//
//			//获取存在的uid
//			String serverUid = (String)redisService.hashGet(Constant.REDIS_LOGIN_USER_UID_KEY, sid);
//
//			//判断用户是否己登录
//			if(StringUtils.isNullOrEmpty(serverUid)){
//				loginError(response,"用户未登录");
//				return false;
//			}
//
//			//根据sid匹配客户端和缓存中的uid
//			if(!clientUid.equals(serverUid)){
//				loginError(response,"帐户在其它设备登录");
//				return false;
//			}
//
//			//将用户id放在头部
////			request.setAttribute(Constant.REDIS_LOGIN_USER_ID_KEY, sid);
//
//			return true;
// 		}catch (Exception e){
//			System.out.println("异常--------"+e.getMessage());
//
//		}

		//获取请求api

		String servletPath = request.getServletPath();
		String token = request.getHeader("token");

		//白名单
		if(StringUtils.isWhiteList(servletPath)){
			return true;
		}

		try {
			//解析token
			Map<String, Object> validMap = TokenUtil.valid(token);
			Integer i = (Integer) validMap.get("Result");
			if (i == 0) {
				JSONObject jsonObject = (JSONObject) validMap.get("data");
				log.debug("jsonObject----->"+jsonObject.toJSONString());

				//解析携带的token载体中的id
				String userId = (String) jsonObject.get("userId");
				//获取存入缓存中的token,每次登录就添加，登出就删除，更改密码或其它就替换
				String rToken = (String)redisService.hashGet(Constant.REDIS_TOKEN_KEY, userId);
				if(!token.equals(rToken)) {
					loginError(response,"帐户己在其它地方登录，你己被迫下线");
				}

				return true;
			} else if (i == 2) {//token已经过期
				log.debug("--------token已经过期------");
				loginError(response,"会话已经过期，请重新登录");
			}else {
				log.debug("--------token错误------");
				loginError(response,"会话错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("--------未知错误------"+e.getCause().getMessage());
		}

		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	//
	private void loginError(HttpServletResponse response,String msg) throws IOException{
		JSONObject res = new JSONObject();
        res.put("code",Constant.SESSION_CODE_ERROR);
        res.put("msg",msg);
		
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(res.toString());
		    out.flush();
		} catch (IOException e) {
			response.sendError(500);
		}finally {
			if(out != null) {
				out.close();
			}
		}
	    
	}

}
