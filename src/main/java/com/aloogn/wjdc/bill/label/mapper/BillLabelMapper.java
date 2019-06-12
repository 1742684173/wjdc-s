package com.aloogn.wjdc.bill.label.mapper;

import com.aloogn.wjdc.bill.label.bean.BillLabel;
import com.aloogn.wjdc.bill.label.bean.BillLabelCriteria;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BillLabelMapper {
    long countByExample(BillLabelCriteria example);

    int deleteByExample(BillLabelCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(BillLabel record);

    int insertSelective(BillLabel record);

    List<BillLabel> selectByExample(BillLabelCriteria example);

    BillLabel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BillLabel record, @Param("example") BillLabelCriteria example);

    int updateByExample(@Param("record") BillLabel record, @Param("example") BillLabelCriteria example);

    int updateByPrimaryKeySelective(BillLabel record);

    int updateByPrimaryKey(BillLabel record);

	List selectByMap(Map<String, String> mapParams);

	long countByMap(Map<String, String> mapParams);
}