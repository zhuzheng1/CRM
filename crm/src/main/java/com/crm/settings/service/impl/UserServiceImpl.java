package com.crm.settings.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);

	@Override
	public User login(String loginAct, String loginPwd, String ip) throws LoginException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginAct", loginAct);
		map.put("loginPwd", loginPwd);
		User user = userDao.login1(map);
		if(user == null) {
			throw new LoginException("账号密码错误或用户不存在");
		}
		//验证失效时间
		String expireTime = user.getExpireTime();
		String currentTime = DateTimeUtil.getSysTime();
		if(expireTime.compareTo(currentTime) < 0) {
			throw new LoginException("账号已经失效");
		}
		
		String lockState = user.getLockState();
		//判断锁定状态
		if("0".equals(lockState)) {
			throw new LoginException("账号已经锁定");
		}
		
		//判断ip地址
		String allowIp = user.getAllowIps();
		if(!allowIp.contains(ip)) {
			throw new LoginException("ip地址受限");
		}
		
		
		return user;
	}

	@Override
	public List<User> getUserList() {
		
		List<User> uList = userDao.getUserList();
		
		return uList;
	}
}
