package com.crm.workbench.service.Impl;

import java.util.List;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);

	@Override
	public List<String> getCustomerName(String name) {
		
		List<String> list = customerDao.getCustomerName(name);
		
		return list;
	}
	
	
}
