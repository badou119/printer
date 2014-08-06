package com.wx.enterprise.service;

import java.util.List;

import com.wx.enterprise.vo.Enterprise;

public interface EnterpriseService {

	public Enterprise load(String id); 
	public Enterprise getEnterprise(String name);
	public void add(Enterprise enterprise); 
	public void delete(Enterprise enterprise); 
	
	public List<Enterprise> selectEnterprise(String name); 
	
	public List<Enterprise> pageSelect(int page, int rows, int[] totalCount, String query1);
}
