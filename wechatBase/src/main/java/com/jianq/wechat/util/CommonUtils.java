package com.jianq.wechat.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * @author longchaozhong
 * @date 2014年10月22日
 */
public class CommonUtils {
	/**
	 * 把一个字符串的第一个字母大写、效率是最高的
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	public static String getMethodName(String fildeName) {
		byte[] items = fildeName.getBytes();
		int temp = (char) items[0] - 'a';
		if (temp >= 0)
			items[0] = (byte) (temp + 'A');
		return new String(items);
	}
	
	/**
	 * 把一个字符串的第一个字母小写
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	public static String lowerFirstChar(String fildeName) {
		byte[] items = fildeName.getBytes();
		if ((char) items[0] - 'Z' <= 0)
			items[0] = (byte) ((char) items[0] - 'A' + 'a');
		return new String(items);
	}
	
	/**
	 * 随机生成指定长度的字符串
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param length
	 *            length表示生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 获取客户端IP
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * POJO生成JSON字符串
	 * 
	 * @author longchaozhong
	 * @date 2014年11月14日
	 * @param obj
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String createJson4POJO(Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		StringBuffer json = new StringBuffer();
		Class ref = obj.getClass();
		Field[] fileds = ref.getDeclaredFields();
		
		json.append("{");
		for (int index = 0; index < fileds.length; index++) {
			if (index != 0) {
				json.append(",");
			}
			Method getter = ref.getMethod("get" + getMethodName(fileds[index].getName()));
			json.append("\"");
			json.append(fileds[index].getName());
			json.append("\":\"");
			json.append(getter.invoke(obj) == null ? "" : getter.invoke(obj));
			json.append("\"");
		}
		json.append("}");
		return json.toString();
	}
	
	/**
	 * 初步解析并验证调用微信接口返回的消息
	 * 
	 * @author longchaozhong
	 * @date 2014年11月10日
	 * @param returnMap
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> verifyWeChatMethodsReturn(Map<String, String> returnMap) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		if (returnMap.containsKey("return_code") && returnMap.get("return_code").equals("SUCCESS")) {
			if (returnMap.containsKey("result_code") && returnMap.get("result_code").equals("SUCCESS")) {
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.putAll(returnMap);
				tempMap.remove("sign");
				String sign = GenerateSign.createSign4Map(tempMap);
				
				if (sign.endsWith(returnMap.get("sign"))) {
					result.put("result", "success");
				} else {
					result.put("result", "fail");
					result.put("type", "signError");
					result.put("msg", "非信任签名");
				}
			} else {
				result.put("result", "fail");
				result.put("type", "logicError");
				result.put("err_code", returnMap.get("err_code"));
				result.put("msg", returnMap.get("err_code_des"));
			}
		} else {
			result.put("result", "fail");
			result.put("type", "syntaxError");
			result.put("msg", returnMap.get("return_msg"));
		}
		return result;
	}
}
