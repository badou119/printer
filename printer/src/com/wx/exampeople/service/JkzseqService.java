package com.wx.exampeople.service;

import com.wx.exampeople.vo.Jkzseq;

public interface JkzseqService {
	
	
	public Jkzseq get(String enterid,String ymd); 
	public void addOrUpdate(Jkzseq jkzseq); 
	public void delete(String enterid,String ymd); 
	
}
