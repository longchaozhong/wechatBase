package com.jianq.wechat.params;

import java.io.Serializable;

/**
 * 调用对账单接口所需参数
 * 
 * @author longchaozhong
 * @date 2014年11月21日
 */
@SuppressWarnings("serial")
public class DownloadBillParams implements Serializable {
	private String	appid;
	private String	mch_id;
	private String	device_info;
	private String	nonce_str;
	private String	sign;
	private String	bill_date;
	private String	bill_type = "ALL";
	
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
	
	public String getDevice_info() {
		return device_info;
	}
	
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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
	
	public String getBill_date() {
		return bill_date;
	}
	
	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}
	
	public String getBill_type() {
		return bill_type;
	}
	
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
}
