package com.crm.settings.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.settings.dao.DicTypeDao;
import com.crm.settings.dao.DicValueDao;
import com.crm.settings.domain.DicType;
import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.utils.SqlSessionUtil;

public class DicServiceImpl implements DicService {

	private DicTypeDao dicTypeDao = SqlSessionUtil.getSession().getMapper(DicTypeDao.class);
	private DicValueDao dicValueDao = SqlSessionUtil.getSession().getMapper(DicValueDao.class);
	
	
	@Override
	public Map<String, List<DicValue>> getAll() {
		
		Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();
		
		//将字典类型列表取出
		List<DicType> typeList = dicTypeDao.getTypeList();
		
		//将列表进行遍历
		for (DicType dt : typeList) {
			
			//取得每一种类型的字典类型编码
			String code = dt.getCode();
			
			//根据code查字典值获得列表
			List<DicValue> valueList = dicValueDao.getListByCode(code);
			
			map.put(code, valueList);
		}
		
		return map;
	}
	
}
