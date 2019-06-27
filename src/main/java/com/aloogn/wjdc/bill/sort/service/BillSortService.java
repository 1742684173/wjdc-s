package com.aloogn.wjdc.bill.sort.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;

@Service
public interface BillSortService {

	int insert(BillSort record);
	
	int insertSelective(BillSort record);

	int deleteById(Integer id);

	int updateByPrimaryKeySelective(BillSort record);

	long countByExample(BillSortCriteria example);
	
	BillSort selectByPrimaryKey(Integer id);

	List selectByMap(Map<String, String> mapParams);

	long countByMap(Map<String, String> mapParams);

	List selectByExample(BillSortCriteria exampleSort);

	
}
