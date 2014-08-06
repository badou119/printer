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

public class ExamPeopleServiceImpl extends HibernateDaoSupport implements ExamPeopleService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExamPeopleServiceImpl.class);

	public void add(ExamPeople examPeople) {
		logger.info("add(ExamPeople) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.saveOrUpdate(examPeople);

		logger.info("add(ExamPeople) - end");
	}
	
	public void delete(ExamPeople examPeople) {
		logger.info("delete(ExamPeople) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.delete(examPeople);

		logger.info("delete(ExamPeople) - end");
	}

	public ExamPeople load(String id) {
		logger.info("load(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from ExamPeople a where a.deleted = '1' and a.id = '"+id+"' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(ExamPeople.class);

		ExamPeople examPeople =  (ExamPeople) sqlquery.uniqueResult();

		logger.info("load(String) - end");
		return examPeople;
	}

	public List<ExamPeople> selectExamPeople(String name) {
		logger.info("selectExamPeople(String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.* from ExamPeople a where a.deleted = '1' and a.name like '%"+name+"%' ";
		Query sqlquery = session.createSQLQuery(sql).addEntity(ExamPeople.class);
		List<ExamPeople> list =  sqlquery.list() ;

		logger.info("selectExamPeople(String) - end");
		return list;
	}

	public List<ExamPeoplePo> pageSelect(int page, int rows, int[] totalCount, String peoplename, String enterid) {
		logger.info("pageSelect(int, int, int[], String, String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select a.*,b.name entername,b.logo enterlogo,c.name proname from exampeople a " +
				" left join enterprise b on a.enterid = b.id " +
				" left join profession c on a.profession = c.id " +
				" where a.deleted = '1' and b.deleted = '1' ";
				
		if(StringUtils.isNotBlank(peoplename)){
			sql += " and a.name like '%"+peoplename.trim()+"%' ";
		}

		if(StringUtils.isNotBlank(enterid)){
			sql += " and a.enterid = '"+enterid+"' ";
		}
		sql += " order by regtime desc,enterid asc ";
		
		totalCount[0] = getCount(peoplename, enterid);
		Query sqlquery = session.createSQLQuery(sql)
				.addEntity(ExamPeoplePo.class).setFirstResult(page * rows - rows)
				.setMaxResults(rows);

		List<ExamPeoplePo> list = sqlquery.list();

		logger.info("pageSelect(int, int, int[], String, String) - end");
		return list;
	}

	private int getCount(String peoplename, String enterid) {
		logger.info("getCount(String, String) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select count(*) from exampeople a " +
				" left join enterprise b on a.enterid = b.id " +
				" where a.deleted = '1' and b.deleted = '1' ";
		
		if(StringUtils.isNotBlank(peoplename)){
			sql += " and a.name like '%"+peoplename.trim()+"%' ";
		}

		if(StringUtils.isNotBlank(enterid)){
			sql += " and a.enterid = '"+enterid+"' ";
		}
		Query sqlquery = session.createSQLQuery(sql);
		int returnint = Integer.parseInt(sqlquery.uniqueResult().toString());

		logger.info("getCount(String, String) - end");
		return returnint;
	}

	public void saveList(List<ExamPeople> list) {
		logger.info("saveList(List<ExamPeople>) - start");

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		 if (list != null && list.size() > 0) {  
//	            try {  
//	                session.beginTransaction(); // 开启事物  
	                ExamPeople mode = null; 
	                // 循环获取对象  
	                for (int i = 0; i < list.size(); i++) {  
	                	mode = (ExamPeople) list.get(i); 
	                    session.save(mode); // 保存对象  
//	                    // 批插入的对象立即写入数据库并释放内存  
//	                    if (i % 10 == 0 || i == list.size() - 1) {
//	                        session.flush();  
//	                        session.clear();  
//	                    }  
	                }  
//	                session.getTransaction().commit(); // 提交事物  
//	            } catch (Exception e) {  
//	                e.printStackTrace(); // 打印错误信息  
//	                session.getTransaction().rollback(); // 出错将回滚事物  
//	            }finally{
//	            	session.close();
//	            }
	        }  

		logger.info("saveList(List<ExamPeople>) - end");
	}


}
