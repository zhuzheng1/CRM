package com.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;

public interface TranService {

	boolean save(Tran t, String customerName);

	Tran detail(String id);

	List<TranHistory> getHistoryListByTranId(String tranId);

	boolean changeStage(Tran t);

	Map<String, Object> getCharts();

}
