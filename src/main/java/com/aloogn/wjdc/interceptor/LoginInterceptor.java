package com.aloogn.wjdc.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.TokenUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.redis.service.RedisService;
import com.nimbusds.jose.JOSEException;

import net.minidev.json.JSONObject;

public class LoginInterceptor implements HandlerInterceptor{
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Resource(name="myRedisTakes")
	private RedisService redisBaiseTakes;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		//客户端传来的token
		String token = (String)request.getHeader("token");
		
		//验证token
		Map<String, Object> validMap = TokenUtil.valid(token);
			
		Integer i = (Integer) validMap.get("Result");
		//token正常
        if (i == 0) {
            JSONObject jsonObject = (JSONObject) validMap.get("data");
            log.debug("jsonObject----->"+jsonObject.toJSONString());
            
            //解析携带的token载体中的id
            String id = (String) jsonObject.get("id");
            //获取存入缓存中的token,每次登录就添加，登出就删除，更改密码或其它就替换
            String rToken = (String)redisBaiseTakes.getObj(Tools.REDIS_TOKEN_KEY, id);
            if(token.equals(rToken)) {
            	//将用户id放在头部，以便需要的时候不用再解析
            	request.setAttribute(Tools.REQUEST_USER_ID_KEY, Integer.parseInt(id));
            	return true;
            }else {
            	loginError(response,"帐户己在其它地方登录，你己被迫下线");
            }
        } else if (i == 2) {//token已经过期
        	log.debug("--------token已经过期------");
        	loginError(response,"会话已经过期，请重新登录");
        }else {
        	log.debug("--------token错误------");
        	loginError(response,"会话错误");
        }
       
        
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
        res.put("code",Tools.CODE_ERROR);
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
