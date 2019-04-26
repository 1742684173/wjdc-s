package com.aloogn.wjdc.bill.sort.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.sort.bean.BillSort;

@Service
public interface BillSortService {

	int add(BillSort record) throws Exception;

	int deleteById(Integer id);

	int updateById(BillSort record) throws Exception;

	Object find(Map<String, String> mapParams);
	
}
