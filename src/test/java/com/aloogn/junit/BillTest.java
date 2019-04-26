package com.aloogn.junit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
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
import com.aloogn.wjdc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.minidev.json.JSONObject;

public class BillTest extends BaseTest {

	@Autowired
	private BillMapper mapper;
	
	@Autowired
	private BillService billService;
	
	@Test
	public void test() {
//		PageInfo pageInfo = new PageInfo();
//		pageInfo.setCurrentPage(0);
//		pageInfo.setPageSize(5);
//		
//		BillCriteria example = new BillCriteria();
//		example.setDistinct(true);
//		BillCriteria.Criteria criteria = example.createCriteria();
//		criteria.andUseridEqualTo(1);
//		
//		List list = mapper.selectByExampleAndPageInfo(pageInfo, example);
//       
//        for(int i=0;i<list.size();i++) {
//        	Map map = (Map) list.get(i);
//        	System.out.println(map.toString());
//        }
        
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("currentPage", "0");
		mapParams.put("pageSize", "5");
		mapParams.put("userId", "1");
		PageInfo<?> p = (PageInfo<?>) billService.find(mapParams);
		
		for(int i=0;i<p.getList().size();i++) {
			System.out.println("=========>"+p.getList().get(i).toString());
		}
		
	}
}
