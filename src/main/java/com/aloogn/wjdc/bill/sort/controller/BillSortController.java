package com.aloogn.wjdc.bill.sort.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.bean.BillSortCriteria;
import com.aloogn.wjdc.bill.sort.service.BillSortService;
import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.service.BillService;
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
	private BillSortService billSortService;
	
	@Autowired
	private BillService billService;
	
	@RequestMapping(value = "/billSort/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String name = (String) mapParams.get("name");
			String strTop = (String) mapParams.get("top");
			String strParentId = (String) mapParams.get("parentId");
			String descs = (String) mapParams.get("descs");
			
			BillSort record = new BillSort();
			record.setUserId(Integer.parseInt(strUserId));
			record.setName(name);
			record.setTop(Integer.parseInt(strTop));
			record.setDescs(descs);
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
			
			BillSortCriteria example = new BillSortCriteria();
			BillSortCriteria.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(record.getName());
			criteria.andUserIdEqualTo(record.getUserId());
			
			long flag = billSortService.countByExample(example);
			if(flag > 0) {
				throw new Exception("分类名称不能重复");
			}
			
			flag = billSortService.insertSelective(record);
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
		info.setCode(Tools.CODE_ERROR);
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			Integer userId = Integer.parseInt(strUserId);
			Integer id = Integer.parseInt(mapParams.get("id"));
			
			BillCriteria example = new BillCriteria();
			BillCriteria.Criteria criteria = example.createCriteria();
			criteria.andSortIdEqualTo(id);
			criteria.andUserIdEqualTo(userId);
			
			List list = billService.selectByExample(example);
			if(list.size() > 0) {
				throw new Exception("此分类与帐单相连，不能删除，如需删除请先删除已关连的帐单");
			}
						
			int flag = billSortService.deleteById(id);
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
	
	@RequestMapping(value = "/billSort/updateById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			String strTop = (String) mapParams.get("top");
			String name = (String) mapParams.get("name");
			String descs = (String) mapParams.get("descs");
			
			BillSort record = new BillSort();
			record.setId(Integer.parseInt(strId));
			record.setTop(Integer.parseInt(strTop));
			record.setUserId(Integer.parseInt(strUserId));
			record.setName(name);
			record.setDescs(descs);
			
			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}
			
			BillSortCriteria example = new BillSortCriteria();
			BillSortCriteria.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(record.getName());
			criteria.andUserIdEqualTo(record.getUserId());
			
			long flag = billSortService.countByExample(example);
			if(flag > 0) {
				throw new Exception("名称不能重复");
			}
		
			flag = billSortService.updateByPrimaryKeySelective(record);
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
	
	@RequestMapping(value = "/billSort/topById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> topById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			
			BillSort record = new BillSort();
			record.setUserId(Integer.parseInt(strUserId));
			record.setId(Integer.parseInt(strId));
			record.setTop(1);
			
			int flag = billSortService.updateByPrimaryKeySelective(record);
			if(flag == 0) {
				throw new Exception("置顶失败");
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
	
	@RequestMapping(value = "/billSort/cancelTopById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelTopById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			
			BillSort record = new BillSort();
			record.setUserId(Integer.parseInt(strUserId));
			record.setId(Integer.parseInt(strId));
			record.setTop(0);
			
			int flag = billSortService.updateByPrimaryKeySelective(record);
			if(flag == 0) {
				throw new Exception("取消置顶失败");
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
	
	@RequestMapping(value = "/billSort/find",  method = RequestMethod.POST)
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
			long count = billSortService.countByMap(mapParams);
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
			
			List list = billSortService.selectByMap(mapParams);
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
	
	@RequestMapping(value = "/billSort/findDetailById",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDetailById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			String strId = (String) mapParams.get("id");
			
			BillSort billSort = billSortService.selectByPrimaryKey(Integer.parseInt(strId));
			billSort.setCreateTime(null);
			billSort.setUpdateTime(null);
			billSort.setUserId(null);
			
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(1);
			pageInfo.setTotalPage(1);
			
			List list = new ArrayList();
			list.add(billSort);
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
