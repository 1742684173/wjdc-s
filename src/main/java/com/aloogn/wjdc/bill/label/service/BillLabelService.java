package com.aloogn.wjdc.bill.label.service;

import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.bill.label.bean.BillLabel;
import com.aloogn.wjdc.bill.label.bean.BillLabelCriteria;

public interface BillLabelService {

	int insert(BillLabel record);

	int insertSelective(BillLabel record);

	int deleteById(Integer id);

	long countByExample(BillLabelCriteria example);

	int updateByPrimaryKeySelective(BillLabel record);

	BillLabel selectByPrimaryKey(Integer id);

	List selectByMap(Map<String, String> mapParams);

	long countByMap(Map<String, String> mapParams);

	int deleteByIds(Map<String, String> mapParams);

}
