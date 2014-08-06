package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Tools.class);
	
	public static void main(String[] args) {
//		String lsh = "0000"+(Integer.parseInt("0022")+1);
//		lsh = lsh.substring(lsh.length()-4, lsh.length());
//		System.out.println(lsh);
	
		System.out.println(getTodayStr());
		
	}
	/**
	 * yyyyMMdd格式的今天
	 * @return yyyyMMdd
	 */
	public static String getTodayStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar rightNow = Calendar.getInstance();
		return df.format(rightNow.getTime());
	}
	
	public static Date prase(String days, String format) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date =	df.parse(days);
		return date;
	}
	
	public static String formatDate(Date days, String format) {

		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnString = df.format(days.getTime());

		return returnString;
	}
	
	/**
	 * 当前日期加减days天后的日期，返回String(yyyy-mm-dd)
	 * 
	 * @param days
	 * @param isAdd
	 * @return String(yyyy-mm-dd)
	 */
	public static String nDaysAftertoday(int days, boolean isAdd) {
		logger.info("nDaysAftertoday(int days=" + days + ", boolean isAdd="
				+ isAdd + ") - start");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();

		logger.info(df.format(rightNow.getTime()));
		if (isAdd) {
			rightNow.add(Calendar.DAY_OF_MONTH, +days); // n为负数 则是减
		} else {
			rightNow.add(Calendar.DAY_OF_MONTH, -days); // n为负数 则是减
		}
		logger.info(df.format(rightNow.getTime()));

		String returnString = df.format(rightNow.getTime());

		logger.info("nDaysAftertoday(days, isAdd) - end - return value="
				+ returnString);
		return returnString;
	}

	/**
	 * 根据URL下载文件
	 * 
	 * @param ourputFile
	 * @param urlStr
	 * @throws Exception
	 */
	public static void getURLResource(String ourputFile, String urlStr)
			throws Exception {
		logger.info("getURLResource(String ourputFile=" + ourputFile
				+ ", String urlStr=" + urlStr + ") - start");

		FileOutputStream out = new FileOutputStream(ourputFile);
		URL resourceUrl = new URL(urlStr);
		URLConnection conn = resourceUrl.openConnection();
		InputStream content = (InputStream) conn.getInputStream();
		int length = conn.getContentLength();
		byte[] buffer = new byte[length];
		int ins;
		while ((ins = content.read(buffer)) != -1) {
			out.write(buffer, 0, ins);
		}
		content.close();
		out.flush();
		out.close();

		logger.info("getURLResource(ourputFile, urlStr) - end");
	}
}
