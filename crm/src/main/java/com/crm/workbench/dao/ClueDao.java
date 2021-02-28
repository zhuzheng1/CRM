package com.crm.workbench.dao;

import com.crm.workbench.domain.Clue;

public interface ClueDao {

	int save(Clue clue);

	Clue detail(String id);

	int unbund(String id);

	Clue getById(String clueId);

	int delete(String clueId);


	

}
