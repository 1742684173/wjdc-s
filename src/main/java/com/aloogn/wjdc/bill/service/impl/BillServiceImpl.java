package com.aloogn.wjdc.bill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;
import com.mysql.cj.util.StringUtils;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	BillMapper billMapper;

	public int insertSelective(Bill record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Bill> selectBillByExample(BillCriteria billCritera) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
