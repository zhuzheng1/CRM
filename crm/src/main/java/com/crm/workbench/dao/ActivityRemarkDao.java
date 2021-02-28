package com.crm.workbench.dao;

import java.util.List;

import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkDao {

	int getCountByAids(String[] idArray);

	int deleteByAids(String[] idArray);
	
	List<Activity> getRemarkListById(String activityId);

	int deleteRemark(String id);

	int saveRemarkBtn(ActivityRemark ar);

	int updateRemark(ActivityRemark ar);

}
