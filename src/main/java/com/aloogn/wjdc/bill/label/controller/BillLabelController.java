package com.aloogn.wjdc.bill.label.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.label.bean.BillLabel;
import com.aloogn.wjdc.bill.label.bean.BillLabelCriteria;
import com.aloogn.wjdc.bill.label.service.BillLabelService;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Controller
public class BillLabelController {
	
	private static Logger log = LoggerFactory.getLogger(BillLabelController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BillLabelService billLabelService;
	
	@Autowired
	private BillService billService;
	
	@RequestMapping(value = "/billLabel/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String name = (String) mapParams.get("name");
			String descs = (String) mapParams.get("descs");
			
			BillLabel billLabel = new BillLabel();
			billLabel.setUserId(Integer.parseInt(strId));
			billLabel.setName(name);
			billLabel.setDescs(descs);
			
			
			if(StringUtils.isNullOrEmpty(billLabel.getName())) {
				throw new Exception("名称不能为空");
			}
			
			BillLabelCriteria example = new BillLabelCriteria();
			BillLabelCriteria.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(billLabel.getName());
			criteria.andUserIdEqualTo(billLabel.getUserId());
			
			long flag = billLabelService.countByExample(example);
			if(flag > 0) {
				throw new Exception("名称不能重复");
			}
			
			flag = billLabelService.insertSelective(billLabel);
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
	
	@RequestMapping(value = "/billLabel/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteByIds(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			String strIds = (String)mapParams.get("ids");
			List list = new ArrayList();
			for(String item:strIds.split(";")) {
				list.add(Integer.parseInt(item));
			}
		
			int flag = billLabelService.deleteByIds(list);
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
	
	@RequestMapping(value = "/billLabel/updateById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			String name = (String) mapParams.get("name");
			String descs = (String) mapParams.get("descs");
			
			BillLabel billLabel = new BillLabel();
			billLabel.setId(Integer.parseInt(strId));
			billLabel.setUserId(Integer.parseInt(strUserId));
			billLabel.setName(name);
			billLabel.setDescs(descs);
			
			
			if(StringUtils.isNullOrEmpty(billLabel.getName())) {
				throw new Exception("名称不能为空");
			}
			
			BillLabelCriteria example = new BillLabelCriteria();
			BillLabelCriteria.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(billLabel.getName());
			criteria.andUserIdEqualTo(billLabel.getUserId());
			
			long flag = billLabelService.countByExample(example);
			if(flag > 0) {
				throw new Exception("名称不能重复");
			}
		
			flag = billLabelService.updateByPrimaryKeySelective(billLabel);
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
	
	@RequestMapping(value = "/billLabel/find",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			//当前页
			String currentPage = (String) mapParams.get("currentPage");
			//每页页数
			String pageSize = (String) mapParams.get("pageSize");
			
			PageInfo pageInfo = new PageInfo();
			mapParams.put("userId", userId);
			//查询总记录
			long count = billLabelService.countByMap(mapParams);
			pageInfo.setTotalCount(count);
			
			if(!StringUtils.isNullOrEmpty(currentPage) && !StringUtils.isNullOrEmpty(pageSize) ) {
				Integer current = Integer.parseInt(currentPage);
				Integer size = Integer.parseInt(pageSize);
				
				mapParams.put("currentPage", (current-1)*size+"");
				mapParams.put("pageSize", (current*size)+"");
				
				pageInfo.setCurrentPage(current);
				pageInfo.setPageSize(size);
				pageInfo.setTotalPage(count/size+(count%size==0?0:1));
			}else {
				pageInfo.setCurrentPage(1);
				pageInfo.setPageSize(count);
				pageInfo.setTotalPage(1);
			}
			
			List list = billLabelService.selectByMap(mapParams);
			pageInfo.setList(list);
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(pageInfo);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	@RequestMapping(value = "/billLabel/findDetailById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDetailById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			
			BillLabel billLabel = billLabelService.selectByPrimaryKey(Integer.parseInt(strId));
			billLabel.setCreateTime(null);
			billLabel.setUpdateTime(null);
			billLabel.setUserId(null);
			
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(1);
			pageInfo.setTotalPage(1);
			
			List list = new ArrayList();
			list.add(billLabel);
			pageInfo.setList(list);
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(pageInfo);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
}
