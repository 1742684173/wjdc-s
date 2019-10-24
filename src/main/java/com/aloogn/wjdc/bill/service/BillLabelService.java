package com.aloogn.wjdc.bill.service;

import com.aloogn.wjdc.bill.bean.BillLabel;
import com.aloogn.wjdc.bill.exception.BillLabelException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BillLabelService {

	void add(BillLabel record) throws BillLabelException;

	void deleteByIds(String ids) throws BillLabelException;

	void updateById(BillLabel record) throws BillLabelException;

	void topById(BillLabel record) throws BillLabelException;

	List<BillLabel> findByUserId(Integer userId);

	Map<String,Object> findPageByMap(Map<String, Object> mapParams) throws BillLabelException;

	BillLabel findDetailById(Integer id) throws BillLabelException;

}
