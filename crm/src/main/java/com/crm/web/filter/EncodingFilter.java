package com.crm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

/*	@Override
	public void destroy() {
		try {
			System.out.println("destroy");
			//Filter.super.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			System.out.println("init");
			//Filter.super.init(filterConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		System.out.println("进入到过滤字符编码过滤器");
		
		//过滤post请求中文参数乱码
		request.setCharacterEncoding("UTF-8");
		//过滤响应流响应中文乱码
		response.setContentType("text/html;charset=utf-8");
		
		//将请求放行
		filterChain.doFilter(request, response);
		
		
	}

}
