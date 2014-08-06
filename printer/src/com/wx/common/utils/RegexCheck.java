package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wx.enterprise.service.EnterpriseService;
import com.wx.enterprise.vo.Enterprise;
import com.wx.exampeople.service.ProfessionService;
import com.wx.exampeople.vo.Profession;

public class RegexCheck {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegexCheck.class);

	/**
	 * 对导入的通讯录中的字段进行正则表达式校验，如果校验通过返回true，否则返回false.
	 * 校验正确的信息直接插入数据库
	 * 校验失败的信息导出到失败文件中
	 */
	public static boolean checkString(String[] employee, int minCount,String enterid,
			OutputStreamWriter filewriteError,EnterpriseService enterpriseService,
			ProfessionService professionService) {
		if (logger.isDebugEnabled()) {
			logger.debug("checkString(String[], int, OutputStreamWriter, EnterpriseService) - start"); //$NON-NLS-1$
		}

		boolean returns = true;
		Pattern p = null;
		Matcher m = null;

		for (int index = 0; index < minCount; index++) {
			returns = true;
			String checkS = employee[index].replace("\"", "").trim();

			switch (index) {
			case 0:// 姓名
				p = Pattern.compile("^[\\s]{0,}$");
				m = p.matcher(checkS);
				if (m.matches()) {
					returns = false;
				} else {
					p = Pattern
							.compile("^[A-Za-z0-9-_()（）.\\u4e00-\\u9fa5]{1,50}$");
					m = p.matcher(checkS);
					if (!m.matches()) {
						returns = false;
					}
				}
				break;
			case 1:// 身份证
				p = Pattern.compile("(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}(|X|x)$)");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}
				break;
			case 2:// 性别
				p = Pattern
						.compile("^['男'|'女']$");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}
				break;
			case 3:// 年龄 1-119
				p = Pattern 
						.compile("^([1-9]|[0-9]{2}|[1][0|1][0-9]{1})$");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}
				break;
			case 4:// 体检时间  2014/07/28
				p = Pattern.compile("^^([0-9]{4})/((([1-9]{1}))|(0([1-9]{1}))|(1[0-2]))/(([0-2]([0-9]{1}))|(3[0|1]))$");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}
				break;
			case 5:// 体检单位
				p = Pattern.compile("^[A-Za-z0-9-\\u4e00-\\u9fa5]{0,100}$");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}else{
					Enterprise ep = enterpriseService.getEnterprise(checkS);
					if(ep==null || !enterid.equals(ep.getId())){
						returns = false;
					}
				}
				break;
			case 6:// 工种
				p = Pattern.compile("^[A-Za-z0-9-\\u4e00-\\u9fa5]{0,100}$");
				m = p.matcher(checkS);
				if (!m.matches()) {
					returns = false;
				}else{
					Profession profession = professionService.get("", checkS);
					if(profession==null){
						returns = false;
					}
				}
				break;
			default:
				break;
			}
			// 如果有任一字段格式不符合。则将该条联系人信息写入错误记录文件中。
			if (returns == false) {
				String rowString = "";
				for (int indexR = 0; indexR < minCount; indexR++) {
					if (indexR == index) {
						rowString += "\"" + employee[indexR].replace("\"", "")
								+ "(格式错误)\"" + ",";
					} else {
						rowString += employee[indexR] + ",";
					}
				}

				try {
					filewriteError.write(rowString + "\r\n");
				} catch (IOException e) {
					logger.error("checkString(String[], int, OutputStreamWriter, EnterpriseService)", e); //$NON-NLS-1$

					e.printStackTrace();
				}
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("checkString(String[], int, OutputStreamWriter, EnterpriseService) - end"); //$NON-NLS-1$
		}
		return returns;
	}
	
	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		Pattern p = null;
		Matcher m = null;
		//(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}(|X|x)$)
		//^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$
		p = Pattern.compile("^([0-9]{4})/((0([1-9]{1}))|(1[0-2]))/(([0-2]([0-9]{1}))|(3[0|1]))$");
		m = p.matcher("2014/07/32");
		if (!m.matches()) {
			System.out.println("false");
		}else
			System.out.println("true");
		
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}	
}
