package com.aloogn.wjdc.bill.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;

@Service
public interface BillService {
	
	public int insertSelective(Bill record) throws Exception;

	int deleteById(Integer id);

	int updateById(Bill record) throws Exception;

	Bill selectByPrimaryKey(Integer id);
	
	List selectByMap(Map example);
    
    long countByMap(Map example);
    
    List selectTotalByDates(Map example);
    
    List selectTotalByMethod(Map example);
    
    List selectTotalBySort(Map example);
    
    List selectTotalByType(Map example);

	int updateByPrimaryKeySelective(Bill record);
}
