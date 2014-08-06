package com.wx.exampeople.service;

import java.util.List;

import com.wx.exampeople.vo.Profession;

public interface ProfessionService {
	
	
	public Profession get(String id,String name); 
	public List<Profession> select(); 
	
	
}
