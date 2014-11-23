package com.jianq.wechat.params;

import java.io.Serializable;

/**
 * 调用短连接转换接口所需参数
 * 
 * @author longchaozhong
 * @date 2014年11月21日
 */
@SuppressWarnings("serial")
public class ShortURLParams implements Serializable {
	private String	appid;
	private String	mch_id;
	private String	nonce_str;
	private String	sign;
	private String	long_url;
	
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getMch_id() {
		return mch_id;
	}
	
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	
	public String getNonce_str() {
		return nonce_str;
	}
	
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getLong_url() {
		return long_url;
	}
	
	public void setLong_url(String long_url) {
		this.long_url = long_url;
	}
}
