package com.aloogn.wjdc.bill.controller;

import java.text.ParseException;
import java.util.Enumeration;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.aloogn.wjdc.bill.bean.Bill;
import com.aloogn.wjdc.bill.bean.BillCriteria;
import com.aloogn.wjdc.bill.service.BillService;
import com.aloogn.wjdc.common.utils.JSONUtil;
import com.aloogn.wjdc.common.utils.TokenUtil;
import com.aloogn.wjdc.common.utils.Tools;
import com.aloogn.wjdc.user.bean.UserCriteria;
import com.nimbusds.jose.JOSEException;

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
		info.setCode(Tools.CODE_ERROR);
		
		try {
			//用户id
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			Integer userId = Integer.parseInt(strId);
			bill.setUserId(userId);
			
			if(null == bill.getMethodId()) {
				throw new Exception("请选择方式");
			}
			
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
	
	/**
	 * 查询帐单
	 * @param mapParams
	 * @return
	 */
	@RequestMapping(value = "/bill/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBill(@RequestBody Map<String,String> mapParams) {
		JSONUtil info = new JSONUtil();
		info.setCode(Tools.CODE_ERROR);
		
		try {
        	//用户id
			String strId = request.getAttribute(Tools.REQUEST_USER_ID_KEY).toString();
			mapParams.put("userId", strId);
			
			info.setCode(Tools.CODE_SUCCESS);
			info.setMsg(Tools.SUCCESS_MSG);
			info.setData(billService.find(mapParams));
            
		}catch(Exception e) {
			info.setMsg(e.getCause().getMessage());
		}

		return info.result();
	}
	
}
