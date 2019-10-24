package com.aloogn.test;


import java.util.Date;

public class Test {

	public static void main(String[] args) {
//		String vcode = Tools.getCode();
//		String msg = String.format("【爱生活】，验证码：%s,五分钟后失效，如果不是你请求请忽略", vcode);
//		System.out.println("验证码是："+msg);
		System.out.println(new Date());
		Integer integer = new Integer(1);
		String str = ""+integer;
	}

	@org.junit.Test
	public void test1(){
		Integer integer = new Integer(1);
		String str = ""+1;
		String str1 = ""+integer;
	}
}

