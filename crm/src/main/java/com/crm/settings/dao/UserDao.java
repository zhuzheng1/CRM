package com.crm.settings.dao;

import java.util.List;
import java.util.Map;

import com.crm.settings.domain.User;

public interface UserDao {

	public User login1(Map<String, Object> map);

	public List<User> getUserList();

	public List<User> getUserList(String id);

	
}
