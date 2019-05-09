package com.aloogn.wjdc.bill.method.mapper;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.bill.method.bean.BillMethodCriteria;
import com.aloogn.wjdc.page.bean.PageInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BillMethodMapper {
    long countByExample(BillMethodCriteria example);

    int deleteByExample(BillMethodCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(BillMethod record);

    int insertSelective(BillMethod record);

    List<BillMethod> selectByExample(BillMethodCriteria example);
    
    List<Map<String, Object>> selectByExampleAndPageInfo(@Param("pageInfo") PageInfo pageInfo,@Param("example")BillMethodCriteria example);

    BillMethod selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BillMethod record, @Param("example") BillMethodCriteria example);

    int updateByExample(@Param("record") BillMethod record, @Param("example") BillMethodCriteria example);

    int updateByPrimaryKeySelective(BillMethod record);

    int updateByPrimaryKey(BillMethod record);
    
    List selectByMap(Map example);
    
    long countByMap(Map example);
}