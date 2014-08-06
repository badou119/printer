package com.wx.user.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wx.user.vo.User;
import com.wx.user.vo.UserPo;

public class UserServiceImpl extends HibernateDaoSupport implements UserService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	public void add(User user) {
		logger.info("add(User) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.saveOrUpdate(user);

		logger.info("add(User) - end");
	}

	public void delete(User user) {
		logger.info("delete(User) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.delete(user);

		logger.info("delete(User) - end");
	}

	public User load(String id) {
		logger.info("load(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from User a where a.id = '" + id + "' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(User.class);
		User user = (User) sqlquery.uniqueResult();

		logger.info("load(String) - end");
		return user;
	}

	public List<User> selectUser(String name) {
		logger.info("selectUser(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from User a where a.deleted = '1' and a.name like '%"
				+ name + "%' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(User.class);
		List<User> list = sqlquery.list();

		logger.info("selectUser(String) - end");
		return list;
	}

	public List<UserPo> pageSelect(int page, int rows, int[] totalCount,
			String query1, String enterid) {
		logger.info("pageSelect(int, int, int[], String, String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.*,b.name entername from User a "
				+ " left join enterprise b on a.enterid = b.id"
				+ " where a.deleted = '1' and b.deleted = '1' ";

		if (StringUtils.isNotBlank(query1)) {
			sql += " and a.name like '%" + query1.trim() + "%' ";
		}
		if (StringUtils.isNotBlank(enterid)) {
			sql += " and a.enterid in ('" + enterid + "')";
		}

		sql += " order by regtime desc";

		totalCount[0] = getCount(query1, enterid);
		Query sqlquery = session.createSQLQuery(sql).addEntity(UserPo.class)
				.setFirstResult(page * rows - rows).setMaxResults(rows);

		List<UserPo> list = sqlquery.list();

		logger.info("pageSelect(int, int, int[], String, String) - end");
		return list;
	}

	private int getCount(String peoplename, String enterid) {
		logger.info("getCount(String, String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select count(*) from User a "
				+ " left join enterprise b on a.enterid = b.id "
				+ " where a.deleted = '1' and b.deleted = '1' ";

		if (StringUtils.isNotBlank(peoplename)) {
			sql += " and a.name like '%" + peoplename.trim() + "%' ";
		}
		if (StringUtils.isNotBlank(enterid)) {
			sql += " and a.enterid in ('" + enterid + "')";
		}

		Query sqlquery = session.createSQLQuery(sql);
		int returnint = Integer.parseInt(sqlquery.uniqueResult().toString());

		logger.info("getCount(String, String) - end");
		return returnint;
	}

}
