package com.jianq.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public String Encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;
		try {
			byte[] bt = strSrc.getBytes("UTF-8");
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(e.getMessage(), e);
		}
		return strDes;
	}
	
	public String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
