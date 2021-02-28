package com.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.crm.workbench.domain.Activity;

public interface ActivityDao {

	int save(Activity activity);

	List<Activity> pageList(Map<String, Object> map);

	int getTotal();

	int delete(String[] idArray);

	Activity getActivity(String id);

	int update(Activity activity);

	Activity detail(String id);

	List<Activity> getActivityListByClueId(String clueId);

	List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

	List<Activity> getActivityListByName(String aname);

}
