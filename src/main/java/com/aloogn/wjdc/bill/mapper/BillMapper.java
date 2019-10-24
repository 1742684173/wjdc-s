package com.aloogn.wjdc.bill.mapper;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import java.util.List;

import com.aloogn.wjdc.bill.bean.BillDetailCriteria;
import org.apache.ibatis.annotations.Param;

public interface BillMapper {
    long countByExample(BillDetailCriteria example);

    List<Bill> selectByExample(BillDetailCriteria example);

    int deleteByExample(BillCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bill record);

    int insertSelective(Bill record);


    Bill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bill record, @Param("example") BillCriteria example);

    int updateByExample(@Param("record") Bill record, @Param("example") BillCriteria example);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List total(@Param("groupName") String groupName, @Param("example") BillCriteria example);
}