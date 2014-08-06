package com.wx.common.utils;

import org.apache.log4j.Logger;

/**
 * 导出通讯录使用
 * @author 贾朝阳
 *
 */
public class ExcelData {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcelData.class);

	
	String[] columnData={"姓名","工号","部门名称","职务","移动电话1","移动电话2","移动电话3","工作电话1","工作电话2","工作电话3","家庭电话1","家庭电话2","短号码","传真","邮箱","办公地点","QQ","员工级别","员工排序"};
	//获取通讯录的字段长度
	public int getColumnCount(){
		return this.columnData.length;
	}
	//获取通讯录对应字段的名称。
	public String getColumnLabel(int index){
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnLabel(int) - start"); //$NON-NLS-1$
		}

		String returnString = this.columnData[index];
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnLabel(int) - end"); //$NON-NLS-1$
		}
		return returnString;
	}
}
