package com.crm.workbench.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;

import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.dao.ActivityRemarkDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.vo.PaginationVO;

public class ActivityServiceImpl implements ActivityService {

	private ActivityDao activityDao = SqlSessionUtil.getSession().getMapper(ActivityDao.class);
	private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSession().getMapper(ActivityRemarkDao.class);
	private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);

	@Override
	public boolean save(Activity activity) {
		boolean flag = false;
		
		int result = activityDao.save(activity);
		if(result == 1) {
			flag = true;
		}
		return flag;
	}

	@Override
	public PaginationVO<Activity> pageList(Map<String, Object> map) {
		PaginationVO<Activity> activityVo = new PaginationVO<Activity>();
		
		int total = activityDao.getTotal();
		List<Activity> aList = activityDao.pageList(map);
		activityVo.setDataList(aList);
		activityVo.setTotal(total);
		
		
		return activityVo;
	}

	@Override
	public boolean delete(String[] idArray) {
		
		boolean flag = true;
		
		//查询出需要删除的备注的数量
		int count1 = activityRemarkDao.getCountByAids(idArray);
		
		//删除备注，返回实际删除的数量
		int count2 = activityRemarkDao.deleteByAids(idArray);
		
		//删除市场活动
		if(count1 != count2) {
			flag = false;
		}

		int count3 = activityDao.delete(idArray);
		if(count3 != idArray.length) {
			flag = false;
		}
		return flag;
	}

	@Override
	public Map<String, Object> getUserListAndActivity(String id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<User> uList = userDao.getUserList(id);
		
		Activity a = activityDao.getActivity(id);
		
		map.put("uList", uList);
		map.put("a", a);
		
		return map;
	}

	@Override
	public boolean update(Activity activity) {
		boolean flag = false;
		
		int result = activityDao.update(activity);
		if(result != 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public Activity detail(String id) {
		
		Activity a = activityDao.detail(id);
		
		return a;
	}

	@Override
	public List<Activity> getActivityListByClueId(String clueId) {
		
		List<Activity> list = activityDao.getActivityListByClueId(clueId);
		
		return list;
	}

	@Override
	public List<Activity> getActivityListByNameAndNotByClueId(
			Map<String, String> map) {

		List<Activity> list = activityDao.getActivityListByNameAndNotByClueId(map);
			
		return list;
	}

	@Override
	public List<Activity> getActivityListByName(String aname) {
		
		List<Activity> aList = activityDao.getActivityListByName(aname);
		
		return aList;
	}
	
	
}
