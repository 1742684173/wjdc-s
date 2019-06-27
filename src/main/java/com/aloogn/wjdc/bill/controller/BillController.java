package com.aloogn.wjdc.bill.controller;

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

import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.page.bean.PageInfo;
import com.mysql.cj.util.StringUtils;

@Controller
public class BillController {
	private static Logger log = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	BillService billService;
	
	/**
	 * 增加帐单
	 * @param bill
	 * @return
	 */
	@RequestMapping(value = "/bill/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBill(Bill bill) {
		JSONUtil info = new JSONUtil();
		
		try {
			//用户id
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			Integer userId = Integer.parseInt(strId);
			bill.setUserId(userId);
			
			if(null == bill.getSortId()) {
				throw new Exception("请选择分类");
			} 
			
			if(null == bill.getSums()) {
				throw new Exception("请输入消费金额");
			}
			
        	int flag = billService.insertSelective(bill);
			if (flag <= 0) {
				info.setMsg("添加失败");
			} else {
				info.setCode(Tools.CODE_SUCCESS);
				info.setMsg(Tools.SUCCESS_MSG);
			}
        }catch(Exception e) {
			info.setMsg(e.getCause().getMessage());
		}

		return info.result();
	}
	
	@RequestMapping(value = "/bill/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteById(@RequestBody Map<String,String> mapParams) {
		
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		try {
			
			Integer id = Integer.parseInt(mapParams.get("id"));
		
			int flag = billService.deleteById(id);
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
	
	@RequestMapping(value = "/bill/updateById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateById(Bill record) {
		
		JSONUtil info = new JSONUtil();
		try {
			String strUserId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			record.setUserId(Integer.parseInt(strUserId));
			
			if(null == record.getSortId()) {
				throw new Exception("请选择分类");
			} 
			
			if(null == record.getSums()) {
				throw new Exception("请输入消费金额");
			}
			
			int flag = billService.updateByPrimaryKeySelective(record);
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
	
	/**
	 * 查询帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/findById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBillById(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		
		try {
			Integer id = Integer.parseInt(mapParams.get("id"));
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(billService.selectByPrimaryKey(id));
            
		}catch(Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getCause().getMessage());
		}

		return info.result();
	}
	
	/**
	 * 查询帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBill(@RequestBody Map<String,String> mapParams) {
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
			long count = billService.countByMap(mapParams);
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
			
			List list = billService.selectByMap(mapParams);
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
	
	
	/**
	 * 通过时间统计帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/totalBillByDates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> totalBillByDates(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			
			PageInfo pageInfo = new PageInfo();
			mapParams.put("userId", userId);
			
			List list = billService.selectTotalByDates(mapParams);
			pageInfo.setList(list);
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(list.size());
			pageInfo.setTotalPage(1);
			pageInfo.setTotalCount(list.size());
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(pageInfo);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	/**
	 * 通过方式统计帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/totalBillByLabel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> totalBillByMethod(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			
			PageInfo pageInfo = new PageInfo();
			mapParams.put("userId", userId);
			
			List list = billService.selectTotalByLabel(mapParams);
			pageInfo.setList(list);
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(list.size());
			pageInfo.setTotalPage(1);
			pageInfo.setTotalCount(list.size());
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(pageInfo);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	/**
	 * 通过种类统计帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/totalBillBySort", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> totalBillBySort(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			
			PageInfo pageInfo = new PageInfo();
			mapParams.put("userId", userId);
			
			List list = billService.selectTotalBySort(mapParams);
			pageInfo.setList(list);
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(list.size());
			pageInfo.setTotalPage(1);
			pageInfo.setTotalCount(list.size());
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(pageInfo);
		}catch (Exception e) {
			info.setCode(Tools.CODE_ERROR);
			info.setMsg(e.getMessage());
		}
	
		return info.result();
	}
	
	/**
	 * 通过类型统计帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/totalBillByType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> totalBillByType(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		
		try {
			String userId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			
			PageInfo pageInfo = new PageInfo();
			mapParams.put("userId", userId);
			
			List list = billService.selectTotalByType(mapParams);
			pageInfo.setList(list);
			pageInfo.setCurrentPage(1);
			pageInfo.setPageSize(list.size());
			pageInfo.setTotalPage(1);
			pageInfo.setTotalCount(list.size());
			
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
