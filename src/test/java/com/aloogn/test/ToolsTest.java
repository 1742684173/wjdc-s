package com.aloogn.test;

import javax.annotation.Resource;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aloogn.wjdc.redis.service.RedisService;

public class ToolsTest {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@Resource(name="myRedisTakes")
//    RedisService redisService = new RedisServiceImpl();
//	
	@Resource(name="myRedisTakes")
	private RedisService redisService;
	
	 @Before
   public void setup(){
       BasicConfigurator.configure();
   }
	
	@Test
	public void testRedis() {
//		log.debug("----------start---------");
//		//redisService.add("aaaa", "11111");
//		redisService.addObj("user","1111","22222");
//        String value = (String) redisService.getObj("user","1111");
//        log.debug("----------"+ value +"---------");
	}
//	
//			
//	@Test
//	public void testSMS() {
//		try {
//			int flag = Tools.sendSMS("13075986917", "xxx xxx，验证码：xxx ,五分钟失效，如果不是你请求请忽略");
//			log.debug("-------------flag:"+flag+"-----------");
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.debug("-------------"+e.getMessage()+"-----------");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//			log.debug("-------------"+e.getMessage()+"-----------");
//		}
//	}
	
//	Enumeration<String> headerNames=request.getHeaderNames();
//
//	for(Enumeration<String> e=headerNames;e.hasMoreElements();){
//
//		String thisName=e.nextElement().toString();
//	
//		String thisValue=request.getHeader(thisName);
//	
//		System.out.println("header的key:"+thisName+"--------------header的value:"+thisValue.toString());
//	
//	}
}
