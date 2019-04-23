package com.aloogn.wjdc.bill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;

@Service
public interface BillService {
	
	public int insertSelective(Bill record) throws Exception;
	
	public List<Bill> selectBillByExample(BillCriteria  billCritera);
	
}
