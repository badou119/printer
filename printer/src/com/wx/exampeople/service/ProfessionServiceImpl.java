package com.wx.exampeople.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wx.exampeople.vo.Profession;

public class ProfessionServiceImpl extends HibernateDaoSupport implements ProfessionService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProfessionServiceImpl.class);

	public Profession get(String id, String name) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		if(StringUtils.isBlank(id)&&StringUtils.isBlank(name)){
			return null;
		}
		String sql = "select * from Profession where 1=1 ";
		
		if(StringUtils.isNotBlank(id)){
			sql += " and id ='"+id+"'";
		}
		
		if(StringUtils.isNotBlank(name)){
			sql += " and name ='"+name.trim()+"'";
		}
				
		Query sqlquery = session.createSQLQuery(sql).addEntity(Profession.class);
		Profession profession = (Profession) sqlquery.uniqueResult();
		
		return profession;
	}

	public List<Profession> select() {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select * from Profession ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(Profession.class);
		List<Profession> pList = sqlquery.list();
		return pList;
	}





}
