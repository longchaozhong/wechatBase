package com.jianq.wechat.params;

/**
 * 微信JS吊起微信支付用到的参数
 * 
 * @author longchaozhong
 * @date 2014年10月24日
 */
public class JSWCPayRequestParams {
	private String	appId;
	private String	timeStamp;
	private String	nonceStr;
	private String	Package;
	private String	signType;
	private String	paySign;
	
	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getNonceStr() {
		return nonceStr;
	}
	
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	public String getPackage() {
		return Package;
	}
	
	public void setPackage(String package1) {
		Package = package1;
	}
	
	public String getSignType() {
		return signType;
	}
	
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public String getPaySign() {
		return paySign;
	}
	
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
}
