package com.aloogn.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aloogn.wjdc.bill.label.bean.BillLabelCriteria;
import com.aloogn.wjdc.bill.label.mapper.BillLabelMapper;
import com.aloogn.wjdc.bill.label.service.BillLabelService;

public class BillLabelTest extends BaseTest{

	@Autowired
	private BillLabelMapper mapper;
	
//	@Autowired
//	private BillLabelService recordService;
	
	@Test
	public void test() {
		BillLabelCriteria example = new BillLabelCriteria();
		BillLabelCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo("test");
		criteria.andUserIdEqualTo(1);
		long flag = mapper.countByExample(example);
		System.out.println("flag----->"+flag);
	}
	
	
	
}
