package com.aloogn.wjdc.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Service
public class BillServiceImpl implements BillService {

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
	public Object find(Map<String, String> mapParams) {
		Integer userId = Integer.parseInt(mapParams.get("userId"));
		Integer currentPage = Integer.parseInt(mapParams.get("currentPage"));
		Integer pageSize = Integer.parseInt(mapParams.get("pageSize"));
		String sortName = (String)mapParams.get("sortName");
		String condition = (String)mapParams.get("condition");
		
		//金额区间
		String minSum = (String) mapParams.get("minSum");
		String maxSum = (String) mapParams.get("maxSum");
        //时间区间
		String startTime = (String)mapParams.get("startTime");
		String endTime = (String)mapParams.get("endTime");
		//方式
		String methodId = (String) mapParams.get("methodId");
		//分类
		String sortId = (String) mapParams.get("sortId");
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		
		BillCriteria example = new BillCriteria();
		if(!StringUtils.isNullOrEmpty(sortName)) {
			example.setOrderByClause(sortName);
		}
		
		BillCriteria.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		if(null != startTime) {
			criteria.andDatesLessThanOrEqualTo(Tools.getDateByStr(startTime,"YYYY:MM:DD"));
		}
		
		if(null != endTime) {
			criteria.andDatesLessThanOrEqualTo(Tools.getDateByStr(endTime,"YYYY:MM:DD"));
		}
		
		if(null != minSum) {
			criteria.andSumsGreaterThanOrEqualTo((float) Integer.parseInt(minSum));
		}
		
		if(null != maxSum) {
			criteria.andSumsLessThanOrEqualTo((float) Integer.parseInt(maxSum));
		}
		
		if(null != methodId) {
			criteria.andMethodIdEqualTo(Integer.parseInt(methodId));
		}
		
		if(null != sortId) {
			criteria.andSortIdEqualTo(Integer.parseInt(sortId));
		}
		
		if(!StringUtils.isNullOrEmpty(condition)) {
			criteria.andDescsLike(condition);
		}
		
		List list = mapper.selectByExampleAndPageInfo(mapParams);
		pageInfo.setList(list);
		
		long count = mapper.countByExample(example);
		pageInfo.setTotalCount(count);
		pageInfo.setTotalPage(count/pageSize+(count%pageSize==0?0:1));
		return pageInfo;
	}

	

}
