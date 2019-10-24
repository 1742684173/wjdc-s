package com.aloogn.wjdc.bill.service.impl;


import com.aloogn.webapp.utils.StringUtils;
import com.aloogn.wjdc.bill.bean.BillLabel;
import com.aloogn.wjdc.bill.bean.BillLabelCriteria;
import com.aloogn.wjdc.bill.exception.BillLabelException;
import com.aloogn.wjdc.bill.mapper.BillLabelMapper;
import com.aloogn.wjdc.bill.service.BillLabelService;
import com.aloogn.wjdc.bill.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillLabelServiceImpl implements BillLabelService {
	@Autowired
	BillLabelMapper mapper;

	@Autowired
	BillMapper billMapper;

	@Override
	public void add(BillLabel record) throws BillLabelException {
		List<BillLabel> list = selectByNameAndUserId(record);
		if(list.size() > 0){
			throw new BillLabelException("该名称己存在");
		}

		mapper.insertSelective(record);
	}

	@Override
	public void deleteByIds(String ids) throws BillLabelException {
		List<Integer> list = new ArrayList<>();
		String[] strs = ids.split(";");
		for (String str:strs) {
			if(!StringUtils.isNullOrEmpty(str)){
				list.add(Integer.parseInt(str));
			}
		}
		mapper.deleteByIds(list);
	}

	@Override
	public void updateById(BillLabel record) throws BillLabelException{
		List<BillLabel> list = selectByNameAndUserId(record);
		if(list.size() > 1){
			throw new BillLabelException("该名称己存在");
		}else if(list.size() > 0){
			if(!record.getId().equals(list.get(0).getId())){
				throw new BillLabelException("该名称己存在");
			}
		}

		mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void topById(BillLabel record){
		mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<BillLabel> findByUserId(Integer userId){
		return selectByUserId(userId);
	}

	@Override
	public Map<String,Object> findPageByMap(Map<String, Object> mapParams) throws BillLabelException{
		BillLabelCriteria example = new BillLabelCriteria();

		Integer currentPage = new Integer(1);
		Integer pageSize = new Integer(5);

		//分页
		if(null != mapParams.get("i_currentPage")){
			currentPage = (Integer)mapParams.get("i_currentPage");
		}
		if(null != mapParams.get("i_pageSize")){
			pageSize = (Integer)mapParams.get("i_pageSize");
		}
		example.setStartCount((currentPage-1)*pageSize);
		example.setEndCount(currentPage*pageSize);

		//排序
		if(null != mapParams.get("i_sortName")){
			example.setOrderByClause((String)mapParams.get("i_sortName"));
		}

		BillLabelCriteria.Criteria criteria = example.createCriteria();

		//用户id
		if(null != mapParams.get("userId")){
			criteria.andUserIdEqualTo((Integer) mapParams.get("userId"));
		}

		//查询条件
		if(null != mapParams.get("i_condition")){
			String i_condition = "%" + (String) mapParams.get("i_condition")+"%";
			criteria.andNameLike(i_condition);
		}

		//总记录
		long totalCount = mapper.countByExample(example);
		//总页数
		long totalPage = totalCount/pageSize+(totalCount%pageSize>0?1:0);
		List list = mapper.selectByExample(example);

		Map<String,Object> result = new HashMap();
		result.put("totalPage",totalPage);
		result.put("totalCount",totalCount);
		result.put("list",list);
		return result;
	}

	@Override
	public BillLabel findDetailById(Integer id) throws BillLabelException{
		return mapper.selectByPrimaryKey(id);
	}

	List<BillLabel> selectByNameAndUserId(BillLabel record){
		BillLabelCriteria example = new BillLabelCriteria();
		example.createCriteria()
				.andNameEqualTo(record.getName())
				.andUserIdEqualTo(record.getUserId());

		return mapper.selectByExample(example);
	}

	List<BillLabel> selectByUserId(Integer userId){
		BillLabelCriteria example = new BillLabelCriteria();
		example.createCriteria().andUserIdEqualTo(userId);

		return mapper.selectByExample(example);
	}
}
