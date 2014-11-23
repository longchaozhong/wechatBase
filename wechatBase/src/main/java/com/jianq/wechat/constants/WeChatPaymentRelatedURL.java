package com.jianq.wechat.constants;

/**
 * @author longchaozhong
 * @date 2014年10月29日
 */
public enum WeChatPaymentRelatedURL {
	OrderQuery("https://api.mch.weixin.qq.com/pay/orderquery"), 
	UnifiedOrder("https://api.mch.weixin.qq.com/pay/unifiedorder"), 
	CloseOrder("https://api.mch.weixin.qq.com/pay/closeorder"),
	Refund("https://api.mch.weixin.qq.com/secapi/pay/refund"),
	RefundQuery("https://api.mch.weixin.qq.com/pay/refundquery"),
	DownloadBill("https://api.mch.weixin.qq.com/pay/downloadbill"),
	ShortURL("https://api.mch.weixin.qq.com/tools/shorturl");
	
	private String	URL;
	
	private WeChatPaymentRelatedURL(String url) {
		this.URL = url;
	}
	
	@Override
	public String toString() {
		return this.URL;
	}
}
