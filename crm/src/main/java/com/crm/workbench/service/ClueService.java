package com.crm.workbench.service;

import javax.servlet.http.HttpServletRequest;

import com.crm.workbench.domain.Clue;
import com.crm.workbench.domain.Tran;

public interface ClueService {

	boolean save(Clue clue);

	Clue detail(String id);

	boolean unbund(String id);

	boolean bund(String cid, String[] aids);

	boolean convert(String clueId, Tran t, String createBy);

}
