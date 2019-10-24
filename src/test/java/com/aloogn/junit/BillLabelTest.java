package com.aloogn.junit;

import com.aloogn.wjdc.bill.exception.BillLabelException;
import com.aloogn.wjdc.bill.mapper.BillLabelMapper;
import com.aloogn.wjdc.bill.service.BillLabelService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class BillLabelTest extends BaseTest{

	@Autowired
	private BillLabelMapper mapper;

	@Autowired
	private BillLabelService billLabelService;
	
	@Test
	public void test() {
		Map mapParams = new HashMap();
		mapParams.put("i_currentPage", 1);
		mapParams.put("i_pageSize", 23);
		mapParams.put("userId", 1);
		mapParams.put("i_condition", "餐");
//		mapParams.put("i_sortName", "top desc");
//		mapParams.put("i_id", "50");

		try {
			Map map = billLabelService.findPageByMap(mapParams);
			System.out.println("=========>"+map.toString());
		}catch (BillLabelException e){

		}

	}
	
	
	
}
