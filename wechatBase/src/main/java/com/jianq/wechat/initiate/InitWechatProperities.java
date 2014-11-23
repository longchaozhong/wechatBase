package com.jianq.wechat.initiate;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jianq.wechat.constants.WeChatProperties;
import com.jianq.wechat.util.LoggerUtil;

/**
 * @author longchaozhong
 * @date 2014年11月21日
 */
public class InitWechatProperities implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			String	filePath = event.getServletContext().getInitParameter("wechatProperitiesLocation");
			//将wechat.properties文件读入内存；
			WeChatProperties.init(filePath == null ? "wechat.properties" : filePath);
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage(), e);
		}
	}
	
}
