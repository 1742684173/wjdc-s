package com.aloogn.wjdc.common;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import sun.rmi.runtime.Log;

public class Tools {
	public static final String COMPANY_NAME = "黔龙股份有限公司";
	public static final String COMPANY_EMAIL = "qinlong@aloogn.com";
	public static final String APP_NAME = "【爱生活】";
	
	//访问成功信息
	public static final String SUCCESS_MSG = "ok";  
	//访问失败信息
	public static final String ERROR_MSG = "error";  
	//访问成功
	public static final int CODE_SUCCESS = 200; 
	//访问错误 服务器不理解请求的语法。
	public static final int CODE_ERROR = 400; 
	//请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。。
	public static final int SESSION_CODE_ERROR = 401; 

	//redis用户登录缓存信息
	public static final String REDIS_TOKEN_KEY="token_key";
	public static final String REQUEST_USER_ID_KEY="user_id_key";
	public static final String REDIS_LOGIN_ID_KEY="user_id_key";//存已登录的id
	public static final String REDIS_GET_CODE_KEY="get_code_key";//存验证码

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
	 * 生成随机验证码
	 * @return
	 */
	public static String getCode() {
		return String.valueOf((int)(Math.random()*900000)+100000);
	}
	
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
	
	/**
	 * 邮箱校验
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		try {
			// 正常邮箱
			// /^\w+((-\w)|(\.\w))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
 
			// 含有特殊 字符的 个人邮箱 和 正常邮箱
			// js: 个人邮箱
			// /^[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+@[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+(\.[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+)+$/
 
			// java：个人邮箱
			// [\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+
 
			// 范围 更广的 邮箱验证 “/^[^@]+@.+\\..+$/”
			final String pattern1 = "[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+";
 
			final Pattern pattern = Pattern.compile(pattern1);
			final Matcher mat = pattern.matcher(email);
			return mat.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 手机号判断
	 * @param tel
	 * @return
	 */
	public static boolean isMobile(String tel) {
		final String REGEX_MOBILE = "(134[0-8]\\d{7})" + "|(" + "((13([0-3]|[5-9]))" + "|149"
				+ "|15([0-3]|[5-9])" + "|166" + "|17(3|[5-8])" + "|18[0-9]" + "|19[8-9]" + ")" + "\\d{8}" + ")";
		return Pattern.matches(REGEX_MOBILE, tel);
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNowDate() {
		return new Date();
	}
	
	/**
	 * 字符转时间
	 * @param dateStr
	 * @param formatStrs 格式
	 * @return
	 */
	public static Date getDateByStr(String dateStr,String... formatStrs) {
		SimpleDateFormat sDateFormat=new SimpleDateFormat(formatStrs.length > 0?formatStrs[0]:"yyyy-MM-dd HH:mm:ss");
		try {
			return sDateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	//字符转map
	public static TreeMap<String,Object> getTreeMapByJsonstr(String jsonStr){
		TreeMap<String,Object> treeMap = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objectMapper.readTree(jsonStr);
			String strMap = objectMapper.writeValueAsString(rootNode);
			treeMap = objectMapper.readValue(strMap, TreeMap.class);
		}catch (Exception e){

		}
		return treeMap;
	}

	//字符转List
	public static List getListByJsonstr(String jsonStr){
		List list = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objectMapper.readTree(jsonStr);
			String logJson = objectMapper.writeValueAsString(rootNode);
			JavaType logType = objectMapper.getTypeFactory().constructParametricType(List.class, Log.class);
			list = objectMapper.readValue(logJson, logType);
		}catch (Exception e){

		}
		return list;
	}

}
