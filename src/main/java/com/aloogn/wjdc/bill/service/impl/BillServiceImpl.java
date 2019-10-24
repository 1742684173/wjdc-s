package com.aloogn.wjdc.bill.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.bean.BillDetailCriteria;
import com.aloogn.wjdc.bill.exception.BillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import com.aloogn.wjdc.bill.service.BillService;

import static com.aloogn.webapp.utils.ConvertUtil.getDateByStr;

@Service
public class BillServiceImpl implements BillService {
	private static Logger log = LoggerFactory.getLogger(BillServiceImpl.class);
	
	@Autowired
	BillMapper mapper;

	@Override
	public void add(Bill record) throws BillException {
		mapper.insertSelective(record);
	}

	@Override
	public void deleteById(Integer id) throws BillException {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateById(Bill record) throws BillException {
		mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Bill findDetailById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Map findPageByMap(Map mapParams) throws BillException {
		BillDetailCriteria billDetailCriteria = new BillDetailCriteria();

		Integer currentPage = new Integer(1);
		Integer pageSize = new Integer(5);


		//分页
		if(null != mapParams.get("i_currentPage")){
			currentPage = (Integer)mapParams.get("i_currentPage");
		}
		if(null != mapParams.get("i_pageSize")){
			pageSize = (Integer)mapParams.get("i_pageSize");
		}
		billDetailCriteria.setStartCount((currentPage-1)*pageSize);
		billDetailCriteria.setEndCount(currentPage*pageSize);

		//排序
		if(null != mapParams.get("i_sortName")){
			billDetailCriteria.setOrderByClause((String)mapParams.get("i_sortName"));
		}

		//条件查询
		BillCriteria.Criteria criteria = billDetailCriteria.createCriteria();
		//用户id
		if(null != mapParams.get("userId")){
			criteria.andUserIdEqualTo((Integer) mapParams.get("userId"));
		}

		//开始时间
		if(null != mapParams.get("i_startTime")){
			Date startTime = getDateByStr((String)mapParams.get("i_startTime"));
			criteria.andDatesGreaterThanOrEqualTo(startTime);
		}
		//结束时间
		if(null != mapParams.get("i_endTime")){
			Date endTime = getDateByStr((String)mapParams.get("i_endTime"));

			criteria.andDatesLessThan(endTime);
		}
		//收入还是支出
		if(null != mapParams.get("i_type")){
			Integer type = (Integer)mapParams.get("i_type");
			criteria.andTypeEqualTo(type.byteValue());
		}
		//分类id
		if(null != mapParams.get("i_sortId")){
			criteria.andSortIdEqualTo((Integer) mapParams.get("i_sortId"));
		}
		//标签id
		if(null != mapParams.get("i_labelId")){
			criteria.andLabelIdEqualTo((Integer) mapParams.get("i_labelId"));
		}

		//查询条件
		if(null != mapParams.get("i_condition")){
			String i_condition = String.format("%{0}%",(String) mapParams.get("i_condition"));
			criteria.andDescsLike(i_condition);
		}

		//总记录
		long totalCount = mapper.countByExample(billDetailCriteria);
		//总页数
		long totalPage = totalCount/pageSize+(totalCount%pageSize>0?1:0);
		List list = mapper.selectByExample(billDetailCriteria);

		Map<String,Object> result = new HashMap();
		result.put("totalPage",totalPage);
		result.put("totalCount",totalCount);
		result.put("list",list);
		return result;
	}

	@Override
	public List total(Map mapParams) throws BillException {
		BillCriteria billCriteria = new BillCriteria();
		//条件查询
		BillCriteria.Criteria criteria = billCriteria.createCriteria();
		//用户id
		if(null != mapParams.get("userId")){
			criteria.andUserIdEqualTo((Integer) mapParams.get("userId"));
		}

		//开始时间
		if(null != mapParams.get("i_startDate")){
			criteria.andDatesGreaterThanOrEqualTo((Date)mapParams.get("i_startDate"));
		}
		//结束时间
		if(null != mapParams.get("i_endDate")){
			criteria.andDatesLessThan((Date)mapParams.get("i_endDate"));
		}
		//收入还是支出
		if(null != mapParams.get("i_type")){
			criteria.andTypeEqualTo((Byte) mapParams.get("i_type"));
		}
		//分类id
		if(null != mapParams.get("i_sortId")){
			criteria.andSortIdEqualTo((Integer) mapParams.get("i_sortId"));
		}
		//标签id
		if(null != mapParams.get("i_labelId")){
			criteria.andLabelIdEqualTo((Integer) mapParams.get("i_labelId"));
		}

		String groupName = "sortId";
		if(null != mapParams.get("i_groupName")){
			groupName = (String) mapParams.get("i_groupName");
		}

		List list = mapper.total(groupName,billCriteria);
		return list;
	}
}
