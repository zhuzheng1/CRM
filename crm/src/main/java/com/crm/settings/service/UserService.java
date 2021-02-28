package com.crm.settings.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.crm.settings.domain.User;

public interface UserService {

	public User login(String loginAct, String loginPwd, String ip) throws LoginException;

	public List<User> getUserList();

	
}
