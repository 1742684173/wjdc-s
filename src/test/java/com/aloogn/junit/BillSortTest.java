package com.aloogn.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.mapper.BillSortMapper;

public class BillSortTest extends BaseTest{

	@Autowired
	private BillSortMapper mapper;
	
	@Test
	public void test() {
		BillSortCriteria example = new BillSortCriteria();
		BillSortCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo("test");
		criteria.andUserIdEqualTo(1);
		long flag = mapper.countByExample(example);
		System.out.println("flag----->"+flag);
	}
	
	
	
}
