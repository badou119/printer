package com.wx.exampeople.vo;

import java.io.Serializable;

public class Jkzseq implements Serializable {


	private String id;
	private String enterid;
	private String jkzseq;
	private String ymd;
	
	public String getEnterid() {
		return enterid;
	}
	public void setEnterid(String enterid) {
		this.enterid = enterid;
	}
	public String getJkzseq() {
		return jkzseq;
	}
	public void setJkzseq(String jkzseq) {
		this.jkzseq = jkzseq;
	}
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
