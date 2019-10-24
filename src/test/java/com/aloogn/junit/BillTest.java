package com.aloogn.junit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.exception.BillException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;

public class BillTest extends BaseTest {

	@Autowired
	private BillMapper mapper;
	
	@Autowired
	private BillService billService;
	
	@Test
	public void test() {
//		Bill bill = mapper.selectByPrimaryKey(142);
//		System.out.println("=========>"+bill.toString());
		Map<String, Object> mapParams = new HashMap<String, Object>();
//		mapParams.put("dateformat", "%Y/%m/%d");
//		mapParams.put("filteTime", "lastQuarter");
//		mapParams.put("startTime", "2019-06-2");
//		mapParams.put("filteTime", "9");
		//i_startTime i_endTime i_maxValue i_minValue i_sortId i_type i_condition
		mapParams.put("i_currentPage", 1);
		mapParams.put("i_pageSize", 5);
		mapParams.put("i_sortName", "餐");
		mapParams.put("userId", 1);
//		mapParams.put("i_startTime", "1");
//		mapParams.put("i_endTime", "1");
//		mapParams.put("i_maxValue", "1");
//		mapParams.put("i_minValue", "1");
//		mapParams.put("i_sortId", "1");
//		mapParams.put("i_type", "1");
//		mapParams.put("i_condition", "1");
//
		try {
			Map map = billService.findPageByMap(mapParams);
			System.out.println("=========>"+map.toString());
		}catch (BillException e){

		}


	}
}
