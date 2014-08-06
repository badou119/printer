package com.wx.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.BASE64Decoder;


public class Base64 {
	/**
	 * 对字符串进行Base64解码
	 * 
	 * @param s
	 *            要解码的字符串
	 * @return 返回解码后的字符串
	 */
	public static byte[] decode(String s) {
		 
	        BASE64Decoder decoder=new BASE64Decoder();  
	        byte[] bytes=null;  
	        try {  
	            bytes=decoder.decodeBuffer(s);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return bytes;  
	}

	/**
	 * 得到经过Base64解码的图像二进制数据
	 */
	public static byte[] getImgData(String fileUrl) {
		/*
		 * 利用canvas元素的toDataURL("image/jpeg")方法生成的图像地址格式为： 
		 * 第一部分：data:image/jpeg;base64 中间一个逗号
		 * 第二部分：一个经过base64编码的字符串，通过Base64解码后即可得到该图像原始二进制数据
		 */
		//String[] data = fileUrl.split(",");// 这里fileUrl就是前台toDataURL（）方法传过来的数据
		return Base64.decode(fileUrl);
	}

	/**
	 * 保存Base64解码后的二进制数据到文件
	 * 
	 * @param base64Str
	 *            经Base64解码后的图片原始二进制数据
	 * @param path
	 *            文件路径
	 */
	public static void saveImage(byte[] imageBytes, String path) {
		File file = new File(path);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(imageBytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeSteam(outputStream);
		}
	}

	/**
	 * 关闭文件输出流
	 * 
	 * @param outputStream
	 */
	public static void closeSteam(FileOutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
		/**
		 * 对字符串进行Base64解码
		 * @param s 要解码的字符串
		 * @return 返回解码后的字符串
		 */
	public static String decodeStr(String s){
			String decoded_str=null;
			BASE64Decoder decoder=new BASE64Decoder();
			try {
				byte[] bytes=decoder.decodeBuffer(s);
				decoded_str=new String(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return decoded_str;
	}
	
	/**
	 * 得到经过Base64解码的图像二进制数据
	 */
	public static String getImgDataStr(String fileUrl){
		/*
		 * 利用canvas元素的toDataURL("image/jpeg")方法生成的图像地址格式为：
		 *	第一部分：data:image/jpeg;base64
		 * 	中间一个逗号
		 * 	第二部分：一个经过base64编码的字符串，通过Base64解码后即可得到该图像原始二进制数据
		 */
		String[] data=fileUrl.split(",");//这里fileUrl就是前台toDataURL（）方法传过来的数据
		return Base64.decodeStr(data[1]);
	}

	/**
	 * 保存Base64解码后的二进制数据到文件
	 * @param base64Str 经Base64解码后的图片原始二进制数据
	 * @param path 文件路径
	 */
	public static void saveImageStr(String base64Str,String path){
		File file=new File(path);
		FileOutputStream outputStream=null;
		try {
			outputStream=new FileOutputStream(file);
			outputStream.write(base64Str.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			closeSteam(outputStream);
		}
	}

	
}
