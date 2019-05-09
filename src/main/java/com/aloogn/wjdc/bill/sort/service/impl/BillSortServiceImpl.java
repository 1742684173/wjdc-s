package com.aloogn.wjdc.bill.sort.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.mapper.BillSortMapper;
import com.aloogn.wjdc.bill.sort.service.BillSortService;
import com.aloogn.wjdc.common.utils.Tools;
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
		criteria.andUseridEqualTo(record.getUserid());
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
