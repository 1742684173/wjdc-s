package com.aloogn.wjdc.bill.service;

import java.util.List;
import java.util.Map;

import com.aloogn.wjdc.bill.exception.BillException;
import org.springframework.stereotype.Service;

import com.aloogn.wjdc.bill.bean.Bill;

@Service
public interface BillService {

	void add(Bill record) throws BillException;

	void deleteById(Integer id) throws BillException;

	void updateById(Bill record) throws BillException;

	Bill findDetailById(Integer id);
	
	Map findPageByMap(Map example) throws BillException;

	List total(Map example) throws BillException;


}
