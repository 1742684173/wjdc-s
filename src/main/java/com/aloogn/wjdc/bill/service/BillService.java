package com.aloogn.wjdc.bill.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;

@Service
public interface BillService {
	
	public int insertSelective(Bill record) throws Exception;
	
	public Object find(Map<String, String> mapParams);

	int deleteById(Integer id);

	int updateById(Bill record) throws Exception;

	Object findById(Integer id);
	
}
