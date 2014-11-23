package com.jianq.wechat.params;

import java.io.Serializable;

/**
 * 调用关闭订单接口所需参数
 * 
 * @author longchaozhong
 * @date 2014年11月18日
 */
@SuppressWarnings("serial")
public class CloseOrderParams implements Serializable {
	private String	appid;
	private String	mch_id;
	private String	out_trade_no;
	private String	nonce_str;
	private String	sign;
	
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
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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
}
