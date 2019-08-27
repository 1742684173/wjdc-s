package com.aloogn.wjdc.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aloogn.wjdc.filter.RequestWrapper;
import com.aloogn.wjdc.filter.BodyReaderHttpServletRequestWrapper;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aloogn.wjdc.common.Tools;
import com.aloogn.wjdc.redis.service.RedisService;

import net.minidev.json.JSONObject;

import static com.aloogn.wjdc.common.Tools.MD5;

public class LoginInterceptor implements HandlerInterceptor{
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	@Resource(name="myRedisTakes")
	private RedisService redisBaiseTakes;

	private Map<String,String> paramsMap;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
//		RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
//		String bodyString = myRequestWrapper.getBody();
//		if(true) return true;

		try {
			//获取头部common参数
			String common = request.getHeader("common");

			String contentType = request.getContentType();
			TreeMap commonTreeMap = Tools.getTreeMapByJsonstr(common);

			//客户端sign
			String clientSign = (String)commonTreeMap.get("sign");
			System.out.println("客户的签名串："+clientSign);
			commonTreeMap.remove("sign");

			//获取body参数
			RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
			String bodyString = myRequestWrapper.getBody();
			System.out.println("bodyString------>"+bodyString);

			//区分form和json
			if (!StringUtils.isNullOrEmpty(contentType) &&
					(contentType.contains("multipart/form-data") ||
							contentType.contains("x-www-form-urlencoded"))){

				for (String str:bodyString.split("&")) {
					String[] strArr = str.split("=");
					if(strArr.length == 2){
						String keyStr = strArr[0];
						String valueStr = strArr[1];
						if(!StringUtils.isNullOrEmpty(keyStr) && !StringUtils.isNullOrEmpty(valueStr)){
							commonTreeMap.put(keyStr,valueStr);
						}
					}
				}

			}else{
				commonTreeMap.putAll(Tools.getTreeMapByJsonstr(bodyString));
			}


			StringBuilder signStr = new StringBuilder();

			Set<Map.Entry<String,String>> entrySet = commonTreeMap.entrySet();
			Iterator<Map.Entry<String,String>> it = entrySet.iterator();
			while (it.hasNext()){
				Map.Entry<String,String> next = it.next();
				String key = next.getKey()+"";
				String value = next.getValue()+"";

				signStr.append(key).append("=").append(value).append("&");

			}
			signStr.append("key=").append("1as2jfa3jkfda.jkakk");

			System.out.println("签名串："+signStr);
			String mySign = MD5(signStr.toString());
			System.out.println("我的签名串："+mySign);

			//检测参数是否被修改
			if(clientSign.equals(mySign)) {
				String servletPath = request.getServletPath();
				//检测是否登录（signIn.do, signUp.do, findPassword.do 不需要登录)
				if(!"/signIn.do".equals(servletPath) && !"/signUp.do".equals(servletPath)
						&& !"/findPassword.do".equals(servletPath)){

					//获取验证码时判断，如果是注册和找回密码就不需要登录
					if("/getCode.do".equals(servletPath)
							&& (bodyString.contains("signUp") || bodyString.contains("findPassword"))){
						return true;
					}

					//sid
					String sid = (String)commonTreeMap.get("sid");
					String clientUid = (String)commonTreeMap.get("uid");//设备唯一
					//String platform = (String)commonTreeMap.get("platform");//平台

					String myUid = (String)redisBaiseTakes.getObj(Tools.REDIS_LOGIN_ID_KEY, sid);

					//根据sid匹配客户端和缓存中的uid
					if(!clientUid.equals(myUid)){
						loginError(response,"帐户在其它设备登录");
						return false;
					}

					request.setAttribute(Tools.REQUEST_USER_ID_KEY, sid);
				}

				return true;
			}else{
				loginError(response,"签名不正确，参数被篡改");
			}
 		}catch (Exception e){
			System.out.println("异常--------"+e.getMessage());

		}



//		//客户端传来的token
//		String token = request.getHeader("token");
//		String servletPath = request.getServletPath();
//		String type = request.getHeader("type");
//
//		if("/user/getCode.do".equals(servletPath) && (
//				"/user/signUp.do".equals(type) || "/user/findPassword.do".equals(type)
//		)) return true;
//
//		//验证token
//		Map<String, Object> validMap = null;
//		try {
//			validMap = TokenUtil.valid(token);
//			Integer i = (Integer) validMap.get("Result");
//			//token正常
//			if (i == 0) {
//				JSONObject jsonObject = (JSONObject) validMap.get("data");
//				log.debug("jsonObject----->"+jsonObject.toJSONString());
//
//				//解析携带的token载体中的id
//				String id = (String) jsonObject.get("id");
//				//获取存入缓存中的token,每次登录就添加，登出就删除，更改密码或其它就替换
//				String rToken = (String)redisBaiseTakes.getObj(Tools.REDIS_TOKEN_KEY, id);
//				if(token.equals(rToken)) {
//					//将用户id放在头部，以便需要的时候不用再解析
//					request.setAttribute(Tools.REQUEST_USER_ID_KEY, Integer.parseInt(id));
//					return true;
//				}else {
//					loginError(response,"帐户己在其它地方登录，你己被迫下线");
//				}
//			} else if (i == 2) {//token已经过期
//				log.debug("--------token已经过期------");
//				loginError(response,"会话已经过期，请重新登录");
//			}else {
//				log.debug("--------token错误------");
//				loginError(response,"会话错误");
//			}
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//			log.debug("--------解析错误------"+e.getMessage());
//		} catch (JOSEException e) {
//			e.printStackTrace();
//			log.debug("--------JOSE错误------"+e.getMessage());
//		}catch (IOException e) {
//			e.printStackTrace();
//			log.debug("--------读取数据错误------"+e.getMessage());
//		}catch (Exception e) {
//			e.printStackTrace();
//			log.debug("--------未知错误------"+e.getCause().getMessage());
//		}


		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//
	private void loginError(HttpServletResponse response,String msg) throws IOException{
		JSONObject res = new JSONObject();
        res.put("code",Tools.SESSION_CODE_ERROR);
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
