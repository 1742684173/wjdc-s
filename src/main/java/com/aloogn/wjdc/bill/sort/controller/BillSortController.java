package com.aloogn.wjdc.bill.sort.controller;

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

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.service.BillSortService;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Controller
public class BillSortController {

	private static Logger log = LoggerFactory.getLogger(BillSortController.class);
	@Autowired
	private HttpServletRequest request;

	@Autowired
	BillSortService billSortService;
	

	@RequestMapping(value = "/billSort/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(BillSort record) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserid(Integer.parseInt(strId));
			
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
			
			int flag = billSortService.add(record);
			if(flag == 0) {
				throw new Exception("添加失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billSort/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			
			Integer id = Integer.parseInt(mapParams.get("id"));
			
			BillSort record = new BillSort();
			record.setId(id);
			
			int flag = billSortService.deleteById(id);
			if(flag == 0) {
				throw new Exception("删除失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {

			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billSort/updateById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateById(BillSort record) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserid(Integer.parseInt(strId));
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
		
			int flag = billSortService.updateById(record);
			if(flag == 0) {
				throw new Exception("修改失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billSort/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			mapParams.put("userId", request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString());
		
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(billSortService.find(mapParams));
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getCause().getMessage());
		}
	
		return info.result();
	}
	
}
