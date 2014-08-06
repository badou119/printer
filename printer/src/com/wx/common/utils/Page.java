package com.wx.common.utils;

public class Page {

	private int allCount;// 总条数
	private int pageCount;// 总条数
	private int pageNum;// 当前页码
	private int pageSize = 10;// 每页条数

	/**
	 * @return the allcount
	 */
	public int getAllCount() {
		return allCount;
	}

	/**
	 * @param allcount
	 *            the allcount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
