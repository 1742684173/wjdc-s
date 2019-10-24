package com.aloogn.wjdc.bill.mapper;

import com.aloogn.wjdc.bill.bean.BillSort;
import java.util.List;

public interface BillSortMapper {
    List<BillSort> selectAll();

    BillSort selectByPrimaryKey(Integer id);
}