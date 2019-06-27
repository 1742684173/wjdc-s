package com.aloogn.wjdc.bill.sort.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.mapper.BillSortMapper;
import com.aloogn.wjdc.bill.sort.service.BillSortService;

@Service
public class BillSortServiceImpl implements BillSortService {
	@Autowired
	BillSortMapper mapper;

	@Override
	public int insert(BillSort record){
		return mapper.insert(record);
	}
	
	@Override
	public int insertSelective(BillSort record){
		return mapper.insertSelective(record);
	}

	@Override
	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BillSort record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public long countByExample(BillSortCriteria example) {
		return mapper.countByExample(example);
	}
	
	@Override
	public BillSort selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List selectByMap(Map<String, String> mapParams) {
		return mapper.selectByMap(mapParams);
	}

	@Override
	public long countByMap(Map<String, String> mapParams) {
		return mapper.countByMap(mapParams);
	}

	@Override
	public List selectByExample(BillSortCriteria exampleSort) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(exampleSort);
	}

}
