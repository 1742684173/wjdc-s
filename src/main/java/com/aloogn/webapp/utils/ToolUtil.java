package com.aloogn.webapp.utils;

import java.io.IOException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class ToolUtil {
	private static final String COMPANY_EMAIL = "qinlong@aloogn.com";
	private static final String APP_NAME = "【爱生活】";

	//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
	private static final String EMAIL_HOST = "smtp.exmail.qq.com";
	//email发送的字节码
	private static final String EMAIL_CHARSET = "utf-8";
	//发送人的密码
	private static final String EMAIL_MY_PASSWORD = "Jcfxkj52..";
	//email 授权码
	private static final String EMAIL_AUTH_CODE = "ZoaYcDuRKTJ2PiMp";

	//短信
	private static final String SMS_UID = "aloogn";
	private static final String SMS_URL_UTF = "http://utf8.api.smschinese.cn";
	private static final String SMS_URL_GBK = "http://gbk.api.smschinese.cn";
	private static final String SMS_KEY = "d41d8cd98f00b204e980";

	/**
	 * 发送邮件
	 * @param email 收件人邮箱
	 * @param subject 主题
	 * @param content 内容
	 * @throws EmailException
	 */
	public static void sendEmail(String email,String subject,String content) throws EmailException {
		HtmlEmail htmlEmail=new HtmlEmail();//创建一个HtmlEmail实例对象
		htmlEmail.setSSLOnConnect(false);
		htmlEmail.setAuthentication(COMPANY_EMAIL,EMAIL_AUTH_CODE);//设置发送人的邮箱和用户名和授权码(授权码是自己设置的)
		htmlEmail.setHostName(EMAIL_HOST);
		htmlEmail.setCharset(EMAIL_CHARSET);//设置发送的字符类型
		htmlEmail.addTo(email);//设置收件人
		htmlEmail.setFrom(COMPANY_EMAIL,APP_NAME);//发送人的邮箱为自己的，用户名可以随便填
		htmlEmail.setSubject(subject);//设置发送主题
		htmlEmail.setMsg(content);//设置发送内容
		htmlEmail.send();//进行发送
	}
	
	/**
	 * 发短信
	 * @param tel 手机号 
	 * @param content 内容
	 * @throws HttpException
	 * @throws IOException
	 */
	public static int sendSMS(String tel,String content) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(SMS_URL_UTF);
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
		NameValuePair[] data ={
				new NameValuePair("Uid", SMS_UID),//
				new NameValuePair("Key", SMS_KEY),
				new NameValuePair("smsMob",tel),
				new NameValuePair("smsText",content)
				};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();

		post.releaseConnection();
		return statusCode;
	}







}
