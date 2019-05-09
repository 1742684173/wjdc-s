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
	public Object findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Object find(Map<String, String> mapParams){
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
			pageInfo.setTotalPage(count/Integer.parseInt(pageSize)+(count%Integer.parseInt(pageSize)==0?0:1));
		}
		
		return pageInfo;
	}

	

}
