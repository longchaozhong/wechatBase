package com.jianq.wechat.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jianq.wechat.constants.WeChatProperties;

/**
 * 生成签名 .
 * 
 * @author longchaozhong
 * @date 2014年11月21日
 */
public class GenerateSign {
	/**
	 * 生成微信支付签名
	 * 
	 * a.对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）后，使用 URL 键值对的 格式（即
	 * key1=value1&key2=value2…）拼接成字符串 string1，注意：值为空的参数不参与 签名；
	 * 
	 * b. 在string1最后拼接上key=Key( 商户支付密钥) 得到stringSignTemp 字符串， 并对 stringSignTemp
	 * 进行 md5 运算，再将得到的字符串所有字符转换为大写，得到 sign 值 signValue。
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param params
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String createSign(Object params) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, IOException {
		String signValue = "";
		Class ref = params.getClass();
		Field[] fileds = ref.getDeclaredFields();
		
		// 对字段名进行由小到大字典排序
		List<String> filedNames = new ArrayList<String>();
		for (Field field : fileds) {
			filedNames.add(CommonUtils.lowerFirstChar(field.getName()));
		}
		Collections.sort(filedNames);
		
		// 按顺序拼接字符串，值为空不参与拼接
		StringBuffer string1 = new StringBuffer();
		for (int index = 0; index < filedNames.size(); index++) {
			Method getter = ref.getMethod("get" + CommonUtils.getMethodName(filedNames.get(index)));
			String filedValue = (String) getter.invoke(params);
			if (filedValue != null && filedValue != "") {
				if (index > 0) {
					string1.append("&");
				}
				string1.append(filedNames.get(index));
				string1.append("=");
				string1.append(filedValue);
			}
		}
		
		// 拼接商户支付密钥
		string1.append("&key=" + WeChatProperties.api_key);
		LoggerUtil.info("Creating sign. The template string1 = " + string1.toString());
		
		// MD5加密
		EncryptUtil encry = new EncryptUtil();
		signValue = encry.Encrypt(string1.toString(), "MD5").toUpperCase();
		LoggerUtil.info("Created sign. The sign = " + signValue);
		
		return signValue;
	}
	
	/**
	 * 生成微信支付签名
	 * 
	 * a.对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）后，使用 URL 键值对的 格式（即
	 * key1=value1&key2=value2…）拼接成字符串 string1，注意：值为空的参数不参与 签名；
	 * 
	 * b. 在string1最后拼接上key=Key( 商户支付密钥) 得到stringSignTemp 字符串， 并对 stringSignTemp
	 * 进行 md5 运算，再将得到的字符串所有字符转换为大写，得到 sign 值 signValue。
	 * 
	 * @author longchaozhong
	 * @date 2014年10月27日
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String createSign4Map(Map<String, String> params) throws IOException {
		String signValue = "";
		Set<String> keys = params.keySet();
		
		// 对key进行由小到大字典排序
		List<String> keyNames = new ArrayList<String>();
		for (String key : keys) {
			keyNames.add(key);
		}
		Collections.sort(keyNames);
		
		// 按顺序拼接字符串，值为空不参与拼接
		StringBuffer string1 = new StringBuffer();
		for (int index = 0; index < keyNames.size(); index++) {
			String value = params.get(keyNames.get(index));
			if (value != null && value != "") {
				if (index > 0) {
					string1.append("&");
				}
				string1.append(keyNames.get(index));
				string1.append("=");
				string1.append(value);
			}
		}
		
		// 拼接商户支付密钥
		string1.append("&key=" + WeChatProperties.api_key);
		LoggerUtil.info("Creating sign. The template string1 = " + string1.toString());
		
		// MD5加密
		EncryptUtil encry = new EncryptUtil();
		signValue = encry.Encrypt(string1.toString(), "MD5").toUpperCase();
		LoggerUtil.info("Created sign. The sign = " + signValue);
		
		return signValue;
	}
	
	/**
	 * 创建微信共享收货地址签名
	 * 
	 * @author longchaozhong
	 * @date 2014年11月7日
	 * @param appid
	 * @param url
	 * @param timestamp
	 * @param noncestr
	 * @param accesstoken
	 * @return
	 */
	public static String createShareAddressSign(String appid, String url, String timestamp, String noncestr, String accesstoken) {
		String string1 = ("accesstoken=" + accesstoken);
		string1 += ("&appid=" + appid);
		string1 += ("&noncestr=" + noncestr);
		string1 += ("&timestamp=" + timestamp);
		string1 += ("&url=" + url);
		LoggerUtil.info("createShareAddressSign. string1=" + string1);
		
		EncryptUtil encry = new EncryptUtil();
		return encry.Encrypt(string1, "SHA-1");
	}
}
