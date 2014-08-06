package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public class HTTPClient {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HTTPClient.class);

	public static void main(String[] args) {
		logger.info("main(String[] args=" + args + ") - start");

		String cString = null;
		try {
			cString = HTTPClient
					.get("http://111.11.28.30/webimadmin/api/user/authentication?"
							+ "username=123&password=e10adc3949ba59abbe56e057f20f883e");

		} catch (ClientProtocolException e) {
			logger.error("main()", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("main()", e);
			e.printStackTrace();
		}
		System.out.println(cString);

		logger.info("main(args) - end");
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String get(String url) throws ClientProtocolException,
			IOException {
		logger.info("get(String url=" + url + ") - start");

		// 创建HttpClient实例
		HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例 "http://127.0.0.1/testhttp.php?username=yonghuming"
		HttpGet http = new HttpGet(url);
		http.setHeader("Accept", "text/html");
		// http.addHeader("Content-Type","text/html; charset=utf-8");
		HttpConnectionParams.setConnectionTimeout(http.getParams(), 30000);
		HttpConnectionParams.setSoTimeout(http.getParams(), 30000);

		HttpResponse response = httpclient.execute(http);
		HttpEntity entity = response.getEntity();

		BufferedReader in = null;
		String content = null;

		if (entity != null) {
			InputStreamReader instreams = new InputStreamReader(entity
					.getContent(), "UTF-8");
			try {
				in = new BufferedReader(instreams);
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				content = sb.toString();
			} finally {
				if (in != null) {
					try {
						in.close();// 最后要关闭BufferedReader
					} catch (Exception e) {
						logger.error("get()", e);

						e.printStackTrace();
					}
				}
			}
		}
		http.abort();

		logger.info("get(url) - end - return value=" + content);
		return content;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String post(String url) throws ClientProtocolException,
			IOException {
		logger.info("post(String url=" + url + ") - start");

		// 创建HttpClient实例
		HttpClient httpclient = new DefaultHttpClient();
		// 创建HttpPost方法实例
		HttpPost http = new HttpPost(url);
		http.setHeader("Accept", "text/html");
		HttpResponse response = httpclient.execute(http);
		HttpEntity entity = response.getEntity();

		BufferedReader in = null;
		String content = null;

		if (entity != null) {
			InputStreamReader instreams = new InputStreamReader(entity
					.getContent(), "UTF-8");
			try {
				in = new BufferedReader(instreams);
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				content = sb.toString();
			} finally {
				if (in != null) {
					try {
						in.close();// 最后要关闭BufferedReader
					} catch (Exception e) {
						logger.error("post()", e);

						e.printStackTrace();
					}
				}
			}
		}
		http.abort();

		logger.info("post(url) - end - return value=" + content);
		return content;
	}

	public static String convertStreamToString(InputStream is) {
		logger.info("convertStreamToString(InputStream is=" + is + ") - start");

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			logger.error("convertStreamToString()", e);

			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("convertStreamToString()", e);

				e.printStackTrace();
			}
		}

		String returnString = sb.toString();
		logger.info("convertStreamToString(is) - end - return value="
				+ returnString);
		return returnString;
	}
}
