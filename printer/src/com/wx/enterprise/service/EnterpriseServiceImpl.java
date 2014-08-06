package com.wx.enterprise.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wx.enterprise.vo.Enterprise;

public class EnterpriseServiceImpl extends HibernateDaoSupport implements EnterpriseService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EnterpriseServiceImpl.class);

	public void add(Enterprise enterprise) {
		logger.info("add(Enterprise) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.saveOrUpdate(enterprise);

		logger.info("add(Enterprise) - end");
	}
	
	public void delete(Enterprise enterprise) {
		logger.info("delete(Enterprise) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.delete(enterprise);

		logger.info("delete(Enterprise) - end");
	}

	public Enterprise load(String id) {
		logger.info("load(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from Enterprise a where a.deleted = '1' and a.id = '"+id+"' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(Enterprise.class);
		Enterprise enterprise =  (Enterprise)sqlquery.uniqueResult() ;

		logger.info("load(String) - end");
		return enterprise;
	}
	public Enterprise getEnterprise(String name) {
		logger.info("getEnterprise(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from Enterprise a where a.deleted = '1' and a.name = '"+name+"' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(Enterprise.class);
		Enterprise ep =  (Enterprise) sqlquery.uniqueResult() ;

		logger.info("getEnterprise(String) - end");
		return ep;
	}
	
	public List<Enterprise> selectEnterprise(String name) {
		logger.info("selectEnterprise(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from Enterprise a where a.deleted = '1' and a.name like '%"+name+"%' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(Enterprise.class);
		List<Enterprise> list =  sqlquery.list() ;

		logger.info("selectEnterprise(String) - end");
		return list;
	}
	
	public List<Enterprise> pageSelect(int page, int rows, int[] totalCount, String query1) {
		logger.info("pageSelect(int, int, int[], String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from enterprise a where a.deleted = '1' ";
		
		if(StringUtils.isNotBlank(query1)){
			sql += " and a.name like '%"+query1.trim()+"%' ";
		}
		sql += " order by regtime desc";
		
		totalCount[0] = getCount(query1);
		Query sqlquery = session.createSQLQuery(sql)
				.addEntity(Enterprise.class)
				.setFirstResult(page * rows - rows)
				.setMaxResults(rows);

		List<Enterprise> list = sqlquery.list();

		logger.info("pageSelect(int, int, int[], String) - end");
		return list;
	}

	private int getCount(String query1) {
		logger.info("getCount(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select count(*) from enterprise a " +
			" where a.deleted = '1' ";
		
		if(StringUtils.isNotBlank(query1)){
			sql += " and a.name like '%"+query1.trim()+"%' ";
		}
		
		Query sqlquery = session.createSQLQuery(sql);
		int returnint = Integer.parseInt(sqlquery.uniqueResult().toString());

		logger.info("getCount(String) - end");
		return returnint;
	}

}
