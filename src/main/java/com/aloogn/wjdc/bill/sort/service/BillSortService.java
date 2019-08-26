package com.aloogn.wjdc.bill.sort.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;

@Service
public interface BillSortService {

	int insertSelective(BillSort record) throws Exception;

	int deleteById(Integer id,Integer userId) throws Exception;

	int updateByPrimaryKeySelective(BillSort record) throws Exception;

	int topById(BillSort record) throws Exception;

	List selectDetailByPrimaryKey(Map<String, String> mapParams) throws Exception;

	List selectListByPage(Map<String, String> mapParams) throws Exception;

}
