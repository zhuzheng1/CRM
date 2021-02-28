package com.crm.workbench.dao;

import java.util.List;

import com.crm.workbench.domain.Customer;

public interface CustomerDao {

	Customer getCustomerByName(String company);

	int save(Customer c);

	List<String> getCustomerName(String name);

}
