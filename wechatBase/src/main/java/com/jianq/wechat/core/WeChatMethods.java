package com.jianq.wechat.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import org.dom4j.DocumentException;

import com.jianq.wechat.constants.WeChatPaymentRelatedURL;
import com.jianq.wechat.constants.WeChatProperties;
import com.jianq.wechat.params.CloseOrderParams;
import com.jianq.wechat.params.DownloadBillParams;
import com.jianq.wechat.params.OrderQueryParams;
import com.jianq.wechat.params.RefundParams;
import com.jianq.wechat.params.RefundQueryParams;
import com.jianq.wechat.params.ShortURLParams;
import com.jianq.wechat.params.UnifiedOrderParams;
import com.jianq.wechat.util.CommonUtils;
import com.jianq.wechat.util.Dom4jUtil;
import com.jianq.wechat.util.GenerateSign;
import com.jianq.wechat.util.HttpsAccessUtil;

/**
 * @author longchaozhong
 * @date 2014年10月14日
 */
public class WeChatMethods {
	/**
	 * 获取用户授权access_token,包含用户openid
	 * 
	 * @author longchaozhong
	 * @date 2014年10月14日
	 * @param code
	 * @return { "access_token":"ACCESS_TOKEN", "expires_in":7200,
	 *         "refresh_token":"REFRESH_TOKEN", "openid":"OPENID",
	 *         "scope":"SCOPE" }
	 */
	public String getOAuth2AccessToken(String code) {
		String result = null;
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeChatProperties.appid + "&secret=" + WeChatProperties.appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		result = HttpsAccessUtil.doGet(url);
		return result;
	}
	
	/**
	 * 刷新access_token
	 * 
	 * @author longchaozhong
	 * @date 2014年10月17日
	 * @param refresh_token
	 * @return { "access_token":"ACCESS_TOKEN", "expires_in":7200,
	 *         "refresh_token":"REFRESH_TOKEN", "openid":"OPENID",
	 *         "scope":"SCOPE" }
	 */
	public String refreshOAuth2AccessToken(String refresh_token) {
		String result = null;
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WeChatProperties.appid + "&grant_type=refresh_token&refresh_token=" + refresh_token;
		result = HttpsAccessUtil.doGet(url);
		return result;
	}
	
