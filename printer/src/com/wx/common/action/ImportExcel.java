package com.wx.common.action;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.wx.common.utils.ConfigurationFile;
import com.wx.common.utils.ReadCsv;
import com.wx.common.utils.XLS2CSVmra;
import com.wx.enterprise.service.EnterpriseService;
import com.wx.exampeople.service.ExamPeopleService;
import com.wx.exampeople.service.JkzseqService;
import com.wx.exampeople.service.ProfessionService;
import com.wx.exampeople.vo.ExamPeople;
import com.wx.user.vo.User;


public class ImportExcel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImportExcel.class);
	

	private File photos;

	private String[] photosFileName;
	private String[] photosContentType;

	// 获取实际文件数据，属性名与控件的name属性值相同
	private File fileInput;

	// 获取文件名,格式：控件的name属性值+FileName
	private String[] fileInputFileName;

	// 获取文件类型,格式：控件的name属性值+ContentType
	private String[] fileInputContentType;

	private ExamPeopleService examPeopleService;
	private EnterpriseService enterpriseService;
	private JkzseqService jkzseqService;
	private ProfessionService professionService;

	/**
	 * 上传图片的处理函数，将上传的图片存储的制定路径下
	 * @return
	 * @throws Exception
	 */
	public String uploadPic() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("uploadPic() - start"); //$NON-NLS-1$
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		ServletContext sc = ServletActionContext.getServletContext();
		String path = sc.getRealPath("/"+ConfigurationFile.logoRootPath);
		
		isExist(path);

		int status = 0;// 状态
		String message = "";// 反馈信息
		if (fileInput == null) {
			status = 1;//文件不存在
			message = "请先选择文件再上传";
		} else {
			if (fileInput.length() <= 300 * 1024) {
				String fileExtension = "";
				try {
					fileExtension = fileInputFileName[0].substring(
							fileInputFileName[0].lastIndexOf("."),
							fileInputFileName[0].length()).toLowerCase();
				} catch (Exception e) {
					logger.error("uploadPic()", e); //$NON-NLS-1$
					fileExtension = "";
				}
				String filename = "";
				
				filename = UUID.randomUUID().toString().replace("-", "")
							+ fileExtension;

				if (fileExtension.equals(".jpg")||fileExtension.equals(".png")||fileExtension.equals(".jpeg")) {
					FileUtils.copyFile(fileInput, new File(path, filename));
					status = 0;
					message = ConfigurationFile.logoRootPath + "/" + filename;
				} else {
					status = 2;//状态不符合格式
					message = "";
				}
			}else{
				status=3;//状态文件太大
				message = "";
			}
		}
		
		response.setCharacterEncoding("utf-8");

		String script = "<script language='javascript'> window.parent.uploadImageResponse(\"[{'status':\'"
				+ status + "\','message':\'" + message + "\'}]\"); </script>";
		response.getWriter().println(script);

		if (logger.isDebugEnabled()) {
			logger.debug("uploadPic() - end");
		}
		return null;
	}
	/**
	 * 处理通讯录导入的过程，将上传的文件保存到制定路径下。
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("execute() - start"); //$NON-NLS-1$
		}
	
		HttpServletResponse response = ServletActionContext.getResponse();
		int status = 0;// 状态
		String message = "";
		// 项目的根路径
		ServletContext sc = ServletActionContext.getServletContext();
		String patherror= sc.getRealPath("/");
		String path = ServletActionContext.getServletContext().getInitParameter("organizisionFileUp"); 
		patherror+=ConfigurationFile.organizisionRootPath;
		path += ConfigurationFile.organizisionRootPath;
		 
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		//获取企业标示
		String groupId = user.getEnterid();
		//获取当前登录企业管理员的用户名
		
		String groupAdmin = user.getId();
		
		if (groupId == null) {
			groupId = "null";
		}
		path = path + "/" + groupId+"/"+groupAdmin;
		isExist(patherror + "/" + groupId);
		patherror = patherror + "/" + groupId+"/"+groupAdmin;
		isExist(path);
		isExist(patherror);

		if (fileInput != null) {// 上传的文件存在
			if (fileInput.length() <= 50*1024 * 1024) {
			String fileExtension = "";
			try {
				fileExtension = fileInputFileName[0].substring(
						fileInputFileName[0].lastIndexOf("."),
						fileInputFileName[0].length()).toLowerCase();// 获取上传文件扩展名
			} catch (Exception e) {
					logger.error("execute()", e); //$NON-NLS-1$

				fileExtension = "";
			}
			String filename = UUID.randomUUID().toString().replace("-", "")
					+ fileExtension;
			if (fileExtension.equals(".xls")|| fileExtension.equals(".csv")) {// 判断文件类型是否符合。
				FileUtils.copyFile(fileInput, new File(path, filename));// 文件保存到服务器中。
				message = readExcel(path,patherror,filename);
				status = 0;

			} else {
				status = 2;//文件格式不符合

			}}else{
				status = 3;//状态文件太大
			}

		} else {
			status = 1;//文件不存在
		}
		message = "{'status':'" + status + "'}," + message;
		String jsonData = "[" + message + "]";

		String script = "<script language='javascript'> window.parent.uploadImageResponse(\""
				+ jsonData + "\"); </script>";

		response.setCharacterEncoding("utf-8");
		response.getWriter().println(script);

		if (logger.isDebugEnabled()) {
			logger.debug("execute() - end"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * 读取上传的excel文件，将文件转存为csv格式，批量导入临时表。然后根据临时表与原表对比插入。
	 */
	public String readExcel(String path,String patherror,String filename) {
		if (logger.isDebugEnabled()) {
			logger.debug("readExcel(String, String, String) - start"); //$NON-NLS-1$
		}

		StringBuffer buffer = new StringBuffer();
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String enterId = user.getEnterid();
		List<ExamPeople> eList = null;
		try {
			// 获取文件后缀
			String fileExtension = filename.substring(
					filename.lastIndexOf("."), filename.length()).toLowerCase();
			isExist(path);
			
			//将要转存为的csv文件的绝对路径
			String filecsvname = path + "/"
					+ UUID.randomUUID().toString().replace("-", "") + ".csv";
			File file = new File(filecsvname);
			file.createNewFile();// 创建csv文件
			FileOutputStream filewrite = new FileOutputStream(filecsvname);
			OutputStreamWriter ouputfile = new OutputStreamWriter(filewrite,
					"UTF-8");
            //将要进行的错误记录的文件
			
			isExist(patherror);
			
			String errorFile = patherror + "/"
					+ ConfigurationFile.errorFile;
			File fileError = new File(errorFile);
			fileError.createNewFile();// 创建csv文件
			FileOutputStream errorFileStream = new FileOutputStream(errorFile);
			OutputStreamWriter errorFileWriter = new OutputStreamWriter(
					errorFileStream);
			int totalcount=0;//符合格式的总条数
			int errorCount=0;//不符合格式的总条数
		
			if (fileExtension.equals(".xls")) {// 文件为excel2003文件
				//首先将excel2003文件直接转换为csv文件的格式，然后对临时的csv文件进行处理
				String filecsvnameInterim = path + "/"
						+ UUID.randomUUID().toString().replace("-", "") + ".csv";
				File fileInterim = new File(filecsvnameInterim);
				fileInterim.createNewFile();// 创建临时csv文件
				FileOutputStream filewriteInterim = new FileOutputStream(filecsvnameInterim);
				OutputStreamWriter ouputfileInterim = new OutputStreamWriter(filewriteInterim);
				//将excel文件转存为临时csv文件
				XLS2CSVmra xls2csv = new XLS2CSVmra(path + "/" + filename, 10,
						ouputfileInterim, enterId, errorFileWriter);
				xls2csv.process();
				
				ouputfileInterim.close();
				filewriteInterim.flush();
				filewriteInterim.close();
				xls2csv=null;
				//处理临时的csv文件
				ReadCsv readcsv=new ReadCsv(filecsvnameInterim, 10, ouputfile, 
						enterId,errorFileWriter,enterpriseService,jkzseqService,professionService);
				totalcount=readcsv.totalCount;
				errorCount=readcsv.errorCount;
				eList = readcsv.epList;
				readcsv=null;
				//删除临时csv文件
				fileInterim.delete();
			}else{
				ReadCsv readcsv=new ReadCsv(path + "/" + filename, 10, ouputfile, 
						enterId,errorFileWriter,enterpriseService,jkzseqService,professionService);
				totalcount=readcsv.totalCount;
				errorCount=readcsv.errorCount;
				eList = readcsv.epList;
				readcsv=null;
			}
			
			examPeopleService.saveList(eList);
			
			
			errorFileWriter.close();
			errorFileStream.flush();
			errorFileStream.close();

			ouputfile.close();
			filewrite.flush();
			filewrite.close();

            file.delete();
            file=new File(path,filename);
            file.delete();
            
			buffer.append("{'result':'ok','rownum':'"+totalcount+"','totalcount':'"+totalcount+"','errorcount':'"+(errorCount>2?errorCount:0)+"'}");
		} catch (Exception e) {
			logger.error("readExcel(String, String, String)", e); //$NON-NLS-1$

			buffer.append("{'result':'error'}");
			e.printStackTrace();
		}
		String jsonstr = buffer.toString();

		if (logger.isDebugEnabled()) {
			logger.debug("readExcel(String, String, String) - end"); //$NON-NLS-1$
		}
		return jsonstr;
	}

	/**
	 * 判断文件夹是否存在,如果不存在则创建文件夹
	 * @param path
	 *            文件路径
	 */
	public void isExist(String path) {
		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - start"); //$NON-NLS-1$
		}

		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()){
			file.mkdir();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isExist(String) - end"); //$NON-NLS-1$
		}
	}


	public File getPhotos() {
		return photos;
	}

	public void setPhotos(File photos) {
		this.photos = photos;
	}

	public String[] getPhotosFileName() {
		return photosFileName;
	}

	public void setPhotosFileName(String[] photosFileName) {
		this.photosFileName = photosFileName;
	}

	public String[] getPhotosContentType() {
		return photosContentType;
	}

	public void setPhotosContentType(String[] photosContentType) {
		this.photosContentType = photosContentType;
	}

	public File getFileInput() {
		return fileInput;
	}

	public void setFileInput(File fileInput) {
		this.fileInput = fileInput;
	}

	public String[] getFileInputFileName() {
		return fileInputFileName;
	}

	public void setFileInputFileName(String[] fileInputFileName) {
		this.fileInputFileName = fileInputFileName;
	}

	public String[] getFileInputContentType() {
		return fileInputContentType;
	}

	public void setFileInputContentType(String[] fileInputContentType) {
		this.fileInputContentType = fileInputContentType;
	}
	/**
	 * @param examPeopleService the examPeopleService to set
	 */
	public void setExamPeopleService(ExamPeopleService examPeopleService) {
		this.examPeopleService = examPeopleService;
	}
	/**
	 * @return the enterpriseService
	 */
	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}
	/**
	 * @param enterpriseService the enterpriseService to set
	 */
	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	/**
	 * @return the examPeopleService
	 */
	public ExamPeopleService getExamPeopleService() {
		return examPeopleService;
	}
	public JkzseqService getJkzseqService() {
		return jkzseqService;
	}
	public void setJkzseqService(JkzseqService jkzseqService) {
		this.jkzseqService = jkzseqService;
	}
	public ProfessionService getProfessionService() {
		return professionService;
	}
	public void setProfessionService(ProfessionService professionService) {
		this.professionService = professionService;
	}


}
