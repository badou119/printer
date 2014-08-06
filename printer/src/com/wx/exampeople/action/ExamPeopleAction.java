package com.wx.exampeople.action;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.wx.common.utils.Base64;
import com.wx.common.utils.CommonUtil;
import com.wx.common.utils.ConfigurationFile;
import com.wx.common.utils.Constants;
import com.wx.common.utils.DataGridJson;
import com.wx.common.utils.FileUtils;
import com.wx.common.utils.Tools;
import com.wx.enterprise.service.EnterpriseService;
import com.wx.enterprise.vo.Enterprise;
import com.wx.exampeople.service.ExamPeopleService;
import com.wx.exampeople.service.JkzseqService;
import com.wx.exampeople.service.ProfessionService;
import com.wx.exampeople.vo.ExamPeople;
import com.wx.exampeople.vo.ExamPeoplePo;
import com.wx.exampeople.vo.Jkzseq;
import com.wx.exampeople.vo.Profession;
import com.wx.user.vo.User;

public class ExamPeopleAction extends ActionSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExamPeopleAction.class);

	private ExamPeoplePo examPeoplePo;
	private ExamPeopleService examPeopleService;
	private EnterpriseService enterpriseService;
	private JkzseqService jkzseqService;
	private ProfessionService professionService;
	
	/** */
	private static final long serialVersionUID = 1L;

	private User user = null;

	@Override
	public void validate() {
		logger.info("validate() - start");
		super.validate();
		
		user = (User) ActionContext.getContext().getSession().get("loginUser");
		if (user == null) {
			logger.info("validate() - end");
			return;
		}

		logger.info("validate() - end");
	}

	public String toAdd() {
		logger.info("toAdd() - start");

		Enterprise enterprise = enterpriseService.load(user.getEnterid());
		examPeoplePo = new ExamPeoplePo();
		if (enterprise != null) {
			examPeoplePo.setEnterid(enterprise.getId());
			examPeoplePo.setEntername(enterprise.getName());
		}
		
		String jkzcode = "";
		String ymd = Tools.getTodayStr();
		String lsh = "0001";
		Jkzseq jkzseq = jkzseqService.get(enterprise.getId(), ymd);
		if(jkzseq != null){
			lsh = getjkzseq(jkzseq.getJkzseq());
			jkzseq.setJkzseq(lsh);//流水号+1
		}else{
			jkzseq = new Jkzseq();
			jkzseq.setId(CommonUtil.getUUID());
			jkzseq.setEnterid(enterprise.getId());
			jkzseq.setJkzseq("0001");
			jkzseq.setYmd(ymd);
		}
		jkzseqService.addOrUpdate(jkzseq);
		
		jkzcode = ymd+enterprise.getEntercode()+lsh;
		examPeoplePo.setJkzcode(jkzcode);
		
		examPeoplePo.setExamtimeStr(Tools.formatDate(new Date(), Constants.FORMAT_YYYY_MM_DD));
		examPeoplePo.setIsprinted(0);
		examPeoplePo.setTocenter(0);
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Profession> proList =  professionService.select();
		request.setAttribute("proList", proList);
		
		logger.info("toAdd() - end");
		return Action.SUCCESS;
	}

	public String add() {
		logger.info("add() - start");

		ExamPeople examPeople = new ExamPeople();
		copy(examPeople);
		examPeopleService.add(examPeople);

		HttpServletRequest request = ServletActionContext.getRequest();
		List<Enterprise> enterList =  enterpriseService.selectEnterprise("");
		request.setAttribute("enterList", enterList);
		
		logger.info("add() - end");
		return Action.SUCCESS;
	}

	public String toEdit() {
		logger.info("toEdit() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		ExamPeople examPeople = examPeopleService.load(id);
		Enterprise enterprise = enterpriseService.load(examPeople.getEnterid());
		examPeoplePo = new ExamPeoplePo();
		copyPo(examPeople);
		examPeoplePo.setEntername(enterprise.getName());

		examPeoplePo.setExamtimeStr(Tools.formatDate(examPeoplePo.getExamtime(), Constants.FORMAT_YYYY_MM_DD));
		
		List<Profession> proList =  professionService.select();
		request.setAttribute("proList", proList);
		
		logger.info("toEdit() - end");
		return Action.SUCCESS;
	}

	public String toPrintPage() {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		List<Enterprise> enterList =  enterpriseService.selectEnterprise("");
		request.setAttribute("enterList", enterList);
		
		return Action.SUCCESS;
	}
	
	public String delete() {
		logger.info("delete() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		ExamPeople examPeople = examPeopleService.load(id);
		examPeople.setDeleted(0);
		examPeopleService.add(examPeople);

		List<Enterprise> enterList =  enterpriseService.selectEnterprise("");
		request.setAttribute("enterList", enterList);
		
		logger.info("delete() - end");
		return Action.SUCCESS;
	}

	public String list() {
		logger.info("list() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String peoplename = request.getParameter("peoplename");
		String type = request.getParameter("type");
		
		Enterprise enterprise = enterpriseService.load(user.getEnterid());
		
		int[] totalCount = new int[] { 0 };
		String enterid = user.getEnterid();
		if (StringUtils.isNotEmpty(enterprise.getParentid())&&"0".equals(enterprise.getParentid())) {
			// 如果是空，就查登录用户的企业单位体检人信息
			enterid = "";
		}
		if(StringUtils.isNotEmpty(type)&&"print".equals(type)){
			enterid = request.getParameter("selectenterid");
		}

		List<ExamPeoplePo> list = examPeopleService.pageSelect(page, rows, totalCount, peoplename, enterid);

		DataGridJson dataGridJson = new DataGridJson();
		dataGridJson.total = totalCount[0];
		dataGridJson.rows = list;
		// HH:mm:ss
		Gson gson = new GsonBuilder().setDateFormat(Constants.FORMAT_YYYY_MM_DD).create();
		String jsonData = gson.toJson(dataGridJson);
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(jsonData);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("list()", e);

			e.printStackTrace();
		}

		logger.info("list() - end");
		return null;
	}

	public String toCam() {
		logger.info("toCam() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		ExamPeople examPeople = examPeopleService.load(id);
		Enterprise enterprise = enterpriseService.load(examPeople.getEnterid());
		examPeoplePo = new ExamPeoplePo();
		copyPo(examPeople);
		
		String jkzCode = examPeople.getJkzcode();
		if(StringUtils.isBlank(jkzCode)){
			String ymd = Tools.getTodayStr();
			String lsh = "0001";
			Jkzseq jkzseq = jkzseqService.get(examPeople.getEnterid(), ymd);
			if(jkzseq != null){
				lsh = getjkzseq(jkzseq.getJkzseq());
				jkzseq.setJkzseq(lsh);//流水号+1
			}else{
				jkzseq = new Jkzseq();
				jkzseq.setId(CommonUtil.getUUID());
				jkzseq.setEnterid(examPeople.getEnterid());
				jkzseq.setJkzseq("0001");
				jkzseq.setYmd(ymd);
			}
			jkzseqService.addOrUpdate(jkzseq);
			jkzCode = ymd+enterprise.getEntercode()+lsh;
		}
		
		examPeople.setJkzcode(jkzCode);
		examPeopleService.add(examPeople);
		
		examPeoplePo.setJkzcode(jkzCode);
		examPeoplePo.setEntername(enterprise.getName());
		examPeoplePo.setEnterlogo(enterprise.getLogo());
		
		logger.info("toCam() - end");
		return Action.SUCCESS;
	}
	
	public String uploadExamPic() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String exampeopleid = request.getParameter("exampeopleid");
		String img = request.getParameter("fileinput");
		
		logger.info("upload img=" + img);

		String realpath = ServletActionContext.getServletContext().getRealPath("/");
		String filepath = ConfigurationFile.pictureRootPath + "/" + user.getEnterid() + "/" + Tools.getTodayStr();

		String savefilepath = filepath + "/" + CommonUtil.getUUID() + ".png";
		
		String imgStr = img.substring(30);// 去掉头文件
		logger.info("截取文件信息substring(30)后 imgStr=" + imgStr);
		try {
			imgStr = URLDecoder.decode(imgStr, "UTF-8");
			logger.info("截取文件信息后 URLDecoder.decode imgStr=" + imgStr);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String imgmsg = "";
		try {
			FileUtils.isExist(realpath + savefilepath);
		} catch (IOException e1) {
			imgmsg = "照片文件上传失败，系统异常！";
			e1.printStackTrace();
		}
		byte[] imgdata = Base64.getImgData(imgStr);
		Base64.saveImage(imgdata, realpath + savefilepath);
		logger.info("realpath+savefilepath=" + realpath + savefilepath);
		
		String jsonData = "";
		
		if(StringUtils.isBlank(imgmsg)){
			jsonData = "[{\"result\":\"ok\",\"message\":\"照片上传成功！\",\"filepath\":\""+savefilepath+"\"}]";
		}else{
			jsonData = "[{\"result\":\"error\",\"message\":\"照片文件上传失败，系统异常！\",\"filepath\":\""+savefilepath+"\"}]";
		}
		
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(jsonData);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("uploadExamPic()", e);
			e.printStackTrace();
		}
		return null;
	}

	private void copy(ExamPeople examPeople) {
		logger.info("copy(ExamPeople) - start");

		if (StringUtils.isNotEmpty(examPeoplePo.getId())) {
			examPeople.setId(examPeoplePo.getId());
		} else {
			examPeople.setId(CommonUtil.getUUID());
			examPeople.setRegtime(new Date());
		}

		examPeople.setAddress(examPeoplePo.getAddress());
		examPeople.setAge(examPeoplePo.getAge());
		examPeople.setCode(examPeoplePo.getCode());
		examPeople.setEnterid(examPeoplePo.getEnterid());
		examPeople.setExamtime(examPeoplePo.getExamtime());
		examPeople.setName(examPeoplePo.getName());
		examPeople.setPhone(examPeoplePo.getPhone());
		examPeople.setProfession(examPeoplePo.getProfession());
		examPeople.setRemark(examPeoplePo.getRemark());
		examPeople.setIsprinted(examPeoplePo.getIsprinted());
		examPeople.setTocenter(examPeoplePo.getTocenter());
		examPeople.setSex(examPeoplePo.getSex());
		examPeople.setUpdatetime(new Date());

		examPeople.setDeleted(1);
		
		logger.info("copy(ExamPeople) - end");
	}

	private void copyPo(ExamPeople examPeople) {
		logger.info("copyPo(ExamPeople) - start");

		examPeoplePo.setId(examPeople.getId());
		examPeoplePo.setAddress(examPeople.getAddress());
		examPeoplePo.setAge(examPeople.getAge());
		examPeoplePo.setCode(examPeople.getCode());
		examPeoplePo.setEnterid(examPeople.getEnterid());
		
		examPeoplePo.setExamtime(examPeople.getExamtime());
		if(examPeople.getExamtime() != null){
			Integer[] ymd = getYMD(examPeople.getExamtime());
			examPeoplePo.setExamtimeY(ymd[0]);
			examPeoplePo.setExamtimeM(ymd[1]);
			examPeoplePo.setExamtimeD(ymd[2]);
			examPeoplePo.setValidtimeY(ymd[0]+1);
			examPeoplePo.setValidtimeM(ymd[1]);
			examPeoplePo.setValidtimeD(ymd[2]);
		}
	
		examPeoplePo.setName(examPeople.getName());
		examPeoplePo.setPhone(examPeople.getPhone());
		examPeoplePo.setProfession(examPeople.getProfession());
		examPeoplePo.setRemark(examPeople.getRemark());
		examPeoplePo.setRegtime(examPeople.getRegtime());
		examPeoplePo.setSex(examPeople.getSex());
		examPeoplePo.setUpdatetime(examPeople.getUpdatetime());
		examPeoplePo.setDeleted(examPeople.getDeleted());
		examPeoplePo.setIsprinted(examPeople.getIsprinted());
		examPeoplePo.setTocenter(examPeople.getTocenter());
		examPeoplePo.setPic(examPeople.getPic());
		examPeoplePo.setJkzcode(examPeople.getJkzcode());
		
		logger.info("copyPo(ExamPeople) - end");
	}

	private Integer[] getYMD(Date date){
		Integer[] str= new Integer[3];
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		str[0] = rightNow.get(Calendar.YEAR);
		str[1] = rightNow.get(Calendar.MONTH);
		str[2] = rightNow.get(Calendar.DAY_OF_MONTH);
		return str;
	}
	
	private String getjkzseq(String jkzseq){
		String lsh = "0000"+(Integer.parseInt(jkzseq)+1);
		lsh = lsh.substring(lsh.length()-4, lsh.length());
		return lsh;
	}
	/**
	 * @param exampeopleService
	 *            the exampeopleService to set
	 */
	public void setExamPeopleService(ExamPeopleService examPeopleService) {
		this.examPeopleService = examPeopleService;
	}

	public ExamPeoplePo getExamPeoplePo() {
		return examPeoplePo;
	}

	public void setExamPeoplePo(ExamPeoplePo examPeoplePo) {
		this.examPeoplePo = examPeoplePo;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public JkzseqService getJkzseqService() {
		return jkzseqService;
	}

	public void setJkzseqService(JkzseqService jkzseqService) {
		this.jkzseqService = jkzseqService;
	}

	public ExamPeopleService getExamPeopleService() {
		return examPeopleService;
	}

	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public ProfessionService getProfessionService() {
		return professionService;
	}

	public void setProfessionService(ProfessionService professionService) {
		this.professionService = professionService;
	}

}
