package com.wx.enterprise.action;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.wx.common.utils.CommonUtil;
import com.wx.common.utils.Constants;
import com.wx.common.utils.DataGridJson;
import com.wx.enterprise.service.EnterpriseService;
import com.wx.enterprise.vo.Enterprise;
import com.wx.exampeople.vo.ExamPeople;
import com.wx.exampeople.vo.ExamPeoplePo;
import com.wx.user.vo.User;

public class EnterpriseAction extends ActionSupport{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EnterpriseAction.class);

	private EnterpriseService enterpriseService;
	private Enterprise enterprise;
	
	/** */
	private static final long serialVersionUID = 1L;

	private User user = null;
	
	@Override
	public void validate() {
		logger.info("validate() - start");

		super.validate();
		user = (User)ActionContext.getContext().getSession().get("loginUser");
		if(user==null){
			logger.info("validate() - end");
			return;
		}

		logger.info("validate() - end");
	}
	
	public String toAdd(){
		logger.info("toAdd() - start");
		
		enterprise = new Enterprise();

		logger.info("toAdd() - end");
		return Action.SUCCESS;
	}
	
	public String add(){
		logger.info("add() - start");

		if(enterprise != null){
			if(StringUtils.isBlank(enterprise.getId())){
				enterprise.setId(CommonUtil.getUUID());
				enterprise.setParentid(user.getEnterid());
				enterprise.setRegtime(new Date());
				enterprise.setDeleted(1);
			}
			enterprise.setUpdatetime(new Date());
			enterpriseService.add(enterprise);
		}

		logger.info("add() - end");
		return Action.SUCCESS;
	}
	
	public String toEdit(){
		logger.info("toEdit() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		enterprise = enterpriseService.load(id);
		
		logger.info("toEdit() - end");
		return Action.SUCCESS;
	}

	public String delete(){
		logger.info("delete() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		enterprise = enterpriseService.load(id);
		enterprise.setDeleted(0);
		enterpriseService.add(enterprise);

		logger.info("delete() - end");
		return Action.SUCCESS;
	}

	public String list(){
		logger.info("list() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
	
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String peoplename = request.getParameter("peoplename");
		
		int[] totalCount = new int[] { 0 };
		List<Enterprise> list = enterpriseService.pageSelect(page, rows, totalCount, peoplename);

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
	/**
	 * @param enterpriseService the enterpriseService to set
	 */
	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	/**
	 * @return the enterprise
	 */
	public Enterprise getEnterprise() {
		return enterprise;
	}

	/**
	 * @param enterprise the enterprise to set
	 */
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	/**
	 * @return the enterpriseService
	 */
	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}
	
}
