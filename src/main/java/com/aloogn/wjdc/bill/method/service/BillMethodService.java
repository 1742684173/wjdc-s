package com.aloogn.wjdc.bill.method.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.bill.method.bean.BillMethodCriteria;
import com.aloogn.wjdc.page.bean.PageInfo;


@Service
public interface BillMethodService {
	int insert(BillMethod record);

	int insertSelective(BillMethod record);
	
	int deleteById(Integer id);
	
	int updateByPrimaryKeySelective(BillMethod record);

	long countByExample(BillMethodCriteria example);
	
	BillMethod selectByPrimaryKey(Integer id);

	List selectByMap(Map<String, String> mapParams);

	long countByMap(Map<String, String> mapParams);
}
