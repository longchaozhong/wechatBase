package com.jianq.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jianq.wechat.constants.WeChatProperties;

/**
 * @author longchaozhong
 * @date 2014年10月13日
 */
@SuppressWarnings("deprecation")
public class HttpsAccessUtil {
	
	/**
	 * get请求
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param url
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public static String doGet(String url) {
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			HttpGet httpGet = new HttpGet(url); // 创建HttpPost
			
			HttpResponse response = httpClient.execute(httpGet); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (KeyManagementException e) {
			LoggerUtil.info(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.info(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.info(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			LoggerUtil.info(e.getMessage(), e);
		} catch (ParseException e) {
			LoggerUtil.info(e.getMessage(), e);
		} catch (IOException e) {
			LoggerUtil.info(e.getMessage(), e);
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}
		return responseContent;
	}
	
	/**
	 * post请求，参数一键值对格式发送
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public static String doPost(String url, Map<String, String> params) {
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(url); // 创建HttpPost
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (KeyManagementException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (ParseException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage(), e);
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}
		return responseContent;
	}
	
	/**
	 * Post请求，发送XMl格式数据
	 * 
	 * @author longchaozhong
	 * @date 2014年11月21日
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public static String doPost(String url, String xmlParam) {
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(url); // 创建HttpPost
			StringEntity xmlEntity = new StringEntity(xmlParam, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(xmlEntity);
			HttpResponse response = httpClient.execute(httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
		} catch (KeyManagementException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (ParseException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage(), e);
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
			}
		}
		return responseContent;
	}
	
	/**
	 * 双向验证访问,发送xml格式数据
	 * 
	 * @author longchaozhong
	 * @date 2014年10月24日
	 * @param url
	 * @param params
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	public static String doPostWithCustomSSL(String url, String xmlParam) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			KeyManagementException, UnrecoverableKeyException {
		String certificate_uri = WeChatProperties.certificate_uri;
		String certificate_pwd = WeChatProperties.mchid;
		StringBuffer result = new StringBuffer();
		// Load certificate
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(certificate_uri));
		try {
			keyStore.load(instream, certificate_pwd.toCharArray());
		} finally {
			instream.close();
		}
		
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certificate_pwd.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		HttpClientBuilder builder = HttpClients.custom().setSSLSocketFactory(sslsf);
		CloseableHttpClient httpclient = builder.build();
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity xmlEntity = new StringEntity(xmlParam, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setEntity(xmlEntity);
			
			LoggerUtil.info("Visit '" + url + "'.Request param = " + xmlParam);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				LoggerUtil.info("status = " + response.getStatusLine());
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result.append(text);
					}
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		LoggerUtil.info("Visit '" + url + "'.Response is \n" + result.toString());
		return result.toString();
	}
	
}
