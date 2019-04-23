package com.aloogn.wjdc.common.json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJson{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private int code;//响应状态码
	private String msg;//响应状态描述
	private Object data;//响应数据
	private Long time = new Date().getTime();//时间戳 
	
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
        log.debug(map.toString());
		return map;
	}
	
}
