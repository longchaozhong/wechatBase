package com.jianq.wechat.constants;

import java.io.IOException;

import com.jianq.wechat.util.PropertiesUtil;

/**
 * @author longchaozhong
 * @date 2014年11月18日
 */
public class WeChatProperties {
	public static String	token;
	public static String	appid;
	public static String	appsecret;
	public static String	mchid;
	public static String	api_key;
	public static String	device_info;
	public static String	notify_url;
	public static String	certificate_uri;
	public static String	order_temp_uri;
	public static String	logs_uri;
	
	public static void init(String filePath) throws IOException {
		PropertiesUtil propertiesUtil = new PropertiesUtil(filePath);
		token = propertiesUtil.getProperty("token");
		appid = propertiesUtil.getProperty("appid");
		mchid = propertiesUtil.getProperty("mchid");
		appsecret = propertiesUtil.getProperty("appsecret");
		mchid = propertiesUtil.getProperty("mchid");
		api_key = propertiesUtil.getProperty("api_key");
		device_info = propertiesUtil.getProperty("device_info");
		notify_url = propertiesUtil.getProperty("notify_url");
		certificate_uri = propertiesUtil.getProperty("certificate_uri");
		order_temp_uri = propertiesUtil.getProperty("order_temp_uri");
		logs_uri = propertiesUtil.getProperty("logs_uri");
	}
}
