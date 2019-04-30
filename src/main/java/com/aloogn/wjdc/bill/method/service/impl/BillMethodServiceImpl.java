package com.aloogn.wjdc.bill.method.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.bill.method.bean.BillMethodCriteria;
import com.aloogn.wjdc.bill.method.mapper.BillMethodMapper;
import com.aloogn.wjdc.bill.method.service.BillMethodService;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Service
public class BillMethodServiceImpl implements BillMethodService {
	@Autowired
	BillMethodMapper mapper;

	@Override
	public int add(BillMethod record) throws Exception {
		BillMethodCriteria example = new BillMethodCriteria();
		BillMethodCriteria.Criteria criteria = example.createCriteria();
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
	public int updateById(BillMethod record) throws Exception  {
		BillMethodCriteria example = new BillMethodCriteria();
		BillMethodCriteria.Criteria criteria = example.createCriteria();
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
		
		BillMethodCriteria example = new BillMethodCriteria();
		
		
		if(!StringUtils.isNullOrEmpty(sortName)) {
			example.setOrderByClause(sortName);
		}
		
		if(!StringUtils.isNullOrEmpty(condition)) {
			BillMethodCriteria.Criteria criteriaName = example.createCriteria();
			criteriaName.andUseridEqualTo(userId);
			criteriaName.andNameLike(condition);
			
			BillMethodCriteria.Criteria criteriaDesc = example.createCriteria();
			criteriaDesc.andUseridEqualTo(userId);
			criteriaDesc.andDescsLike(condition);
			example.or(criteriaDesc);
		}
		pageInfo.setCurrentPage((currentPage-1)*pageSize);
		pageInfo.setList(mapper.selectByExampleAndPageInfo(pageInfo,example));
		
		long count = mapper.countByExample(example);
		pageInfo.setTotalCount(count);
		pageInfo.setTotalPage(count/pageSize+(count%pageSize==0?0:1));
		return pageInfo;
	}

}
