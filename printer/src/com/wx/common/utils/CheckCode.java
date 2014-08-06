package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 
 * @author 徐旭昭
 * 生成验证码字符串
 *
 */
public class CheckCode {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckCode.class);

	static Random r = new Random();

	/**
	 * 随机生成size个字符
	 */
	
	public  String getnumber(int size) {
		if (logger.isDebugEnabled()) {
			logger.debug("getnumber(int) - start"); //$NON-NLS-1$
		}

		String str = "ABCDEFGHIJKLMNPQRSTUVWXY123456789";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buf.append(str.charAt(r.nextInt(str.length())));
		}
		String returnString = buf.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getnumber(int) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * 生成一张图片
	 */

	public BufferedImage createImage(String number) {
		if (logger.isDebugEnabled()) {
			logger.debug("createImage(String) - start"); //$NON-NLS-1$
		}

		// 随机字符串

		// 创建一个内存映像对象
		BufferedImage image = new BufferedImage(60, 20,
				BufferedImage.TYPE_INT_RGB);
		// 获取画笔
		Graphics g = image.getGraphics();
		g.setColor(Color.white);

		// 填充
		g.fillRect(0, 0, 60, 20);
		// 画画
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		// 字体设置 Font
		g.setFont(new Font("宋体", Font.BOLD, 15));

		g.drawString(number,5,15);
		for (int i = 0; i < 2; i++) {
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r
							.nextInt(255)));
			g.drawLine(r.nextInt(60), r.nextInt(20), r.nextInt(60), r
					.nextInt(20));
		}
		// 关闭资源
		g.dispose();

		if (logger.isDebugEnabled()) {
			logger.debug("createImage(String) - end"); //$NON-NLS-1$
		}
		return image;
	}

	/**
	 * 将图片转换成流输出
	 * 
	 * @return
	 */
	public  ByteArrayInputStream getImageAsInputStream(BufferedImage image) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImageAsInputStream(BufferedImage) - start"); //$NON-NLS-1$
		}

		
		ByteArrayInputStream returnByteArrayInputStream = convertImageToStream(image);
		if (logger.isDebugEnabled()) {
			logger.debug("getImageAsInputStream(BufferedImage) - end"); //$NON-NLS-1$
		}
		return returnByteArrayInputStream;
	}

	/**
	 * 将 image 转换成 流 的方法
	 * 
	 * @param image
	 * @return
	 */
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertImageToStream(BufferedImage) - start"); //$NON-NLS-1$
		}

		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
		try {
			encoder.encode(image);
			byte[] bts = outputStream.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (ImageFormatException e) {
			logger.error("convertImageToStream(BufferedImage)", e); //$NON-NLS-1$

			e.printStackTrace();
		} catch (IOException e) {
			logger.error("convertImageToStream(BufferedImage)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertImageToStream(BufferedImage) - end"); //$NON-NLS-1$
		}
		return inputStream;
	}

}
