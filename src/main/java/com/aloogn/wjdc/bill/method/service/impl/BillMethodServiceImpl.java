package com.aloogn.wjdc.bill.method.service.impl;


import java.util.List;
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
		//当前页
		String currentPage = (String) mapParams.get("currentPage");
		//每页页数
		String pageSize = (String) mapParams.get("pageSize");
		
		PageInfo pageInfo = new PageInfo();
		
		List list = mapper.selectByMap(mapParams);
		pageInfo.setList(list);
		
		long count = mapper.countByMap(mapParams);
		pageInfo.setTotalCount(count);
		
		if(!StringUtils.isNullOrEmpty(currentPage) && !StringUtils.isNullOrEmpty(pageSize) ) {
			pageInfo.setCurrentPage(Integer.parseInt(currentPage));
			pageInfo.setPageSize(Integer.parseInt(pageSize));
			pageInfo.setTotalPage(count/Integer.parseInt(pageSize)+(count%Integer.parseInt(pageSize)==0?0:1));
		}else {
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(count);
			pageInfo.setTotalPage(1);
		}
		
		return pageInfo;
	}

}
