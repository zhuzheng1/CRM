package com.crm.settings.service;

import java.util.List;
import java.util.Map;

import com.crm.settings.domain.DicValue;

public interface DicService {

	Map<String, List<DicValue>> getAll();

}
