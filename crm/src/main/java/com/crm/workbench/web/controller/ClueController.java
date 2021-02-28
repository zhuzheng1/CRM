package com.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.service.ClueService;
import com.crm.workbench.service.Impl.ActivityServiceImpl;
import com.crm.workbench.service.Impl.ClueServiceImpl;

public class ClueController extends HttpServlet {

private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("进入到线索模块控制器");
		
		String path = req.getServletPath();
		
		if("/workbench/clue/getUserList.do".equals(path)) {
			getUserList(req, resp);
		}else if("/workbench/clue/save.do".equals(path)) {
			save(req, resp);
		}else if("/workbench/clue/detail.do".equals(path)) {
			detail(req, resp);
		}else if("/workbench/clue/getActivityListByClueId.do".equals(path)) {
			getActivityListByClueId(req, resp);
		}else if("/workbench/clue/unbund.do".equals(path)) {
			unbund(req, resp);
		}else if("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {
			getActivityListByNameAndNotByClueId(req, resp);
		}else if("/workbench/clue/bund.do".equals(path)) {
			bund(req, resp);
		}else if("/workbench/clue/getActivityListByName.do".equals(path)) {
			getActivityListByName(req, resp);
		}else if("/workbench/clue/convert.do".equals(path)) {
			convert(req, resp);
		}//else if("/workbench/clue/saveRemarkBtn.do".equals(path)) {
//			saveRemarkBtn(req, resp);
//		}else if("/workbench/clue/updateRemark.do".equals(path)) {
//			updateRemark(req, resp);
//		}else {
//			
//		}
		
	}

	private void convert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("执行线索转换操作");
		
		String clueId = req.getParameter("clueId");
		//判断是否需要创建交易
		String flag = req.getParameter("flag");
		String createBy = ((User)req.getSession().getAttribute("user")).getName();
		
		Tran t = null;
		
		//如果需要创建交易
		if("a".equals(flag)) {
			
			t = new Tran();
			
			//接受交易表单中的参数
			String money = req.getParameter("money");
			String name = req.getParameter("name");
			String stage = req.getParameter("stage");
			String expectedDate = req.getParameter("expectedDate");
			String activityId = req.getParameter("activityId");
			String id = UUIDUtil.getUUID();
			//String createTime = DateTimeUtil.getSysTime();
			
			t.setId(id);
			t.setMoney(money);
			t.setName(name);
			t.setExpectedDate(expectedDate);
			t.setActivityId(activityId);
			//t.setCreateBy(createBy);
			//t.setCreateTime(createTime);
			t.setStage(stage);
		}
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag1 = cs.convert(clueId, t, createBy);
		
		if(flag1) {
			resp.sendRedirect(req.getContextPath()+"/workbench/clue/index.jsp");
		}
		
		PrintJson.printJsonFlag(resp, flag1);
		
	}

	private void getActivityListByName(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("查询市场活动列表，根据名称模糊查询");
		
		String aname = req.getParameter("aname");
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		List<Activity> aList = as.getActivityListByName(aname);
		
		PrintJson.printJsonObj(resp, aList);
		
	}

	private void bund(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("执行关联市场活动的操作");
		
		String cid = req.getParameter("cid");
		String[] aids = req.getParameterValues("aid");
		
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag = cs.bund(cid,aids);
		
		PrintJson.printJsonFlag(resp, flag);
		
	}

	private void getActivityListByNameAndNotByClueId(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("查询市场活动列表（根据名称模糊查询+排除到已经关联指定线索的列表）");
		
		String aname = req.getParameter("aname");
		String clueId = req.getParameter("clueId");
		
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("aname", aname);
		map.put("clueId", clueId);
		
		List<Activity> list = as.getActivityListByNameAndNotByClueId(map);
		
		PrintJson.printJsonObj(resp, list);
	}

	private void unbund(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("解除关联操作");
		
		String id = req.getParameter("id");
		
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag = cs.unbund(id);
		PrintJson.printJsonFlag(resp, flag);
	}

	private void getActivityListByClueId(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("根据线索id来查询关联的市场活动列表");
		
		String clueId = req.getParameter("clueId");
		
		ActivityService as = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
		
		List<Activity> aList = as.getActivityListByClueId(clueId);
		
		PrintJson.printJsonObj(resp, aList);
		
	}

	private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("跳转到线索详细信息页");
		
		String id = req.getParameter("id");
		
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		
		Clue clue = cs.detail(id);
		
		req.setAttribute("c", clue);
		req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req, resp);
		
	}

	private void save(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入线索保存，正在处理中");
		
		String id = UUIDUtil.getUUID();
		System.out.println(id);
		String fullname = request.getParameter("fullname");
		String appellation = request.getParameter("appellation");
		String owner = request.getParameter("owner");
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String website = request.getParameter("website");
		String mphone = request.getParameter("mphone");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String createBy = ((User)request.getSession().getAttribute("user")).getName();
		String createTime = DateTimeUtil.getSysTime();
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String nextContactTime = request.getParameter("nextContactTime");
		String address = request.getParameter("address");
        
		Clue clue = new Clue();
		clue.setFullname(fullname);
		clue.setAddress(address);
		clue.setAppellation(appellation);
		clue.setCompany(company);
		clue.setContactSummary(contactSummary);
		clue.setCreateBy(createBy);
		clue.setCreateTime(createTime);
		clue.setDescription(description);
		clue.setEmail(email);
		clue.setJob(job);
		clue.setId(id);
		clue.setMphone(mphone);
		clue.setNextContactTime(nextContactTime);
		clue.setOwner(owner);
		clue.setPhone(phone);
		clue.setSource(source);
		clue.setState(state);
		clue.setWebsite(website);
		
		ClueService cs = (ClueService)ServiceFactory.getService(new ClueServiceImpl());
		
		boolean flag = cs.save(clue);
		
		PrintJson.printJsonFlag(response, flag);
		
	}

	private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("取得用户信息列表");
		
		UserService us = (UserService)ServiceFactory.getService(new UserServiceImpl());
		
		List<User> list = us.getUserList();
		
		PrintJson.printJsonObj(resp, list);
		
	}
	
}
