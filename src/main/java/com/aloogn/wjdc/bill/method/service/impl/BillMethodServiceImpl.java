package com.aloogn.wjdc.bill.method.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.bill.method.bean.BillMethodCriteria;
import com.aloogn.wjdc.bill.method.mapper.BillMethodMapper;
import com.aloogn.wjdc.bill.method.service.BillMethodService;

@Service
public class BillMethodServiceImpl implements BillMethodService {
	@Autowired
	BillMethodMapper mapper;


	@Override
	public int insert(BillMethod record){
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}
	
	@Override
	public int insertSelective(BillMethod record){
		return mapper.insertSelective(record);
	}

	@Override
	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BillMethod record){
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public long countByExample(BillMethodCriteria example) {
		return mapper.countByExample(example);
	}
	
	@Override
	public BillMethod selectByPrimaryKey(Integer id) {
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

}
