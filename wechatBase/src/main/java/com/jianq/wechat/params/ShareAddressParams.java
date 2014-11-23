package com.jianq.wechat.params;

/**
 * JS调用微信收货地址共享接口所需参数
 * 
 * @author longchaozhong
 * @date 2014年11月7日
 */
public class ShareAddressParams {
	private String	appId;
	private String	scope;
	private String	signType;
	private String	addrSign;
	private String	timeStamp;
	private String	nonceStr;
	
	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getSignType() {
		return signType;
	}
	
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public String getAddrSign() {
		return addrSign;
	}
	
	public void setAddrSign(String addrSign) {
		this.addrSign = addrSign;
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
}
