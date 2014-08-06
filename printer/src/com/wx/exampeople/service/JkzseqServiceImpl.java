package com.wx.exampeople.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wx.enterprise.vo.Enterprise;
import com.wx.exampeople.vo.ExamPeople;
import com.wx.exampeople.vo.ExamPeoplePo;
import com.wx.exampeople.vo.Jkzseq;

public class JkzseqServiceImpl extends HibernateDaoSupport implements JkzseqService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JkzseqServiceImpl.class);

	public Jkzseq get(String enterid,String ymd) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select * from jkzseq where enterid ='"+enterid+"' and ymd='"+ymd+"'";
		Query sqlquery = session.createSQLQuery(sql).addEntity(Jkzseq.class);
		Jkzseq jkzseq = (Jkzseq) sqlquery.uniqueResult();
		return jkzseq;
	}
	
	public void addOrUpdate(Jkzseq jkzseq) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.saveOrUpdate(jkzseq);
		
	}
	
	public void delete(String enterid,String ymd) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "delete from jkzseq where enterid ='"+enterid+"' and ymd='"+ymd+"'";
		Query sqlquery = session.createSQLQuery(sql);
		sqlquery.executeUpdate();
	}




}
