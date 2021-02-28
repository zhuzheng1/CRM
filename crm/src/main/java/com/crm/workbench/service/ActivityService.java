package com.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.crm.workbench.domain.Activity;
import com.crm.workbench.vo.PaginationVO;

public interface ActivityService {

	boolean save(Activity activity);

	PaginationVO<Activity> pageList(Map<String, Object> map);

	boolean delete(String[] idArray);

	Map<String, Object> getUserListAndActivity(String id);

	boolean update(Activity activity);

	Activity detail(String id);

	List<Activity> getActivityListByClueId(String clueId);

	List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

	List<Activity> getActivityListByName(String aname);

}
