package com.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.service.CustomerService;
import com.crm.workbench.service.TranService;
import com.crm.workbench.service.Impl.CustomerServiceImpl;
import com.crm.workbench.service.Impl.TranServiceImpl;

public class TranController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("进入到交易模块控制器");
		
		String path = req.getServletPath();
		
		if("/workbench/transaction/add.do".equals(path)) {
			add(req, resp);
		}else if("/workbench/transaction/getCustomerName.do".equals(path)) {
			getCustomerName(req, resp);
		}else if("/workbench/transaction/save.do".equals(path)) {
			save(req, resp);
		}else if("/workbench/transaction/detail.do".equals(path)) {
			detail(req, resp);
		}else if("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
			getHistoryListByTranId(req, resp);
		}else if("/workbench/transaction/changeStage.do".equals(path)) {
			changeStage(req, resp);
		}else if("/workbench/transaction/getCharts.do".equals(path)) {
			getCharts(req, resp);
		}
	}

	private void getCharts(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("取得交易阶段数量统计图表的数据");
		TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());
		
		Map<String, Object> map = ts.getCharts();
		PrintJson.printJsonObj(resp, map);
	}

	private void changeStage(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("进入修改阶段操作");
		String stage = req.getParameter("stage");
		String id = req.getParameter("id");
		String editBy = ((User)req.getSession().getAttribute("user")).getName();
		String editTime = DateTimeUtil.getSysTime();
		String expectedDate = req.getParameter("expectedDate");
		String money = req.getParameter("money");
		
		Tran t = new Tran();
		t.setId(id);
		t.setStage(stage);
		t.setEditBy(editBy);
		t.setEditTime(editTime);
		t.setExpectedDate(expectedDate);
		t.setMoney(money);
		
		Map<String, String> pMap = (Map<String, String>)this.getServletContext().getAttribute("pMap");
		t.setPossibility(pMap.get(stage));
		
		TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());
		
		boolean flag = ts.changeStage(t);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("t", t);
		
		PrintJson.printJsonObj(resp, map);
		
	}

	private void getHistoryListByTranId(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("根据交易id取得相应的历史列表");
		
		String tranId = req.getParameter("tranId");
		
		TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());
		
		List<TranHistory> list = ts.getHistoryListByTranId(tranId);
		
		//阶段和可能性之间的关系
		ServletContext application = this.getServletContext();
		Map<String, String> pMap = (Map<String, String>)application.getAttribute("pMap");
		
		//将交易历史列表遍历
		for (TranHistory th : list) {
			//根据每条交易历史，取出每一个阶段
			String stage = th.getStage();
			String possibility = pMap.get(stage);
			th.setPossibility(possibility);
		}
		PrintJson.printJsonObj(resp, list);
		
	}

	private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("跳转到详细信息页");
		
		String id = req.getParameter("id");
		
		TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());
		
		Tran t = ts.detail(id);
		
		//处理可能性
		//阶段和可能性之间的对应关系，pMap,以下三种方法任选其一
		String stage = t.getStage();
		ServletContext application = this.getServletContext();
		Map<String, String> pMap = (Map<String, String>)application.getAttribute("pMap");
		String possibility = pMap.get(stage);
		
		t.setPossibility(possibility);
		
//		ServletContext application2 = req.getServletContext();
//		ServletContext application3 = this.getServletConfig().getServletContext();
		
		req.setAttribute("t", t);
		//req.setAttribute("p", possibility);
		req.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(req, resp);
		
	}

	private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("执行添加交易的操作");
		
		String id              = UUIDUtil.getUUID();
		String owner           = req.getParameter("owner");
		String money           = req.getParameter("money");
		String name            = req.getParameter("name");
		String expectedDate    = req.getParameter("expectedDate");
		String customerName      = req.getParameter("customerName");//此处还只有客户名称，没有id
		String stage           = req.getParameter("stage");
		String type            = req.getParameter("type");
		String source          = req.getParameter("source");
		String activityId      = req.getParameter("activityId");
		String contactsId      = req.getParameter("contactsId");
		String createBy        = ((User)req.getSession().getAttribute("user")).getName();
		String createTime      = DateTimeUtil.getSysTime();
		String description     = req.getParameter("description");
		String contactSummary  = req.getParameter("contactSummary");
		String nextContactTime = req.getParameter("nextContactTime");

		Tran t = new Tran();
		t.setId(id);
		t.setActivityId(activityId);
		t.setContactsId(contactsId);
		t.setContactSummary(contactSummary);
		t.setCreateBy(createBy);
		t.setCreateTime(createTime);
		t.setDescription(description);
		t.setExpectedDate(expectedDate);
		t.setMoney(money);
		t.setName(name);
		t.setNextContactTime(nextContactTime);
		t.setOwner(owner);
		t.setSource(source);
		t.setStage(stage);
		t.setType(type);
		
		TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());
		
		boolean flag = ts.save(t,customerName);
		
		if(flag) {
			//如果添加成功，跳转到列表页
			resp.sendRedirect(req.getContextPath()+"/workbench/transaction/index.jsp");
		}
	}

	private void getCustomerName(HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("进入自动补全操作");
		
		String name = req.getParameter("name");
		
		CustomerService cs = (CustomerService)ServiceFactory.getService(new CustomerServiceImpl());
		
		List<String> list = cs.getCustomerName(name);
		PrintJson.printJsonObj(resp, list);
		
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("进入到跳转到交易添加页的操作");
		UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
		
		List<User> uList = us.getUserList();
		
		req.setAttribute("uList", uList);
		req.getRequestDispatcher("/workbench/transaction/save.jsp").forward(req, resp);;
		
		
	}

	
	
}
