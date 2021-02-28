package com.crm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.crm.settings.domain.User;

public class LoginFilter implements Filter {

	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		//Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		//Filter.super.destroy();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("进入到验证有没有登陆过的过滤器");
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		String path = request.getServletPath();
		System.out.println(path);
		if("/login.jsp".equals(path) || "settings/user/login.do".equals(path)) {
			System.out.println("????????????");
			chain.doFilter(req, resp);
		}else {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			//如果user不为null，说明登陆过
			if(user != null) {
				chain.doFilter(req, resp);
			}else {
				//重定向到登录页
				/*
				 * 重定向的路径怎么写？
				 * 在实际项目开发中，对于路径的写法，不论操作的是前端还是后端，应该一律使用绝对路径
				 * 	关于转发和重定向的路径的写法如下：
				 * 	转发：
				 * 		使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加/项目名，这种路径也称之为内部路径
				 * 		/login.jsp
				 * 重定向：
				 * 		使用的是传统绝对路径的写法，前面必须以/项目名开头，后面跟具体的资源路径
				 * 		/crm/login.jsp
				 */
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				//为啥不用请求转发?
				/*
				 * 转发之后，路径会停留在老路径上，而不是跳转之后最新资源的路径
				 * 我们应该为用户跳转到登录也的同时，将浏览器的地址栏应该自动设置为当前的登录页的路径
				 */
			}
		}
	}

}
