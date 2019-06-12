package com.aloogn.wjdc.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.controller.BillController;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Service
public class BillServiceImpl implements BillService {
	private static Logger log = LoggerFactory.getLogger(BillServiceImpl.class);
	
	@Autowired
	BillMapper mapper;

	@Override
	public int insertSelective(Bill record) throws Exception {
		return mapper.insertSelective(record);
	}
	
	@Override
	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateById(Bill record) throws Exception  {
		return mapper.updateByPrimaryKey(record);
	}
	
	@Override
	public Bill selectByPrimaryKey(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List selectByMap(Map example) {
		// TODO Auto-generated method stub
		return mapper.selectByMap(example);
	}

	@Override
	public long countByMap(Map example) {
		// TODO Auto-generated method stub
		return mapper.countByMap(example);
	}

	@Override
	public List selectTotalByDates(Map example) {
		// TODO Auto-generated method stub
		return mapper.selectTotalByDates(example);
	}

	@Override
	public List selectTotalByLabel(Map example) {
		// TODO Auto-generated method stub
		return mapper.selectTotalByLabel(example);
	}

	@Override
	public List selectTotalBySort(Map example) {
		// TODO Auto-generated method stub
		return mapper.selectTotalBySort(example);
	}

	@Override
	public List selectTotalByType(Map example) {
		// TODO Auto-generated method stub
		return mapper.selectTotalByType(example);
	}

	@Override
	public int updateByPrimaryKeySelective(Bill record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	

}
