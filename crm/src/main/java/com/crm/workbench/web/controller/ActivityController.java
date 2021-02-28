package com.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.expr.NewArray;

import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.service.ActivityRemarkService;
import com.crm.workbench.service.Impl.ActivityRemarkServiceImpl;
import com.crm.workbench.service.Impl.ActivityServiceImpl;
import com.crm.workbench.vo.PaginationVO;

public class ActivityController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("进入到市场活动控制器");
		String path = req.getServletPath();
		if("/workbench/activity/getUserList.do".equals(path)) {
			getUserList(req, resp);
		}else if("/workbench/activity/save.do".equals(path)) {
			save(req, resp);
		}else if("/workbench/activity/pageList.do".equals(path)) {
			pageList(req, resp);
		}else if("/workbench/activity/delete.do".equals(path)) {
			delete(req, resp);
		}else if("/workbench/activity/getUserListAndActivity.do".equals(path)) {
			getUserListAndActivity(req, resp);
		}else if("/workbench/activity/update.do".equals(path)) {
			update(req, resp);
		}else if("/workbench/activity/detail.do".equals(path)) {
			detail(req, resp);
		}else if("/workbench/activity/getRemarkListById.do".equals(path)) {
			getRemarkListById(req, resp);
		}else if("/workbench/activity/deleteRemark.do".equals(path)) {
			deleteRemark(req, resp);
		}else if("/workbench/activity/saveRemarkBtn.do".equals(path)) {
			saveRemarkBtn(req, resp);
		}else if("/workbench/activity/updateRemark.do".equals(path)) {
			updateRemark(req, resp);
		}else {
			
		}
		
	}
	
	private void updateRemark(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("执行修改备注的操作");
		
		String id = req.getParameter("id");
		String noteContent = req.getParameter("noteContent");
		String editTime = DateTimeUtil.getSysTime();
		String editBy = ((User)req.getSession().getAttribute("user")).getName();
		String editFlag = "1";
		
		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setEditTime(editTime);
		ar.setEditBy(editBy);
		ar.setEditFlag(editFlag);
		
		ActivityRemarkService ars = (ActivityRemarkService)ServiceFactory.getService(new ActivityRemarkServiceImpl());
		
		boolean flag = ars.updateRemark(ar);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("flag", flag);
		map.put("ar", ar);
		
		PrintJson.printJsonObj(resp, map);
		
	}

	private void saveRemarkBtn(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("执行添加操作");
		
		String noteContent = request.getParameter("noteContent");
		String activityId = request.getParameter("activityId");
		String id = UUIDUtil.getUUID();
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String createTime = DateTimeUtil.getSysTime();
		String editFlag = "0";
		
		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setActivityId(activityId);
		ar.setCreateBy(createBy);
		ar.setCreateTime(createTime);
		ar.setEditFlag(editFlag);
		
		ActivityRemarkService ars = (ActivityRemarkService)ServiceFactory.getService(new ActivityRemarkServiceImpl());
		
		boolean flag = ars.saveRemarkBtn(ar);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("flag", flag);
		map.put("ar", ar);
		
		PrintJson.printJsonObj(response, map);
		
	}

	private void deleteRemark(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("删除备注操作");
		
		String id = req.getParameter("id");
		ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
		
		boolean flag = ars.deleteRemark(id);
		PrintJson.printJsonFlag(resp, flag);
		
	}

	private void getRemarkListById(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("根据市场活动id，取得备注信息列表");
		
		String activityId = req.getParameter("activityId");
		
		ActivityRemarkService ars = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
		
		List<Activity> arList = ars.getRemarkListById(activityId);
		
		PrintJson.printJsonObj(resp, arList);
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入详细页面");
		
		String id = request.getParameter("id");
		ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		Activity a = as.detail(id);
		
		request.setAttribute("a", a);
		request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
		
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("执行修改市场活动更新操作");
		
		ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String createTime = DateTimeUtil.getSysTime();
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String editTime = null;
		String editBy = null;
		
		Activity activity = new Activity(id,owner,name,startDate,endDate,cost,description,createTime,createBy,editTime,editBy);
		
		boolean flag = activityService.update(activity);
		PrintJson.printJsonFlag(response, flag);
		
	}

	private void getUserListAndActivity(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("进入执行市场活动修改操作");
		
		String id = request.getParameter("id");
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		Map<String, Object> map = as.getUserListAndActivity(id);
		PrintJson.printJsonObj(response, map);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入到删除市场活动信息列表的操作");
		
		String[] idArray = request.getParameterValues("id");
		
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		boolean flag = as.delete(idArray);
		
		PrintJson.printJsonFlag(response, flag);
	}

	private void pageList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
		
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pageNoStr = request.getParameter("pageNo");
		String pageSizeStr = request.getParameter("pageSize");
		
		int pageNo = Integer.valueOf(pageNoStr);
		int pageSize = Integer.valueOf(pageSizeStr);
		//计算略过的记录数
		int skipCount = (pageNo-1)*pageSize;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("skipCount", skipCount);
		map.put("pageSize", pageSize);
		
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		PaginationVO<Activity> activityVo = as.pageList(map);
		
		PrintJson.printJsonObj(response, activityVo);

	}



	public void getUserList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("取得用户信息列表");
		
		UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
		
		List<User> userList = userService.getUserList();
		PrintJson.printJsonObj(response, userList);
	}
	
	public void save(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("保存创建用户市场信息");
		
		ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
		
		String id = UUIDUtil.getUUID();
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String createTime = DateTimeUtil.getSysTime();
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String editTime = null;
		String editBy = null;
		
		Activity activity = new Activity(id,owner,name,startDate,endDate,cost,description,createTime,createBy,editTime,editBy);
		
		boolean flag = activityService.save(activity);
		PrintJson.printJsonFlag(response, flag);
		
	}

	
}
