package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class CommonUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonUtil.class);

	public static String utf2Gb(String str) {
		if (logger.isDebugEnabled()) {
			logger.debug("utf2Gb(String) - start"); //$NON-NLS-1$
		}

		String tmp = "";
		try {
			tmp = new String(str.getBytes("UTF-8"), "gb2312");
		} catch (Exception e) {
			logger.error("utf2Gb(String)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("utf2Gb(String) - end"); //$NON-NLS-1$
		}
		return tmp;
	}

	public static String gb2Utf8(String str) {
		if (logger.isDebugEnabled()) {
			logger.debug("gb2Utf8(String) - start"); //$NON-NLS-1$
		}

		String tmp = "";
		try {
			tmp = new String(str.getBytes("gb2312"), "UTF-8");
		} catch (Exception e) {
			logger.error("gb2Utf8(String)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("gb2Utf8(String) - end"); //$NON-NLS-1$
		}
		return tmp;
	}

	public static int getRandom() {
		if (logger.isDebugEnabled()) {
			logger.debug("getRandom() - start"); //$NON-NLS-1$
		}

		Random r = new Random();
		int tmp = 0;
		while (true) {
			int x = r.nextInt(999999);
			tmp = 0;
			if (x > 100000) {
				// System.out.println(x);
				tmp = x;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRandom() - end"); //$NON-NLS-1$
		}
		return tmp;
	}

	public void getDAYFLAG() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDAYFLAG() - start"); //$NON-NLS-1$
		}

		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(date);
		System.out.println(str);

		if (logger.isDebugEnabled()) {
			logger.debug("getDAYFLAG() - end"); //$NON-NLS-1$
		}
	}

	public static String getUUID() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUUID() - start"); //$NON-NLS-1$
		}

		UUID uuid = UUID.randomUUID();
		String returnString = uuid.toString().replace("-", "");
		if (logger.isDebugEnabled()) {
			logger.debug("getUUID() - end"); //$NON-NLS-1$
		}
		return returnString;

	}

	public boolean isAll(String phone) {
		if (logger.isDebugEnabled()) {
			logger.debug("isAll(String) - start"); //$NON-NLS-1$
		}

		boolean flag = false;
		String[] phones = { "13903110010", "13803110086", "13503336606",
				"15803210006", "13903110082", "13831182223", "13903117022",
				"15903111002", "18831103993", "15931086351", "13603110011",
				"13933103800", "13503110080", "13931168205", "13582331870",
				"13903110040", "13930190999", "13930186861", "13903110633",
				"13503336622", "13903110191", "13903110043" };
		int ii = Arrays.binarySearch(phones, phone);
		System.out.println(ii);
		for (int i = 0; i < phones.length; i++) {
			if (phone.equals(phones[i])) {
				flag = true;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAll(String) - end"); //$NON-NLS-1$
		}
		return flag;

	}

	public static void main(String args[]) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		CommonUtil cu = new CommonUtil();
		// cu.sendSms("测试", "18831103993");
		System.out.println(cu.isAll("1883110993"));

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
