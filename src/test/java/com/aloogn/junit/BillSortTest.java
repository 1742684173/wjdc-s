package com.aloogn.junit;

import com.aloogn.wjdc.bill.sort.service.BillSortService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.mapper.BillSortMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillSortTest extends BaseTest{

	@Autowired
	private BillSortMapper mapper;

	@Autowired
	private BillSortService billSortService;
	
	@Test
	public void test() {
		Map mapParams = new HashMap();
		mapParams.put("i_parentId", "0");
		mapParams.put("i_currentPage", "1");
		mapParams.put("i_pageSize", "23");
		mapParams.put("i_userId", "1");
//		mapParams.put("i_condition", "");
//		mapParams.put("i_sortName", "top desc");
//		mapParams.put("i_id", "50");

		try{
			List<?> list = billSortService.selectListByPage(mapParams);
			System.out.println(mapParams.get("o_msg"));
			System.out.println(mapParams.get("o_code"));
//			String o_code = ;
			Integer.parseInt(mapParams.get("o_code")+"");
//			System.out.println(mapParams.get("o_total"));
//			for(int i=0;i<list.size();i++){
//				System.out.println("---》"+list.get(i).toString());
//			}
		}catch (Exception e) {
			System.out.println(e.getCause().getMessage());
		}

	}
	
	
	
}
