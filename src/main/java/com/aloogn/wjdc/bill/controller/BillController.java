package com.aloogn.wjdc.bill.controller;

import java.util.Map;

import com.aloogn.webapp.base.BaseController;
import com.aloogn.webapp.utils.JSONUtil;
import com.aloogn.wjdc.bill.exception.BillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.service.BillService;

@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BillController.class);

	@Autowired
	BillService billService;

	@PostMapping("/add")
	public @ResponseBody JSONUtil add(Bill bill) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			if(null == bill.getSortId()) {
				jsonUtil.setMsg("分类不能为空");
				return jsonUtil;
			} 
			
			if(null == bill.getSums()) {
				jsonUtil.setMsg("消费金额不能为空");
				return jsonUtil;
			}
			
        	billService.add(bill);

			jsonUtil.setMsg("成功");
			jsonUtil.setCode(200);
        }catch(BillException e) {
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}
		return jsonUtil;
	}
	
	@PostMapping("/deleteById")
	public @ResponseBody JSONUtil deleteById(Integer id) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			billService.deleteById(id);
			jsonUtil.setMsg("成功");
			jsonUtil.setCode(200);
		}catch(BillException e) {
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}
		return jsonUtil;
	
	}
	
	@PostMapping("/updateById")
	public @ResponseBody JSONUtil updateById(Bill bill) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {

			if(null == bill.getSortId()) {
				jsonUtil.setMsg("分类不能为空");
				return jsonUtil;
			}

			if(null == bill.getSums()) {
				jsonUtil.setMsg("消费金额不能为空");
				return jsonUtil;
			}

			billService.updateById(bill);
			jsonUtil.setMsg("成功");
			jsonUtil.setCode(200);
		}catch(BillException e) {
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}
		return jsonUtil;
	}
	

	@PostMapping("/findDetailById")
	public @ResponseBody JSONUtil findDetailById(Integer id) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			Bill bill = billService.findDetailById(id);

			jsonUtil.setMsg("成功");
			jsonUtil.setCode(200);
			jsonUtil.setData(bill);
		}catch(Exception e) {
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}
		return jsonUtil;
	}
	

	@PostMapping("findPageByMap")
	public @ResponseBody JSONUtil findPageByMap(@RequestBody Map<String,Object> map) {

		try {
			map = billService.findPageByMap(map);
			return new JSONUtil(200,"成功",map);
		}catch (BillException e) {
			log.error(e.getMessage());
			return new JSONUtil(400,e.getMessage());
		}
	
	}

	@PostMapping("total")
	public @ResponseBody JSONUtil total(@RequestBody Map<String,Object> map) {

		try {
			 billService.total(map);
			return new JSONUtil(200,"成功",map);
		}catch (BillException e) {
			log.error(e.getMessage());
			return new JSONUtil(400,e.getMessage());
		}

	}
}
