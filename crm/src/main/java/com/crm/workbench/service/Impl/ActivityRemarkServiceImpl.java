package com.crm.workbench.service.Impl;

import java.util.List;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.ActivityRemarkDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;
import com.crm.workbench.service.ActivityRemarkService;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {

	private ActivityRemarkDao ard = SqlSessionUtil.getSession().getMapper(ActivityRemarkDao.class);
	
	@Override
	public List<Activity> getRemarkListById(String activityId) {

		List<Activity> arList = ard.getRemarkListById(activityId);
			
		return arList;
	}

	@Override
	public boolean deleteRemark(String id) {

		boolean flag = false;
		
		int result = ard.deleteRemark(id);
		
		if(result == 1) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean saveRemarkBtn(ActivityRemark ar) {
		
		boolean flag = true;
		int result = ard.saveRemarkBtn(ar);
		
		if(result != 1) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public boolean updateRemark(ActivityRemark ar) {

		boolean flag = true;
		int result = ard.updateRemark(ar);
		if(result != 1) {
			flag = false;
		}
		
		return flag;
	}
	
}