	/**
	 * 调用统一支付接口
	 * 
	 * @author longchaozhong
	 * @date 2014年11月18日
	 * @param openid
	 *            用户在商户appid 下的唯一 标识，trade_type 为JSAPI 时，此参数必传
	 * @param total_fee
	 *            订单总金额，单位为分，不 能带小数点
	 * @param body
	 *            商品描述
	 * @param attach
	 *            附加数据，原样返回
	 * @param out_trade_no
	 *            商户系统内部的订单号,32 个字符内、可包含字母,确保 在商户系统唯一
	 * @param spbill_create_ip
	 *            订单生成的机器IP
	 * @param trade_type
	 *            交易类型
	 * @param product_id
	 *            只在trade_type 为NATIVE 时需要填写。此id 为二维码 中包含的商品ID，商户自行 维护。
	 * @param goods_tag
	 *            商品标记，该字段不能随便 填，不使用请填空
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> unifiedOrder(String openid, String total_fee, String body, String attach, String out_trade_no, String spbill_create_ip, String trade_type,
			String product_id, String goods_tag) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
			DocumentException {
		UnifiedOrderParams params = new UnifiedOrderParams();// 调用统一支付接口传递的参数
		
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setDevice_info(WeChatProperties.device_info);
		params.setNotify_url(WeChatProperties.notify_url);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setSpbill_create_ip(spbill_create_ip);
		params.setTrade_type(trade_type);
		params.setOpenid(openid);
		params.setBody(body);
		params.setAttach(attach);
		params.setTotal_fee(total_fee);
		params.setOut_trade_no(out_trade_no);
		params.setProduct_id(product_id);
		params.setGoods_tag(goods_tag);
		params.setSign(GenerateSign.createSign(params));// 设置签名一定要放在最后一行
		return invokeWeChatInterface(WeChatPaymentRelatedURL.UnifiedOrder, params);
	}
	
	/**
	 * 微信WEB环境下调用统一支付接口
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param openid
	 *            用户在商户appid 下的唯一 标识，trade_type 为JSAPI 时，此参数必传
	 * @param total_fee
	 *            订单总金额，单位为分，不 能带小数点
	 * @param body
	 *            商品描述
	 * @param attach
	 *            附加数据，原样返回
	 * @param out_trade_no
	 *            商户系统内部的订单号,32 个字符内、可包含字母,确保 在商户系统唯一
	 * @param spbill_create_ip
	 *            订单生成的机器IP
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> unifiedOrder4JSAPI(String openid, String total_fee, String body, String attach, String out_trade_no, String spbill_create_ip)
			throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, DocumentException {
		return unifiedOrder(openid, total_fee, body, attach, out_trade_no, spbill_create_ip, "JSAPI", null, null);
	}
	
	/**
	 * Native环境下调用统一支付接口
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param total_fee
	 *            订单总金额，单位为分，不 能带小数点
	 * @param body
	 *            商品描述
	 * @param attach
	 *            附加数据，原样返回
	 * @param out_trade_no
	 *            商户系统内部的订单号,32 个字符内、可包含字母,确保 在商户系统唯一
	 * @param spbill_create_ip
	 *            订单生成的机器IP
	 * @param product_id
	 *            只在trade_type 为NATIVE 时需要填写。此id 为二维码 中包含的商品ID，商户自行 维护。
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> unifiedOrder4Native(String total_fee, String body, String attach, String out_trade_no, String spbill_create_ip, String product_id)
			throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, DocumentException {
		return unifiedOrder(null, total_fee, body, attach, out_trade_no, spbill_create_ip, "NATIVE", product_id, null);
	}
	
	/**
	 * 查询微信订单信息
	 * 
	 * @author longchaozhong
	 * @date 2014年11月18日
	 * @param out_trade_no
	 *            商户订单号
	 * @param transaction_id
	 *            微信订单号
	 * @return
	 * @throws IOException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws DocumentException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public Map<String, String> orderQuery(String out_trade_no, String transaction_id) throws IOException, SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException, KeyManagementException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, DocumentException {
		OrderQueryParams params = new OrderQueryParams();
		
		//装配参数
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setOut_trade_no(out_trade_no);
		params.setTransaction_id(transaction_id);
		params.setSign(GenerateSign.createSign(params));
		return invokeWeChatInterface(WeChatPaymentRelatedURL.OrderQuery, params);
	}
	
	/**
	 * 关闭订单
	 * 
	 * @author longchaozhong
	 * @date 2014年11月18日
	 * @param out_trade_no
	 *            商户订单号
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws DocumentException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public Map<String, String> closeOrder(String out_trade_no) throws SecurityException, NoSuchMethodException, KeyManagementException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, DocumentException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		CloseOrderParams params = new CloseOrderParams();
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setOut_trade_no(out_trade_no);
		params.setSign(GenerateSign.createSign(params));
		
		return invokeWeChatInterface(WeChatPaymentRelatedURL.CloseOrder, params);
	}
	
	/**
	 * 退款申请
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param transaction_id
	 *            微信订单号
	 * @param out_trade_no
	 *            商户订单号,transaction_id 、 out_trade_no 二选一，如果同 时存在优先级：
	 *            transaction_id> out_trade_no
	 * @param out_refund_no
	 *            商户退款单号
	 * @param total_fee
	 *            总金额
	 * @param refund_fee
	 *            退款金额
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> refund(String transaction_id, String out_trade_no, String out_refund_no, String total_fee, String refund_fee) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, KeyManagementException,
			UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, DocumentException {
		RefundParams params = new RefundParams();
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setDevice_info(WeChatProperties.device_info);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setTransaction_id(transaction_id);
		params.setOut_trade_no(out_trade_no);
		params.setOut_refund_no(out_refund_no);
		params.setTotal_fee(total_fee);
		params.setRefund_fee(refund_fee);
		params.setOp_user_id(WeChatProperties.mchid);
		params.setSign(GenerateSign.createSign(params));
		return invokeWeChatInterface(WeChatPaymentRelatedURL.Refund, params);
	}
	
	/**
	 * 退款查询
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param transaction_id
	 *            微信订单号
	 * @param out_trade_no
	 *            商户订单号
	 * @param out_refund_no
	 *            商户退款单号
	 * @param refund_id
	 *            微信退款单号.refund_id、out_refund_no、 out_trade_no 、 transaction_id
	 *            四个参数必 填一个，如果同事存在优先级 为： refund_id>out_refund_no>t
	 *            ransaction_id>out_trade_n o
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> refundQuery(String transaction_id, String out_trade_no, String out_refund_no, String refund_id) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, KeyManagementException,
			UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, DocumentException {
		RefundQueryParams params = new RefundQueryParams();
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setDevice_info(WeChatProperties.device_info);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setTransaction_id(transaction_id);
		params.setOut_trade_no(out_trade_no);
		params.setOut_refund_no(out_refund_no);
		params.setRefund_id(refund_id);
		params.setSign(GenerateSign.createSign(params));
		return invokeWeChatInterface(WeChatPaymentRelatedURL.RefundQuery, params);
	}
	
	/**
	 * 对账单
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param bill_date
	 *            下载对账单的日期，格式： 20140603
	 * @param bill_type
	 *            账单类型--默认值 ALL，返回当日所有订单信息; SUCCESS，返回当日成功支付 的订单;
	 *            REFUND，返回当日退款订单;
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> downloadBill(String bill_date, String bill_type) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, DocumentException {
		DownloadBillParams params = new DownloadBillParams();
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setDevice_info(WeChatProperties.device_info);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setBill_date(bill_date);
		params.setBill_type(bill_type);
		params.setSign(GenerateSign.createSign(params));
		return invokeWeChatInterface(WeChatPaymentRelatedURL.DownloadBill, params);
	}
	
	/**
	 * 短链接转换
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param long_url
	 *            需要转换的URL，签名用原串， 传输需URL encode
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 */
	public Map<String, String> shortURL(String long_url) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
			DocumentException {
		ShortURLParams params = new ShortURLParams();
		params.setAppid(WeChatProperties.appid);
		params.setMch_id(WeChatProperties.mchid);
		params.setNonce_str(CommonUtils.getRandomString(16));
		params.setLong_url(long_url);
		params.setSign(GenerateSign.createSign(params));
		return invokeWeChatInterface(WeChatPaymentRelatedURL.ShortURL, params);
	}
	
	/**
	 * 调用微信支付相关接口
	 * 
	 * @author longchaozhong
	 * @date 2014年10月29日
	 * @param url
	 *            接口地址
	 * @param params
	 *            调用接口所需参数,最终转换成XML发送
	 * @return
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private Map<String, String> invokeWeChatInterface(WeChatPaymentRelatedURL url, Object params) throws KeyManagementException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, DocumentException {
		String xmlParam = Dom4jUtil.POJO2XML(params);
		String responseBody = HttpsAccessUtil.doPostWithCustomSSL(url.toString(), xmlParam);
		Map<String, String> responseMap = Dom4jUtil.readStringXmlOut(responseBody);
		return responseMap;
	}
}
