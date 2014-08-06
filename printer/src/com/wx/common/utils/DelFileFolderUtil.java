package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.File;
/** 
 * 删除文件和目录 
 */  
public class DelFileFolderUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DelFileFolderUtil.class);

	 /** 
	  * 删除文件，可以是文件或文件夹 
	  *  
	  * @param fileName 
	  *            要删除的文件名 
	  * @return 删除成功返回true，否则返回false 
	  */  
	 public static boolean delete(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete(String) - start"); //$NON-NLS-1$
		}
  
	  File file = new File(fileName);  
	  if (!file.exists()) {  
	   System.out.println("删除文件失败:" + fileName + "不存在！");  

			if (logger.isDebugEnabled()) {
				logger.debug("delete(String) - end"); //$NON-NLS-1$
			}
	   return false;  
	  } else {  
	   if (file.isFile())  
	    return deleteFile(fileName);  
	   else  
	    return deleteDirectory(fileName);  
	  }  
	 }  
	  
	 /** 
	  * 删除单个文件 
	  *  
	  * @param fileName 
	  *            要删除的文件的文件名 
	  * @return 单个文件删除成功返回true，否则返回false 
	  */  
	 public static boolean deleteFile(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteFile(String) - start"); //$NON-NLS-1$
		}
  
	  File file = new File(fileName);  
	  // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除  
	  if (file.exists() && file.isFile()) {  
	   if (file.delete()) {  
	    System.out.println("删除单个文件" + fileName + "成功！");  

				if (logger.isDebugEnabled()) {
					logger.debug("deleteFile(String) - end"); //$NON-NLS-1$
				}
	    return true;  
	   } else {  
	    System.out.println("删除单个文件" + fileName + "失败！");  

				if (logger.isDebugEnabled()) {
					logger.debug("deleteFile(String) - end"); //$NON-NLS-1$
				}
	    return false;  
	   }  
	  } else {  
	   System.out.println("删除单个文件失败：" + fileName + "不存在！");  

			if (logger.isDebugEnabled()) {
				logger.debug("deleteFile(String) - end"); //$NON-NLS-1$
			}
	   return false;  
	  }  
	 }  
	  
	 /** 
	  * 删除目录及目录下的文件 
	  *  
	  * @param dir 
	  *            要删除的目录的文件路径 
	  * @return 目录删除成功返回true，否则返回false 
	  */  
	 public static boolean deleteDirectory(String dir) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteDirectory(String) - start"); //$NON-NLS-1$
		}
  
	  // 如果dir不以文件分隔符结尾，自动添加文件分隔符  
	  if (!dir.endsWith(File.separator))  
	   dir = dir + File.separator;  
	  File dirFile = new File(dir);  
	  // 如果dir对应的文件不存在，或者不是一个目录，则退出  
	  if ((!dirFile.exists()) || (!dirFile.isDirectory())) {  
	   System.out.println("删除目录失败：" + dir + "不存在！");  

			if (logger.isDebugEnabled()) {
				logger.debug("deleteDirectory(String) - end"); //$NON-NLS-1$
			}
	   return false;  
	  }  
	  boolean flag = true;  
	  // 删除文件夹中的所有文件包括子目录  
	  File[] files = dirFile.listFiles();  
	  for (int i = 0; i < files.length; i++) {  
	   // 删除子文件  
	   if (files[i].isFile()) {  
	    flag = DelFileFolderUtil.deleteFile(files[i].getAbsolutePath());  
	    if (!flag)  
	     break;  
	   }  
	   // 删除子目录  
	   else if (files[i].isDirectory()) {  
	    flag = DelFileFolderUtil.deleteDirectory(files[i]  
	      .getAbsolutePath());  
	    if (!flag)  
	     break;  
	   }  
	  }  
	  if (!flag) {  
	   System.out.println("删除目录失败！");  

			if (logger.isDebugEnabled()) {
				logger.debug("deleteDirectory(String) - end"); //$NON-NLS-1$
			}
	   return false;  
	  }  
	  // 删除当前目录  
	  if (dirFile.delete()) {  
	   System.out.println("删除目录" + dir + "成功！");  

			if (logger.isDebugEnabled()) {
				logger.debug("deleteDirectory(String) - end"); //$NON-NLS-1$
			}
	   return true;  
	  } else {  
			if (logger.isDebugEnabled()) {
				logger.debug("deleteDirectory(String) - end"); //$NON-NLS-1$
			}
	   return false;  
	  }  
	 }  
}
