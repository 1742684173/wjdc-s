package com.aloogn.junit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aloogn.wjdc.redis.service.RedisService;

public class RedisTest extends BaseTest {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisService redisService;
	
	@Test
	public void test() {
//		redisService.addObj("user","1111","22222");
//        String value = (String) redisService.getObj("user","1111");
//        log.debug("----------"+ value +"---------");
		
	}
}
