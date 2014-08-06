package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileUtils.class);

	public static void main(String[] args) {
		URL pp = FileUtils.class.getClassLoader().getResource("")  ;
		System.out.println(pp.getPath());
		
		String pathString="E:/tomcat/apache-tomcat-6.0.37/webapps/printer/file/userImg/1/20140801/5c918329a37c42228eb0d2c8e66cf921.png";
		try {
			isExist(pathString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 判断文件夹是否存在,如果不存在则创建文件夹
	 * @param path
	 *            文件路径
	 * @throws IOException 
	 */
	public static void isExist(String path) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - start"); 
		}

		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		
			if (!file.exists()){
				if(path.contains(".")){
					try {
						String p2 = path;
						creatParentDir(p2);
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					file.mkdirs();
				}
			}
		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - end");
		}
	}
	/**
	 * 创建父级目录
	 * @param path
	 */
	public static void creatParentDir(String path){
		// 创建目标目录
		int index = path.lastIndexOf("/");
		if(index>0){
			String dirPath = path.substring(0, path.lastIndexOf("/"));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
	public static String getFilePrefix(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFilePrefix(String) - start"); //$NON-NLS-1$
		}

		int splitIndex = fileName.lastIndexOf(".");
		String returnString = fileName.substring(0, splitIndex);
		if (logger.isDebugEnabled()) {
			logger.debug("getFilePrefix(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public static String getFileSufix(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFileSufix(String) - start"); //$NON-NLS-1$
		}

		int splitIndex = fileName.lastIndexOf(".");
		String returnString = fileName.substring(splitIndex + 1);
		if (logger.isDebugEnabled()) {
			logger.debug("getFileSufix(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public static void copyFile(String inputFile, String outputFile)
			throws FileNotFoundException {
		if (logger.isDebugEnabled()) {
			logger.debug("copyFile(String, String) - start"); //$NON-NLS-1$
		}

		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;
		try {
			while ((temp = fis.read()) != -1) {
				fos.write(temp);
			}
		} catch (IOException e) {
			logger.error("copyFile(String, String)", e); //$NON-NLS-1$

			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				logger.error("copyFile(String, String)", e); //$NON-NLS-1$

				e.printStackTrace();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("copyFile(String, String) - end"); //$NON-NLS-1$
		}
	}
}
