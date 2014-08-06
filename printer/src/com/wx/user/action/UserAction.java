package com.wx.user.action;

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
import com.wx.common.utils.MD5;
import com.wx.enterprise.service.EnterpriseService;
import com.wx.enterprise.vo.Enterprise;
import com.wx.user.service.UserService;
import com.wx.user.vo.User;
import com.wx.user.vo.UserPo;

public class UserAction extends ActionSupport{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAction.class);

	private UserService userService;
	private EnterpriseService enterpriseService;
	
	/** */
	private static final long serialVersionUID = 1L;

	private User user ;

	private User loginUser = null;
	
	@Override
	public void validate() {
		logger.info("validate() - start");

		super.validate();
		loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
		if(loginUser==null){
			logger.info("validate() - end");
			return;
		}

		logger.info("validate() - end");
	}
	public String toAdd(){
		logger.info("toAdd() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		user = new User();
		List<Enterprise> enterList = enterpriseService.selectEnterprise("");
		request.setAttribute("enterList", enterList);

		logger.info("toAdd() - end");
		return Action.SUCCESS;
	}

	public String add(){
		logger.info("add() - start");

		if(user != null){
			if(StringUtils.isBlank(user.getId())){
				user.setId(CommonUtil.getUUID());
				user.setRegtime(new Date());
				user.setDeleted(1);
				
				String pwd = "123456";
				MD5 md5 = new MD5(pwd);
				pwd = md5.compute();
				user.setPassword(pwd);
				
			}
			user.setUpdatetime(new Date());
			userService.add(user);
		}

		logger.info("add() - end");
		return Action.SUCCESS;
	}
	
	public String toEdit(){
		logger.info("toEdit() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		user = userService.load(id);
		
		List<Enterprise> enterList = enterpriseService.selectEnterprise("");
		request.setAttribute("enterList", enterList);
		
		logger.info("toEdit() - end");
		return Action.SUCCESS;
	}

	public String delete(){
		logger.info("delete() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		user = userService.load(id);
		user.setDeleted(0);
		userService.add(user);

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
		List<UserPo> list = userService.pageSelect(page, rows, totalCount, peoplename,null);

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
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
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
	
}
