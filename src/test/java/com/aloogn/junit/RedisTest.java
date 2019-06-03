package com.aloogn.junit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.common.json.MyJson;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.aloogn.wjdc.redis.service.RedisService;
import com.aloogn.wjdc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.minidev.json.JSONObject;

public class RedisTest extends BaseTest {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisService redisService;
	
	@Test
	public void test() {
		redisService.addObj("user","1111","22222");
        String value = (String) redisService.getObj("user","1111");
        log.debug("----------"+ value +"---------");
		
	}
}
