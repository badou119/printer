package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

public class MD5 {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MD5.class);

	private String inStr;
	private MessageDigest md5;

	public MD5(String inStr) {
		this.inStr = inStr;
		try {
			this.md5 = MessageDigest.getInstance("MD5");

		} catch (Exception e) {
			logger.error("MD5(String)", e); //$NON-NLS-1$

			e.printStackTrace();
		}
	}

	public String compute() {
		if (logger.isDebugEnabled()) {
			logger.debug("compute() - start"); //$NON-NLS-1$
		}

		char[] charArray = this.inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = this.md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String returnString = hexValue.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("compute() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		MD5 md5 = new MD5("qt0424");
		String post = md5.compute();
		System.out.println(post);
	
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
