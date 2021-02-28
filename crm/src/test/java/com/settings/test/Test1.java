package com.settings.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.crm.utils.DateTimeUtil;
import com.crm.utils.MD5Util;

public class Test1 {

	public static void main(String[] args) {
		
		String a = "2021-01-23 14:40:20";
		
		String currentTime = DateTimeUtil.getSysTime();
		int count = a.compareTo(currentTime);
		System.out.println(count+":    "+currentTime);

		
		
		//验证失效时间
		//失效时间
//		String expireTimeString = "2020-9-19 15:40:10";
//		
//		String currentTime = DateTimeUtil.getSysTime();
//		
//		int count = expireTimeString.compareTo(currentTime);
//		System.out.println(currentTime);
//		
//		System.out.println(count);
		
		String a1 = "123";
		String pwd = MD5Util.getMD5(a1);
		
		System.out.println(pwd);
	}
}
