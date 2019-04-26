package com.aloogn.wjdc.bill.method.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.page.bean.PageInfo;


@Service
public interface BillMethodService {

	int add(BillMethod record) throws Exception;
	
	int deleteById(Integer id);
	
	int updateById(BillMethod record) throws Exception;
	
	PageInfo<?> find(Map<String, String> mapParams);
}
