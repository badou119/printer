package com.wx.exampeople.service;

import java.util.List;

import com.wx.exampeople.vo.ExamPeople;
import com.wx.exampeople.vo.ExamPeoplePo;

public interface ExamPeopleService {
	
	
	public ExamPeople load(String id); 
	public void add(ExamPeople examPeople); 
	public void delete(ExamPeople examPeople); 
	
	public List<ExamPeople> selectExamPeople(String name);
	public List<ExamPeoplePo> pageSelect(int page, int rows, int[] totalCount, String peoplename, String enterid);
	public void saveList(List<ExamPeople> eList); 
}
