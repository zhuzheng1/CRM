package com.crm.workbench.dao;

import java.util.List;

import com.crm.workbench.domain.ClueRemark;


public interface ClueRemarkDao {

	List<ClueRemark> getListByClueId(String clueId);

	int delete(ClueRemark clueRemark);

}
