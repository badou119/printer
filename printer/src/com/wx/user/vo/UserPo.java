package com.wx.user.vo;

import java.io.Serializable;
import java.util.Date;

public class UserPo implements Serializable{

	private String id;//
	private String account;//	帐号
	private String password;//	密码
	private String enterid;//	所属单位
	private String entername;//	所属单位
	private String name;//	姓名
	private String phone;//	电话
	private String remark;//	备注
	private Date regtime	;//注册时间
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the enterid
	 */
	public String getEnterid() {
		return enterid;
	}
	/**
	 * @param enterid the enterid to set
	 */
	public void setEnterid(String enterid) {
		this.enterid = enterid;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	/**
	 * @return the entername
	 */
	public String getEntername() {
		return entername;
	}
	/**
	 * @param entername the entername to set
	 */
	public void setEntername(String entername) {
		this.entername = entername;
	}
	

}
