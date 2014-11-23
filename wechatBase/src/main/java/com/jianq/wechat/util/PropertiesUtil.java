package com.jianq.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author longchaozhong
 * @date 2014年10月11日
 */
public class PropertiesUtil {
	private Properties	properties	= new Properties();
	
	public PropertiesUtil(String fileName) throws IOException {
		InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
		properties.load(is);
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
