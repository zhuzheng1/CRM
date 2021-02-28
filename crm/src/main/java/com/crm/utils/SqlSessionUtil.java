package com.crm.utils;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class SqlSessionUtil {

	private static SqlSessionFactory factory;
	
	static {
		String resources = "mybatis-config.xml";
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(resources);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory = new SqlSessionFactoryBuilder().build(is);
	}
	
	private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();
	
	public static SqlSession getSession() {
		
		SqlSession session = t.get();
		if(session == null) {
			session = factory.openSession();
			t.set(session);
		}
		
		return session;
	}
	
	public static void myClose(SqlSession session) {
		
		if(session != null) {
			session.close();
			t.remove();
		}
	}
}
