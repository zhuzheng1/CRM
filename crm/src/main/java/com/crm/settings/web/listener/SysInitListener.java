package com.crm.settings.web.listener;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.javassist.expr.NewArray;

import com.alibaba.druid.support.logging.Resources;
import com.crm.settings.dao.DicValueDao;
import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.settings.service.impl.DicServiceImpl;
import com.crm.utils.ServiceFactory;

public class SysInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

		/*	
		 	该方法用来监听上下文域对象的方法，当服务器启动，上下文域对象创建
			对象创建完毕之后，马上执行该方法
			
			event： 该参数能够取得监听的对象
					监听的是什么对象，就可以通过该参数取得什么对象
					例如我们现在监听的是上下文域对象，通过该参数就可以获得上下文域对象
		*/
		System.out.println("服务器缓存处理数据字典开始");
		
		ServletContext application = event.getServletContext();
		
		//取得数据字典
		DicService ds = (DicService)ServiceFactory.getService(new DicServiceImpl());
		
		Map<String, List<DicValue>> map = ds.getAll();
		
		//将map解析为上下文域对象中保存的键值对
		Set<Map.Entry<String, List<DicValue>>> entrys = map.entrySet();
		for (Entry<String, List<DicValue>> entry : entrys) {
			application.setAttribute(entry.getKey(), entry.getValue());
		}
		System.out.println("服务器缓存处理数据字典结束");
		
		System.out.println("开始处理Stage2Possibility.properties文件");
		//解析该文件，将该属性文件中的键值对关系处理成为Java中键值对关系（map）
		//方法1：使用Properties类（此类擅长处理不含中文的键值对文件）
		//方法2
		Map<String, String> pMap = new HashMap<String, String>(); 
		ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
		Enumeration<String> e = rb.getKeys();
		while(e.hasMoreElements()) {
			//阶段
			String key = e.nextElement();
			//可能性
			String value = rb.getString(key);
			pMap.put(key, value);
		}
		application.setAttribute("pMap", pMap);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	
}
