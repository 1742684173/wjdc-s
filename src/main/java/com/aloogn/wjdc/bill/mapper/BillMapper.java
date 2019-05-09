package com.aloogn.wjdc.bill.mapper;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.page.bean.PageInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BillMapper {
    long countByExample(BillCriteria example);

    int deleteByExample(BillCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bill record);

    int insertSelective(Bill record);

    List<Bill> selectByExample(BillCriteria example);

    Bill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bill record, @Param("example") BillCriteria example);

    int updateByExample(@Param("record") Bill record, @Param("example") BillCriteria example);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);
    
    List selectByMap(Map example);
    
    long countByMap(Map example);
}