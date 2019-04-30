package com.aloogn.wjdc.bill.method.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloogn.wjdc.bill.method.bean.BillMethod;
import com.aloogn.wjdc.bill.method.service.BillMethodService;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.mysql.cj.util.StringUtils;

@Controller
public class BillMethodController {
	
	private static Logger log = LoggerFactory.getLogger(BillMethodController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BillMethodService billMethodService;
	
	@RequestMapping(value = "/billMethod/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(BillMethod record) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserid(Integer.parseInt(strId));
			
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
			
			int flag = billMethodService.add(record);
			if(flag == 0) {
				throw new Exception("添加失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billMethod/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			
			Integer id = Integer.parseInt(mapParams.get("id"));
			
			int flag = billMethodService.deleteById(id);
			if(flag == 0) {
				throw new Exception("删除失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billMethod/updateById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateById(BillMethod record) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserid(Integer.parseInt(strId));
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
		
			int flag = billMethodService.updateById(record);
			if(flag == 0) {
				throw new Exception("修改失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billMethod/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			mapParams.put("userId", request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString());
		
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(billMethodService.find(mapParams));
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
}
