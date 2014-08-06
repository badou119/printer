package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DownloadHelper.class);

	public static boolean isIE(String userAgent) {
		if (logger.isDebugEnabled()) {
			logger.debug("isIE(String) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = userAgent.toLowerCase().indexOf("msie") > 0 ? true : false;
		if (logger.isDebugEnabled()) {
			logger.debug("isIE(String) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	public void downloadAttachment(String fileName, String filepath,
			String mimeType, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("downloadAttachment(String, String, String, HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}

		BufferedOutputStream bos = null;
		FileInputStream fis = null;

		if (fileName != null && !"".equals(fileName)) {
			try {
				// 解决中文文件名乱码问题
				String ecodefilename = fileName;

				String userAgent = request.getHeader("User-Agent");
				if (isIE(userAgent))
					ecodefilename = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
				else
					ecodefilename = new String(fileName.getBytes("UTF-8"),
							"ISO8859-1");// firefox浏览器

				String disposition = "attachment;filename=" + ecodefilename;// 注意(1)

				response.reset();
				response.setContentType(mimeType + ";charset=UTF-8");// 注意(2)
				response.setHeader("Content-disposition", disposition);

				fis = new FileInputStream(filepath);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buffer = new byte[2048];
				int len = 0;
				while ((len = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
			} catch (IOException e) {
				logger.error("downloadAttachment(String, String, String, HttpServletRequest, HttpServletResponse)", e); //$NON-NLS-1$

				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						logger.warn("downloadAttachment(String, String, String, HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
					}
				}
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						logger.warn("downloadAttachment(String, String, String, HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("downloadAttachment(String, String, String, HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}
}
