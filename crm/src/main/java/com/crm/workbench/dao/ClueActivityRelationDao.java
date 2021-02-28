package com.crm.workbench.dao;

import java.util.List;

import com.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

	int bund(ClueActivityRelation car1);

	List<ClueActivityRelation> getListByClueId(String clueId);

	int delete(ClueActivityRelation clueActivityRelation);

	

}
