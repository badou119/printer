package com.wx.enterprise.vo;

import java.io.Serializable;
import java.util.Date;

public class Enterprise implements Serializable {
	
	private String id	;//
	private String entercode	;//单位代号
	private String name;//	单位名称
	private String address	;//单位地址
	private String linkperpon;//	联系人
	private String linkphone;//	联系电话
	private String logo;//印章
	private String parentid	;//上级单位ID
	private String remark;//	备注
	private Date regtime;//	注册时间
	private Date updatetime;//	更新时间
	private Integer deleted	;//有效0/1
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the entercode
	 */
	public String getEntercode() {
		return entercode;
	}
	/**
	 * @param entercode the entercode to set
	 */
	public void setEntercode(String entercode) {
		this.entercode = entercode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the linkperpon
	 */
	public String getLinkperpon() {
		return linkperpon;
	}
	/**
	 * @param linkperpon the linkperpon to set
	 */
	public void setLinkperpon(String linkperpon) {
		this.linkperpon = linkperpon;
	}
	/**
	 * @return the linkphone
	 */
	public String getLinkphone() {
		return linkphone;
	}
	/**
	 * @param linkphone the linkphone to set
	 */
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the parentid
	 */
	public String getParentid() {
		return parentid;
	}
	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the regtime
	 */
	public Date getRegtime() {
		return regtime;
	}
	/**
	 * @param regtime the regtime to set
	 */
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	/**
	 * @return the deleted
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
