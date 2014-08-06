package com.wx.common.action;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionContext;
import com.wx.common.utils.ConfigurationFile;
import com.wx.user.vo.User;

import java.io.File;

public class ExcelExportAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcelExportAction.class);
	
	/**
	 * 下载模板文件
	 * @return
	 */
	public HttpServletResponse down(){
		if (logger.isDebugEnabled()) {
			logger.debug("down() - start"); //$NON-NLS-1$
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		try {
			ServletContext sc = ServletActionContext.getServletContext();
			String path = sc.getRealPath("/");
			File file = new File(path+ConfigurationFile.organizisionModel);
			String filename = file.getName();
			
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			logger.error("down()", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("down() - end"); //$NON-NLS-1$
		}
		return response;
	}
	
	//下载导入通讯录时的错误记录文件
	public String downError(){
	
		HttpServletResponse response = ServletActionContext.getResponse();
		//获取企业标示
		User user = (User) ActionContext.getContext().getSession().get("loginUser");
		String groupId = user.getEnterid();
		
		String groupAdmin =user.getId();
		//获取系统根目录的绝对路径
		ServletContext sc=ServletActionContext.getServletContext();
		//获取导出的通讯录的相对路径
		String xlsFile=ConfigurationFile.organizisionRootPath+"/"+groupId+"/"+groupAdmin+"/"+ConfigurationFile.errorFile; //产生的Excel文件的名称
		//获取导出的通讯录的绝对路径
		 
		StringBuffer buffer = new StringBuffer();
		// if (result == 0) {
		buffer.append("{\"result\":\"ok\"}");
		// } else {
		//			
		// buffer.append("{\"result\":\"error\"}");
		// }
		buffer.append(",{\"file\":\"" + xlsFile + "\"}");
		String jsonstr = buffer.toString();
		String jsonData = "[" + jsonstr + "]";
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 下载文件，调用此函数需要传入参数filename,参数的值为文件的相对路径。
	 * 所要下载的文件必须存在与此系统的发布主目录下。
	 * @return
	 */
	public HttpServletResponse downDept(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			response.setContentType("text/html; charset=utf-8");
			ServletContext sc = ServletActionContext.getServletContext();
			String path = sc.getRealPath("/");
			File file = new File(path+request.getParameter("filename"));
			String filename = file.getName();
	        //设置response的编码方式
	        response.setContentType("application/x-msdownload");

	        //写明要下载的文件的大小
	        response.setContentLength((int)file.length());
	        response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
	        //读出文件到i/o流
	        FileInputStream fis=new FileInputStream(file);
	        BufferedInputStream buff=new BufferedInputStream(fis);

	        byte [] buffer=new byte[1024];//相当于我们的缓存
			
	        long k=0;//该值用于计算当前实际下载了多少字节
	        OutputStream toClient=response.getOutputStream();
	        //开始循环下载
	        while(k<file.length()){
	            int j=buff.read(buffer,0,1024);
	            k+=j;
	            //将b中的数据写到客户端的内存
	            toClient.write(buffer,0,j);
	        }
	        //将写入到客户端的内存的数据,刷新到磁盘
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	 /**  
	  *   判断文件夹是否存在,如果不存在则创建文件夹
	  *  @param path 文件路径  
	  */ 
	public  void isExistpath(String path) {
		if (logger.isDebugEnabled()) {
			logger.debug("isExistpath(String) - start"); //$NON-NLS-1$
		}

		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()){
			file.mkdir();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isExistpath(String) - end"); //$NON-NLS-1$
		}
	}
	
	 /**   
	  * 判断文件是否存在,如果不存在则创建文件
	  *  @param filename 文件的绝对路径  
	  */ 
	public  void isExist(String filename) {
		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - start"); //$NON-NLS-1$
		}

		ServletContext sc = ServletActionContext.getServletContext();
		String path = sc.getRealPath("/");
		File filemodel=new File(path+ConfigurationFile.organizisionModel);
		File file = new File(filename); // 
		try {
			file.delete();
			FileUtils.copyFile(filemodel,file);
		} catch (IOException e) {
			logger.error("isExist(String)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - end"); //$NON-NLS-1$
		}
	}
	
}
