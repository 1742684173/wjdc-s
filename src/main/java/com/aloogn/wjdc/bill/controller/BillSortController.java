package com.aloogn.wjdc.bill.controller;

import java.util.List;

import com.aloogn.webapp.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aloogn.wjdc.bill.service.BillSortService;

@Controller
@RequestMapping("/billSort")
public class BillSortController {
	
	private static Logger log = LoggerFactory.getLogger(BillSortController.class);


	@Autowired
	private BillSortService billSortService;

	@PostMapping("/findAll")
	public @ResponseBody JSONUtil findAll() {

		List list = billSortService.selectAll();

		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(200);
		jsonUtil.setMsg("成功");
		jsonUtil.setData(list);

		return jsonUtil;
	}
}
