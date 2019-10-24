package com.aloogn.wjdc.bill.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.aloogn.wjdc.bill.bean.BillSort;

@Service
public interface BillSortService {

	List<BillSort> selectAll();

}
