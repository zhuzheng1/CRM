package com.crm.settings.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.MD5Util;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;

//@Controller
public class UserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@RequestMapping(value = "/settings/user/login.do")
//	public String test1() {
//		System.out.println("进入到用户控制器");
//		//String path = 
//		
//		return "index";
//	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("进入到用户控制器");
		String path = request.getServletPath();
		if("/settings/user/login.do".equals(path)) {
			login(request, response);
		}else {
			try {
				throw new LoginException("404");
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入到验证登录操作阶段");
		
		String loginAct = request.getParameter("loginAct");
		String loginPwd = request.getParameter("loginPwd");
		
		//将密码的明文形式转换为MD5的密文形式
		loginPwd = MD5Util.getMD5(loginPwd);
		//接受浏览器端的IP地址
		String ip = request.getRemoteAddr();
		System.out.println("------------------ip:"+ip);
		
		UserService us = (UserService)ServiceFactory.getService(new UserServiceImpl());
		try {
			User user = us.login(loginAct, loginPwd, ip);
			request.getSession().setAttribute("user", user);
			//如果程序执行到此处，说明业务层没有为controller抛出任何的异常
			//表示登录成功
			PrintJson.printJsonFlag(response, true);
			
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			//此处执行表示登录失败，说明业务层为我们验证登录失败，为controller抛出了异常
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("msg", msg);
			
			PrintJson.printJsonObj(response, map);
			
		}
		
	}
	
	
}
