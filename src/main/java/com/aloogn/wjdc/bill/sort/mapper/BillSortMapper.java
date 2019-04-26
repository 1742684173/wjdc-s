package com.aloogn.wjdc.bill.sort.mapper;

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.page.bean.PageInfo;

import java.util.List;
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

	List selectByExampleAndPageInfo(@Param("pageInfo")PageInfo pageInfo, @Param("example")BillSortCriteria example);
}