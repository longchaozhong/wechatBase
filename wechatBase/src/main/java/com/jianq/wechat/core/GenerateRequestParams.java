package com.jianq.wechat.core;

import java.util.Date;

import com.jianq.wechat.constants.WeChatProperties;
import com.jianq.wechat.params.ShareAddressParams;
import com.jianq.wechat.util.CommonUtils;
import com.jianq.wechat.util.GenerateSign;

/**
 * @author longchaozhong
 * @date 2014年11月21日
 */
public class GenerateRequestParams {
	/**
	 * 生成调用收货地址共享所需参数
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param url
	 *            当前网页url
	 * @param accesstoken
	 *            用户授权凭证
	 * @return
	 */
	public static ShareAddressParams createShareAddressParams(String url, String accesstoken) {
		ShareAddressParams params = new ShareAddressParams();
		String appid = WeChatProperties.appid;
		String timestamp = new Date().getTime() / 1000 + "";
		String noncestr = CommonUtils.getRandomString(20);
		params.setAppId(appid);
		params.setScope("jsapi_address");
		params.setSignType("sha1");
		params.setNonceStr(noncestr);
		params.setTimeStamp(timestamp);
		params.setAddrSign(GenerateSign.createShareAddressSign(appid, url, timestamp, noncestr, accesstoken));
		return params;
	}
}
