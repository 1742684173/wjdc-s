package com.aloogn.wjdc.bill.controller;

import com.aloogn.webapp.utils.JSONUtil;
import com.aloogn.wjdc.bill.bean.BillLabel;
import com.aloogn.wjdc.bill.exception.BillLabelException;
import com.aloogn.wjdc.bill.service.BillLabelService;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/billLabel")
public class BillLabelController {
	
	private static Logger log = LoggerFactory.getLogger(BillLabelController.class);


	@Autowired
	private BillLabelService billLabelService;

	@PostMapping("/add")
	public @ResponseBody JSONUtil add(BillLabel record) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			if(StringUtils.isNullOrEmpty(record.getName())) {
				jsonUtil.setMsg("名称不能为空");
				return jsonUtil;
			}

			billLabelService.add(record);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (BillLabelException e){
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

	@PostMapping("/deleteByIds")
	public @ResponseBody JSONUtil deleteByIds(String ids) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			billLabelService.deleteByIds(ids);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (BillLabelException e){
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}

	@PostMapping("/updateById")
	public @ResponseBody JSONUtil updateById(BillLabel record) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			billLabelService.updateById(record);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (BillLabelException e){
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;

	}

	@PostMapping("/topById")
	public @ResponseBody JSONUtil topById(BillLabel record) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			billLabelService.topById(record);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
		}catch (BillLabelException e){
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;

	}

	@PostMapping("/findByUserId")
	public @ResponseBody JSONUtil findByUserId(Integer userId) {
		List list = billLabelService.findByUserId(userId);

		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(200);
		jsonUtil.setMsg("成功");
		jsonUtil.setData(list);

		return jsonUtil;
	}

	@PostMapping("/findPageByMap")
	public @ResponseBody JSONUtil findPageByMap(@RequestBody Map<String,Object> mapParams) {
		JSONUtil jsonUtil = new JSONUtil();
		jsonUtil.setCode(400);

		try {
			mapParams = billLabelService.findPageByMap(mapParams);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
			jsonUtil.setData(mapParams);
		}catch (BillLabelException e){
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
			BillLabel billSort = billLabelService.findDetailById(id);

			jsonUtil.setCode(200);
			jsonUtil.setMsg("成功");
			jsonUtil.setData(billSort);
		}catch (BillLabelException e){
			log.error(e.getMessage());
			jsonUtil.setMsg(e.getMessage());
		}

		return jsonUtil;
	}
}
