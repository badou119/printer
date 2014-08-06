package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.xwork2.ActionContext;

public class InputDate extends HibernateDaoSupport{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InputDate.class);

	public void inputdate(List<String[]> rowlist) throws HibernateException, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("inputdate(List<String[]>) - start"); //$NON-NLS-1$
		}

		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String groupId = ActionContext.getContext().getSession().get("groupId")
				.toString();
		PreparedStatement prep = null;

		int rowindex = 0;
		if (rowlist.size() > 0) {
			for (String[] sb = rowlist.get(0); rowindex < rowlist.size(); rowindex++) {
				sb = rowlist.get(rowindex);
				prep = session.connection()
						.prepareStatement("insert into employeeinput(uuid,employeename,quanpin,jianpin,phonenum1,phonenum2,phonenum3,worknum1,worknum2,worknum3,familynum1,familynum2,shortphone,fax,email,groupid,deptid,duty,employsort,userqq,reserver1,reserver2,reserver3,officeplace,employpic,empno,visiblelvl,emplvl,edittime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

				int index = 1;

				prep.setString(index++, UUID.randomUUID().toString().replace(
						"-", ""));
				prep.setString(index++, sb[0]);

				prep.setString(index++, ConverChineseCharToEn
						.converterToPingYingHeadUppercase(sb[0]).replace("-",
								""));
				prep.setString(index++, ConverChineseCharToEn
						.converterToAllFirstSpellsUppercase(sb[0]).replace("-",
								""));
				prep.setString(index++, sb[4]);

				prep.setString(index++, sb[5]);
				prep.setString(index++, sb[6]);
				prep.setString(index++, sb[7]);
				prep.setString(index++, sb[8]);
				prep.setString(index++, sb[9]);

				prep.setString(index++, sb[10]);
				prep.setString(index++, sb[11]);
				prep.setString(index++, sb[12]);
				prep.setString(index++, sb[13]);
				prep.setString(index++, sb[14]);
				prep.setString(index++, groupId);
				prep.setString(index++, sb[2]);
				prep.setString(index++, sb[3]);
				String sort = "";
				try {
					Integer.parseInt(sb[18]);
					sort = sb[18];
				} catch (Exception ex) {
					logger.error("inputdate(List<String[]>)", ex); //$NON-NLS-1$

					sort = "999";
				}
				prep.setString(index++, sort);
				prep.setString(index++, sb[16]);
				prep.setString(index++, "");
				prep.setString(index++, "");
				prep.setString(index++, "");
				prep.setString(index++, sb[15]);
				prep.setString(index++, "");
				prep.setString(index++, sb[1]);
				prep.setString(index++, "");
				int level = 999;
				try {
					level = Integer.parseInt(sb[17]);
				} catch (Exception ex) {
					logger.error("inputdate(List<String[]>)", ex); //$NON-NLS-1$

					level = 999;
				}
				prep.setString(index++,String.valueOf(level));

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String a1 = sdf.format(new Date(System.currentTimeMillis()));
				prep.setTimestamp(index++, Timestamp.valueOf(a1));

				prep.executeUpdate();
			}
			 session.connection().commit();
			 session.connection().setAutoCommit(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("inputdate(List<String[]>) - end"); //$NON-NLS-1$
		}
	}
}
