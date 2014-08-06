package com.wx.login.service;

import com.wx.user.vo.User;

public interface LoginService {

	public int login(User user);

	public String getMenu(User user);

	public void modifySuperPWD(String id, String pwd);

}
