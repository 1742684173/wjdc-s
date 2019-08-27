package com.aloogn.wjdc.common;

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
//	private LinkedHashSet<String> selectColumns;

	public JSONUtil() {
	}

	public JSONUtil(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JSONUtil(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

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
	
}
