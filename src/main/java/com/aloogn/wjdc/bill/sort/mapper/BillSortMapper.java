package com.aloogn.wjdc.bill.sort.mapper;

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BillSortMapper {
    long countByExample(BillSortCriteria example);

    int deleteByExample(BillSortCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(BillSort record);

    int insertSelective(BillSort record);

    List<BillSort> selectByExample(BillSortCriteria example);

    BillSort selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BillSort record, @Param("example") BillSortCriteria example);

    int updateByExample(@Param("record") BillSort record, @Param("example") BillSortCriteria example);

    int updateByPrimaryKeySelective(BillSort record);

    int updateByPrimaryKey(BillSort record);
    
    long countByMap(Map example);
    
    List selectByMap(Map example);
}