package com.aloogn.wjdc.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtil{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private int code;//响应状态码
	private String msg;//响应状态描述
	private Object data;//响应数据
	private Long time = new Date().getTime();//时间戳 
	private LinkedHashSet<String> selectColumns;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Map<String, Object> result(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", this.getCode());
        map.put("msg", this.getMsg());
		map.put("data", this.getData());
        log.debug("返回结果："+map.toString());
		return map;
	}
	
	public Object selectProperties(String... properties) {
//		if(properties != null && properties.length > 0) {
//			if(this.selectColumns == null) {
//				this.selectColumns = new LinkedHashSet<String>();
//			}
//			
//			for(String property:properties) {
//				if()
//			}
//		}
		return null;
	}
	
}
