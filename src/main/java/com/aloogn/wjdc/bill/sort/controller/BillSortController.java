package com.aloogn.wjdc.bill.sort.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aloogn.wjdc.bill.sort.exception.BillSortException;
import com.aloogn.wjdc.page.bean.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aloogn.wjdc.bill.sort.bean.BillSort;
import com.aloogn.wjdc.bill.sort.service.BillSortService;
import com.aloogn.wjdc.common.JSONUtil;
import com.aloogn.wjdc.common.Tools;
import com.mysql.cj.util.StringUtils;

@Controller
@RequestMapping("/billSort")
public class BillSortController {
	
	private static Logger log = LoggerFactory.getLogger(BillSortController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BillSortService billSortService;

	@PostMapping("/add")
	public @ResponseBody JSONUtil add(BillSort record) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserId(Integer.parseInt(strUserId));


			if(StringUtils.isNullOrEmpty(record.getName())) {
				throw new Exception("名称不能为空");
			}

			if(null == record.getParentId()) {
				record.setParentId(0);
			}
			
			long flag = billSortService.insertSelective(record);
			if(flag == 0) {
				throw new Exception("添加失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(record.getId());
		}catch (BillSortException e){
			info.setMsg(e.getMessage());
		}catch (Exception e) {
			info.setMsg(e.getCause().getMessage());
		}
	
		return info;
	}

	@PostMapping("/deleteById")
	public @ResponseBody JSONUtil deleteById(String id) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			Integer userId = Integer.parseInt(strUserId);
			Integer mid = Integer.parseInt(id);

			int flag = billSortService.deleteById(mid,userId);
			if(flag == 0) {
				throw new Exception("删除失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (BillSortException e) {
			info.setMsg(e.getMessage());
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info;
	}

	@PostMapping("/updateById")
	public @ResponseBody JSONUtil updateById(BillSort record) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);

		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserId(Integer.parseInt(strUserId));
			
			long flag = billSortService.updateByPrimaryKeySelective(record);
			if(flag == 0) {
				throw new BillSortException("修改失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		} catch (BillSortException e){
			info.setMsg(e.getMessage());
		} catch(Exception e) {
			info.setMsg(e.getCause().getMessage());
		}
	
		return info;
	}

	@PostMapping("/topById")
	public @ResponseBody JSONUtil topById(String id) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			BillSort billSort = new BillSort();
			billSort.setId(Integer.parseInt(id));
			billSort.setTop(1);

			int flag = billSortService.topById(billSort);
			if(flag == 0) {
				throw new Exception("置顶失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info;
	}

	@PostMapping("/cancelTopById")
	public @ResponseBody JSONUtil cancelTopById(String id) {

		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			BillSort billSort = new BillSort();
			billSort.setId(Integer.parseInt(id));
			billSort.setTop(0);

			int flag = billSortService.topById(billSort);
			if(flag == 0) {
				throw new Exception("置顶失败");
			}
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(null);
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}

		return info;
	}

	@PostMapping("/findByPage")
	public @ResponseBody JSONUtil findByPage(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			mapParams.put("i_userId",userId);

			List list = billSortService.selectListByPage(mapParams);
			int code = Integer.parseInt(String.valueOf(mapParams.get("o_code")));
			info.setCode(code==1?Tools.CODE_SUCCESS:Tools.CODE_ERROR);
			info.setMsg(mapParams.get("o_msg").toString());

			long totalCount = Long.parseLong(String.valueOf(mapParams.get("o_total")));

			info.setData(new PageInfo(totalCount,list));
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info;
	}

	@PostMapping("/findDetailById")
	public @ResponseBody JSONUtil findDetailById(String id) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		
		try {
			Map mapParams = new HashMap();
			mapParams.put("i_id",id);

			List list = billSortService.selectDetailByPrimaryKey(mapParams);

			int code = Integer.parseInt(String.valueOf(mapParams.get("o_code")));
			info.setCode(code==1?Tools.CODE_SUCCESS:Tools.CODE_ERROR);
			info.setMsg(mapParams.get("o_msg").toString());
			info.setData(list.get(0));
		}catch (Exception e) {
			info.setMsg(e.getMessage());
		}
	
		return info;
	}
}
