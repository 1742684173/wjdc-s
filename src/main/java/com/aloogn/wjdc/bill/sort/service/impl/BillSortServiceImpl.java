package com.aloogn.wjdc.bill.sort.service.impl;


import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.bill.sort.exception.BillSortException;
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

	@Autowired
	BillMapper billMapper;

	@Override
	public int insertSelective(BillSort record) throws Exception{
		BillSortCriteria example = new BillSortCriteria();
		BillSortCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(record.getName());
		criteria.andParentIdEqualTo(record.getParentId());
		criteria.andUserIdEqualTo(record.getUserId());

		if(mapper.countByExample(example) > 0){
			throw new BillSortException("同级名称不能重复");
		}

		return mapper.insertSelective(record);
	}

	@Override
	public int deleteById(Integer id,Integer userId) throws Exception {

		//查询是否有相关连的帐单
		BillCriteria example = new BillCriteria();
		BillCriteria.Criteria criteria = example.createCriteria();
		criteria.andSortIdEqualTo(id);
		criteria.andUserIdEqualTo(userId);

		List list = billMapper.selectByExample(example);
		if(list.size() > 0) {
			throw new BillSortException("此分类与帐单相连，不能删除，如需删除请先删除已关连的帐单");
		}

		//查询是否有子分类
		BillSortCriteria billSortCriteria = new BillSortCriteria();
		BillSortCriteria.Criteria criteria1 = billSortCriteria.createCriteria();
		criteria1.andParentIdEqualTo(id);
		criteria1.andUserIdEqualTo(userId);

		if(mapper.countByExample(billSortCriteria) > 0){
			throw new BillSortException("该分类有子分类，不能删除");
		}

		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BillSort record) throws Exception{
		//查询同级是否有同名
		BillSortCriteria example = new BillSortCriteria();
		BillSortCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(record.getName());
		criteria.andParentIdEqualTo(record.getParentId());
		criteria.andUserIdEqualTo(record.getUserId());
		criteria.andIdNotEqualTo(record.getId());

		if(mapper.countByExample(example) > 0) {
			throw new BillSortException("该名称己存在");
		}

		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int topById(BillSort record) throws Exception{
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List selectDetailByPrimaryKey(Map<String, String> mapParams) {
		return mapper.proDetailById(mapParams);
	}

	@Override
	public List selectListByPage(Map<String, String> mapParams) {
		return mapper.proListByPage(mapParams);
	}
}
