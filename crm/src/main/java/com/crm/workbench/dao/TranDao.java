package com.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.crm.workbench.domain.Tran;

public interface TranDao {

	int save(Tran t);

	Tran detail(String id);

	int changeStage(Tran t);

	int getTotal();

	List<Map<String, Object>> getCharts();

}
