package com.aloogn.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aloogn.wjdc.common.json.MyJson;
import com.aloogn.wjdc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.minidev.json.JSONObject;

public class UserTest extends BaseTest {

//	@Autowired
//	private UserService service;
	
	@Test
	public void testSignIn() {
		ResultActions resultActions;
		
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = new JSONObject();
		json.put("account", "admin");
		json.put("password", "e10adc3949ba59abbe56e057f20f883e");
		try {
			resultActions = mockMvc.perform(MockMvcRequestBuilders
					.post("/user/signIn.do")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json.toJSONString()));
			 MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		        String result = mvcResult.getResponse().getContentAsString();
		        System.out.println("==========结果为：==========\n" + result + "\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("==========结果为：==========\n" + e.getMessage() + "\n");
		}
       
        
	}
}
