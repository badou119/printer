package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 此类属于一个单例模式的例子 用来创建获取对象实例
 * 
 * @author
 */
public class Env {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Env.class);

	private static Properties prop = new Properties();
	private static Env instance = new Env();

	// 初始化属性prop对象
	private Env() {
		InputStream in = this.getClass().getResourceAsStream(
				"/config/config.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error("Env()", e); //$NON-NLS-1$

			e.printStackTrace();
		}
	}

	/*
	 * 返回env的单例对象
	 */
	public static Env getInstance() {
		return instance;
	}

	/*
	 * 读属性对象prop读对应的健值
	 */
	public String getProperty(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProperty(String) - start"); //$NON-NLS-1$
		}

		String returnString = (String) prop.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getProperty(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}
}
