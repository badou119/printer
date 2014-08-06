package com.wx.login.action;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.wx.common.utils.MD5;
import com.wx.login.service.LoginService;
import com.wx.user.vo.User;

public class LoginAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginAction.class);

	private LoginService loginService;
	private User user;

	/**
	 * 验证用户登录的用户名密码是否正确。并且获取用户的权限对应的功能列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		logger.info("login() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String username = request.getParameter("username");
		String pwd = request.getParameter("password");

		StringBuffer buffer = new StringBuffer();

		// 用户登陆
		user = new User();
		user.setAccount(username);
		user.setPassword(pwd);

		int result = loginService.login(user);
		if (result > 0) {
			saveCookie(request, response, username, pwd);
			// 将登录管理员的用户信息存储到session中
			setSession();
			// 获取登录的企业管理员的功能列表的json串，并且存储到session中。
			String menu = loginService.getMenu(user);
			ActionContext.getContext().getSession().put("menu", menu);
			if (!menu.trim().equals("{\"menus\":[]}")
					&& (!menu.trim().equals(""))) {
				buffer.append("{\"result\":\"ok\"}");
			} else {// 未分配业务权限
				buffer.append("{\"result\":\"actionerror\"}");
			}
		} else {
			buffer.append("{\"result\":\"error\"}");
		}

		String jsonstr = buffer.toString();
		String jsonData = "[" + jsonstr + "]";
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(jsonData);

		logger.info("login() - end");
		return null;
	}

	private void saveCookie(HttpServletRequest request,
			HttpServletResponse response, String username, String pwd) {
		logger.info("saveCookie(HttpServletRequest, HttpServletResponse, String, String) - start");

		try {
			// 将用户名密码存储到cookie中。用于首页的记住密码功能。
			if (request.getParameter("rePassWord") != null
					&& request.getParameter("rePassWord").equals("on")) {
				Cookie user = new Cookie("user", username + "'" + pwd);
				user.setMaxAge(60 * 24 * 15);
				response.addCookie(user);
			} else {
				Cookie user = new Cookie("user", username + "'" + pwd);
				user.setMaxAge(0);
				response.addCookie(user);
			}
		} catch (Exception e) {
			logger.warn("saveCookie(HttpServletRequest, HttpServletResponse, String, String) - exception ignored", e);
			
		}

		logger.info("saveCookie(HttpServletRequest, HttpServletResponse, String, String) - end");
	}

	private void setSession() {
		logger.info("setSession() - start");

		ActionContext.getContext().getSession().clear();
		ActionContext.getContext().getSession().put("loginUser", user);

		logger.info("setSession() - end");
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String uppwd() {
		logger.info("uppwd() - start");

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		StringBuffer buffer = new StringBuffer();
		User user = (User) ActionContext
				.getContext().getSession().get("loginUser");
		
		String oldpwd = request.getParameter("oldpwd");
		
		MD5 md5 = new MD5(oldpwd);
		oldpwd = md5.compute();
		
		String pwd = request.getParameter("pwd");

		try {
			// 修改系统管理员的密码
			if (user != null && (!user.getId().equals(""))) {
				if (!oldpwd.equals(user.getPassword())) {
					buffer.append("{\"result\":\"pwderror\"}");
				} else {
					loginService.modifySuperPWD(user.getId(), pwd);
					buffer.append("{\"result\":\"ok\"}");
				}
			} 
		} catch (Exception e) {
			logger.error("uppwd()", e);

			buffer.append("{\"result\":\"error\"}");
		}
		
		try {
			String jsonstr = buffer.toString();
			String jsonData = "[" + jsonstr + "]";
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(jsonData);
		} catch (IOException e) {
			logger.error("uppwd()", e);

			e.printStackTrace();
		}

		logger.info("uppwd() - end");
		return null;
	}

	public String logout() {
		logger.info("logout() - start");

		ActionContext.getContext().getSession().clear();

		logger.info("logout() - end");
		return Action.SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param loginService the loginService to set
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
