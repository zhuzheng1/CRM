package com.crm.settings.dao;

import java.util.List;

import com.crm.settings.domain.DicValue;

public interface DicValueDao {

	List<DicValue> getListByCode(String code);

}
