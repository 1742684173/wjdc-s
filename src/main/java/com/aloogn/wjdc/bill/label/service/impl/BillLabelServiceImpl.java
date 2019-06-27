package com.aloogn.wjdc.bill.label.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.label.bean.BillLabel;
import com.aloogn.wjdc.bill.label.bean.BillLabelCriteria;
import com.aloogn.wjdc.bill.label.mapper.BillLabelMapper;
import com.aloogn.wjdc.bill.label.service.BillLabelService;

@Service
public class BillLabelServiceImpl implements BillLabelService {
	@Autowired
	BillLabelMapper mapper;

	@Override
	public int insert(BillLabel record){
		return mapper.insert(record);
	}
	
	@Override
	public int insertSelective(BillLabel record){
		return mapper.insertSelective(record);
	}

	@Override
	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BillLabel record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public long countByExample(BillLabelCriteria example) {
		return mapper.countByExample(example);
	}
	
	@Override
	public BillLabel selectByPrimaryKey(Integer id) {
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
	public int deleteByIds(List list) {
		// TODO Auto-generated method stub
		return mapper.deleteByIds(list);
	}

}
