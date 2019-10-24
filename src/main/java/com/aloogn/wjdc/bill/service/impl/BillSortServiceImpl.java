package com.aloogn.wjdc.bill.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.BillSort;
import com.aloogn.wjdc.bill.mapper.BillSortMapper;
import com.aloogn.wjdc.bill.service.BillSortService;

@Service
public class BillSortServiceImpl implements BillSortService {
	@Autowired
	BillSortMapper mapper;

	@Override
	public List<BillSort> selectAll(){
		return mapper.selectAll();
	}

}
