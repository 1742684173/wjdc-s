package com.aloogn.wjdc.bill.sort.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.mapper.BillSortMapper;
import com.aloogn.wjdc.bill.sort.service.BillSortService;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Service
public class BillSortServiceImpl implements BillSortService {
	@Autowired
	BillSortMapper mapper;


	@Override
	public int add(BillSort record) throws Exception {
		BillSortCriteria example = new BillSortCriteria();
		BillSortCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(record.getName());
		long flag = mapper.countByExample(example);
		if(flag > 0) {
			throw new Exception("名称不能重复");
		}
		return mapper.insert(record);
	}

	@Override
	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateById(BillSort record) throws Exception  {
		BillSortCriteria example = new BillSortCriteria();
		BillSortCriteria.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(record.getName());
		long flag = mapper.countByExample(example);
		if(flag > 0) {
			throw new Exception("名称不能重复");
		}
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public PageInfo<?> find(Map<String, String> mapParams) {
		Integer userId = Integer.parseInt(mapParams.get("userId"));
		Integer currentPage = Integer.parseInt(mapParams.get("currentPage"));
		Integer pageSize = Integer.parseInt(mapParams.get("pageSize"));
		String sortName = (String)mapParams.get("sortName");
		String condition = (String)mapParams.get("condition");
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		
		BillSortCriteria example = new BillSortCriteria();
		if(!StringUtils.isNullOrEmpty(sortName)) {
			example.setOrderByClause(sortName);
		}
		
		if(!StringUtils.isNullOrEmpty(condition)) {
			BillSortCriteria.Criteria criteriaName = example.createCriteria();
			criteriaName.andNameLike(condition);
			criteriaName.andUseridEqualTo(userId);
			
			BillSortCriteria.Criteria criteriaDesc = example.createCriteria();
			criteriaDesc.andDescsLike(condition);
			criteriaDesc.andUseridEqualTo(userId);
			example.or(criteriaDesc);
		}
		
		pageInfo.setList(mapper.selectByExampleAndPageInfo(pageInfo,example));
		
		long count = mapper.countByExample(example);
		pageInfo.setTotalCount(count);
		pageInfo.setTotalPage(count/pageSize+(count%pageSize==0?0:1));
		return pageInfo;
	}

}
