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
		String pageSize = (String) mapParams.get("pageSize");
		//排弃字段
		String sortName = (String)mapParams.get("sortName");
		//查询条件
		String condition = (String)mapParams.get("condition");
		//id
		String id = (String) mapParams.get("billId");
		//userId
		String userId = (String) mapParams.get("userId");
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
		
		BillCriteria example = new BillCriteria();
		if(!StringUtils.isNullOrEmpty(sortName)) {
			example.setOrderByClause(sortName);
		}
		
		BillCriteria.Criteria criteria = example.createCriteria();
		
		if(null != userId) {
			criteria.andUserIdEqualTo(Integer.parseInt(userId));
		}
		
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
		
		PageInfo pageInfo = new PageInfo();
		if(!StringUtils.isNullOrEmpty(currentPage) && !StringUtils.isNullOrEmpty(pageSize) ) {
			pageInfo.setCurrentPage(Integer.parseInt(currentPage));
			pageInfo.setPageSize(Integer.parseInt(pageSize));
			mapParams.put("currentPage", (Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize)+"");
		}
		
		List list = mapper.selectByExampleAndPageInfo(mapParams);
		pageInfo.setList(list);
		
		long count = mapper.countByExample(example);
		pageInfo.setTotalCount(count);
		if(!StringUtils.isNullOrEmpty(currentPage) && !StringUtils.isNullOrEmpty(pageSize) ) {
			pageInfo.setTotalPage(count/Integer.parseInt(pageSize)+(count%Integer.parseInt(pageSize)==0?0:1));
		}	
		
		return pageInfo;
	}

	

}
