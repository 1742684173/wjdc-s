package com.aloogn.junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aloogn.wjdc.bill.method.service.BillMethodService;
import com.aloogn.wjdc.common.json.MyJson;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.aloogn.wjdc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.minidev.json.JSONObject;

public class BillMethodTest extends BaseTest {

	@Autowired
	private BillMethodService service;
	
	@Test
	public void testFindService() {
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("currentPage", "1");
		mapParams.put("pageSize", "5");
		mapParams.put("userId", "1");
		PageInfo<?> p = service.find(mapParams);
		
		for(int i=0;i<p.getList().size();i++) {
			System.out.println("=========>"+p.getList().get(i).toString());
		}
		
	}
	
	@Test
	public void testFind() {
		ResultActions resultActions;
		
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = new JSONObject();
		json.put("currentPage", "1");
		json.put("pageSize", "5");
		try {
			resultActions = mockMvc.perform(MockMvcRequestBuilders
					.post("/billMethod/find.do")
					.header("token","eyJhbGciOiJIUzI1NiJ9.eyJzaWduIjoiZjQ0Mjk3MmVmMDgyYWVhZDI1ZjY4MWQyOWU4NTBhNGIiLCJ1aWQiOm51bGwsInN0YSI6MTU1NTk5NzUxMzI4MiwiaWQiOiIxIiwiZXhwIjoxNTU2MjU2NzEzMjgyfQ.U3IaegG7i28f8oU3QsZCqCvcHsVItE5oE132FNJ1OW0")
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
