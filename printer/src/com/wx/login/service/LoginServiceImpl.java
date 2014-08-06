package com.wx.login.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.xwork2.ActionContext;
import com.wx.common.utils.MD5;
import com.wx.enterprise.vo.Enterprise;
import com.wx.user.vo.User;


public class LoginServiceImpl extends HibernateDaoSupport implements LoginService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

	public int login(User user) {
		logger.info("login(User) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String account = user.getAccount();
		String pwd = user.getPassword();
		
		MD5 md5 = new MD5(pwd);
		pwd = md5.compute();
		user.setPassword(pwd);
		
		String sql = "select * from user where deleted = '1' and account ='"
				+ account + "' and password= '" + pwd + "'";
		Query sqlquery = session.createSQLQuery(sql.toString()).addEntity(
				User.class);

		List<User> userList = sqlquery.list();
		if (userList.size() > 0) {
			User us = userList.get(0);
			user.setDeleted(us.getDeleted());
			user.setEnterid(us.getEnterid());
			user.setId(us.getId());
			user.setName(us.getName());
			user.setPhone(us.getPhone());
			user.setRegtime(us.getRegtime());
			user.setRemark(us.getRemark());
			user.setUpdatetime(us.getUpdatetime());
			int returnint = userList.size();
			logger.info("login(User) - end");
			return returnint;
		} else {
			logger.info("login(User) - end");
			return 0;
		}
	}
	
	public String getMenu(User user) {
		logger.info("getMenu(User) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		
		String userEnterId = user.getEnterid();
		String actionTreeString = ",";
		String actionId = null;
		String menu = null;
		
		if (isCenter(userEnterId)) {//疾控中心用户
			String sql = "select id from systemact order by sort,id ";
			SQLQuery sqlquery = session.createSQLQuery(sql);
			List groupAdminList = sqlquery.list();
			for (int index = 0; index < groupAdminList.size(); index++) {
				actionId = groupAdminList.get(index).toString();
				actionTreeString += actionId + ",";
			}
		}else{
			String sql = "select id from systemact where actdesc='enterprise' order by sort,id ";
			SQLQuery sqlquery = session.createSQLQuery(sql);
			List groupAdminList = sqlquery.list();
			for (int index = 0; index < groupAdminList.size(); index++) {
				actionId = groupAdminList.get(index).toString();
				actionTreeString += actionId + ",";
			}
		}
		
		ActionContext.getContext().getSession().put("actionTreeString",
					actionTreeString);
		
		menu = getMenuByList(actionTreeString);

		logger.info("getMenu(User) - end");
		return menu;
	}
	
	

	public String getMenuByList(String actionTreeString) {
		logger.info("getMenuByList(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		boolean check = false;
		String sql = "select id,name,url,icon from systemact where parentid ='0' order by sort,id ";
		SQLQuery sqlquery = session.createSQLQuery(sql);
		List list = sqlquery.list();
		// 为该用户分配的业务功能列表
		String menu = " {\"menus\":[";
		List serviceActionList = null;
		for (int index = 0; index < list.size(); index++) {
			Object[] objs = (Object[]) list.get(index);
			String actionId = objs[0].toString();
			if (actionTreeString.indexOf("," + actionId) >= 0) {// 用户拥有的权限包含此权限则加载
				check = true;
				menu += "{\"menuid\":\"" + actionId + "\",\"icon\":\""
						+ objs[3].toString() + "\",\"menuname\":\""
						+ objs[1].toString() + "\",\"menus\":";
				menu += getMenuByParentAction(actionId, actionTreeString);
				menu += "},";

			}
		}
		if (check) {
			menu = menu.substring(0, menu.length() - 1);// 去掉字符串最后的“,”符号
		}
		menu += "]}";

		logger.info("getMenuByList(String) - end");
		return menu;
	}

	public String getMenuByParentAction(String actionId, String actionTreeString) {
		logger.info("getMenuByParentAction(String, String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		boolean check = false;
		String menu = "[";
		String sql = "select id,name,url,icon from systemact where parentid='"
				+ actionId + "'  order by sort,id ";
		SQLQuery sqlquery = session.createSQLQuery(sql);
		List serviceActionMenuList = sqlquery.list();
		for (int serviceActionMenuListIndex = 0; serviceActionMenuListIndex < serviceActionMenuList
				.size(); serviceActionMenuListIndex++) {
			Object[] serviceMenuObjs = (Object[]) serviceActionMenuList
					.get(serviceActionMenuListIndex);
			if (actionTreeString.indexOf(serviceMenuObjs[0].toString()) >= 0) {// 用户拥有的权限包含此权限则加载
				check = true;
				menu += "{\"menuid\":\"" + serviceMenuObjs[0].toString()
						+ "\",\"menuname\":\"" + serviceMenuObjs[1].toString()
						+ "\",\"icon\":\"" + serviceMenuObjs[3].toString()
						+ "\",\"url\":\"" + serviceMenuObjs[2].toString()
						+ "\",\"menus\":";
				menu += getMenuByParentAction(serviceMenuObjs[0].toString(),
						actionTreeString);
				menu += "},";
			}
		}
		if (check) {
			menu = menu.substring(0, menu.length() - 1);// 去掉字符串最后的“,”符号
		}
		menu += "]";

		logger.info("getMenuByParentAction(String, String) - end");
		return menu;
	}
	
	//疾控中心
	public boolean isCenter(String enterId) {
		logger.info("isCenter(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from enterprise ");
		sql.append(" where id = '" + enterId + "' and parentid='0'  ");
		Query sqlquery = session.createSQLQuery(sql.toString()).addEntity(
				Enterprise.class);

		List<Enterprise> list = sqlquery.list();
		if (list.size() > 0) {
			logger.info("isCenter(String) - end");
			return true;
		}

		logger.info("isCenter(String) - end");
		return false;
	}
	
	public void modifySuperPWD(String id, String pwd) {
		logger.info("modifySuperPWD(String, String) - start");

		MD5 md5 = new MD5(pwd);
		pwd = md5.compute();
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "update user set password = '" + pwd
				+ "' where id= '" + id + "' ";
		session.createSQLQuery(sql).executeUpdate();

		logger.info("modifySuperPWD(String, String) - end");
	}
}
