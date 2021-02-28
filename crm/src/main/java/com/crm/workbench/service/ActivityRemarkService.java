package com.crm.workbench.service;

import java.util.List;

import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkService {

	List<Activity> getRemarkListById(String activityId);

	boolean deleteRemark(String id);

	boolean saveRemarkBtn(ActivityRemark ar);

	boolean updateRemark(ActivityRemark ar);
	
}
