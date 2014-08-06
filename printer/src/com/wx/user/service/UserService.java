package com.wx.user.service;

import java.util.List;

import com.wx.user.vo.User;
import com.wx.user.vo.UserPo;

public interface UserService {

	public User load(String id);

	public void add(User user);

	public void delete(User user);

	public List<User> selectUser(String name);

	public List<UserPo> pageSelect(int page, int rows, int[] totalCount,
			String query1, String enterid);
}
