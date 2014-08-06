package com.wx.exampeople.vo;

import java.io.Serializable;
import java.util.Date;

public class ExamPeople implements Serializable {

	private String id;
	private String name;
	private String code;
	private String sex;
	private int age;
	private String pic;
	private String jkzcode;
	private Date examtime;
	private String phone;
	private String address;
	private String enterid;
	private String profession;
	private Integer isprinted;
	private Integer tocenter;
	private String remark;
	private Date regtime;
	private Date updatetime;
	private Integer deleted;
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the examtime
	 */
	public Date getExamtime() {
		return examtime;
	}
	/**
	 * @param examtime the examtime to set
	 */
	public void setExamtime(Date examtime) {
		this.examtime = examtime;
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
	 * @return the entercode
	 */
	public String getEnterid() {
		return enterid;
	}
	/**
	 * @param entercode the entercode to set
	 */
	public void setEnterid(String enterid) {
		this.enterid = enterid;
	}
	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}
	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	/**
	 * @return the isprinted
	 */
	public Integer getIsprinted() {
		return isprinted;
	}
	/**
	 * @param isprinted the isprinted to set
	 */
	public void setIsprinted(Integer isprinted) {
		this.isprinted = isprinted;
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
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getJkzcode() {
		return jkzcode;
	}
	public void setJkzcode(String jkzcode) {
		this.jkzcode = jkzcode;
	}
	/**
	 * @return the tocenter
	 */
	public Integer getTocenter() {
		return tocenter;
	}
	/**
	 * @param tocenter the tocenter to set
	 */
	public void setTocenter(Integer tocenter) {
		this.tocenter = tocenter;
	}

	
}
