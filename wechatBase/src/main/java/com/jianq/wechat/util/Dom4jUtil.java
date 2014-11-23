package com.jianq.wechat.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jUtil {
	
	/**
	 * 根据POJO生成XML
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String POJO2XML(Object params) {
		String requestBody = null;
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		
		Class ref = params.getClass();
		Field[] fileds = ref.getDeclaredFields();
		for (Field field : fileds) {
			try {
				String filedName = field.getName();
				Method getter = ref.getMethod("get" + CommonUtils.getMethodName(filedName));
				if (getter.invoke(params) != null)
					root.addElement(filedName).setText((String) getter.invoke(params));
				else
					root.addElement(filedName).setText("");
			} catch (Exception e) {
				LoggerUtil.error("POJO生成XML出错.", e);
				continue;
			}
		}
		requestBody = document.asXML();
		return requestBody;
	}
	
	/**
	 * 将xml字符串转换成map
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> readStringXmlOut(String xml) throws DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = DocumentHelper.parseText(xml);
		// 获取根节点
		Element rootElt = doc.getRootElement();
		// 遍历Element跟节点
		Iterator<Element> iter = rootElt.elementIterator();
		while (iter.hasNext()) {
			Element recordEle = iter.next();
			map.put(recordEle.getName(), recordEle.getText());
		}
		return map;
	}
}
