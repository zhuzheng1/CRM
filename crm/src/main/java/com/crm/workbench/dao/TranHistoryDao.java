package com.crm.workbench.dao;

import java.util.List;

import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;

public interface TranHistoryDao {

	int save(TranHistory th);

	List<TranHistory> getHistoryListByTranId(String tranId);

}
