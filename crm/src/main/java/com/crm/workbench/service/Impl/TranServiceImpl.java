package com.crm.workbench.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.dao.TranDao;
import com.crm.workbench.dao.TranHistoryDao;
import com.crm.workbench.domain.Customer;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.service.TranService;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

public class TranServiceImpl implements TranService {

	private TranDao tranDao = SqlSessionUtil.getSession().getMapper(TranDao.class);
	private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSession().getMapper(TranHistoryDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);
	
	@Override
	public boolean save(Tran t, String customerName) {
		boolean flag = true;
		
		/*
		 * 先处理客户相关的需求
		 * 	（1）判断customerName，根据客户名称在客户表进行精确查询
		 * 		如果有这个客户，则取出这个客户的id，封装到t对象中
		 * 		如果没有这个客户，则在客户表新建一条客户信息，然后将新建的客户的id取出，封装到t对象中
		 * 	（2）经过以上操作后，t对象中的信息就全了，需要执行添加交易的操作
		 * 	（3）添加交易完毕后，需要创建一条交易历史
		 */
		Customer c = customerDao.getCustomerByName(customerName);
		if(c == null) {
			//此时需要新建一个客户
			c = new Customer();
			c.setName(customerName);
			c.setId(UUIDUtil.getUUID());
			c.setCreateBy(t.getCreateBy());
			c.setCreateTime(DateTimeUtil.getSysTime());
			c.setContactSummary(t.getContactSummary());
			c.setDescription(t.getDescription());
			c.setNextContactTime(t.getNextContactTime());
			c.setOwner(t.getOwner());
			
			//添加客户
			int count1 = customerDao.save(c);
			if(count1 != 1) {
				flag = false;
			}
		}
		
		//将客户id封装到t对象中
		t.setCustomerId(c.getId());
		//添加交易
		int count2 = tranDao.save(t);
		if(count2 != 1) {
			flag = false;
		}
		
		//添加交易历史
		TranHistory th = new TranHistory();
		th.setId(UUIDUtil.getUUID());
		th.setCreateBy(t.getCreateBy());
		th.setCreateTime(DateTimeUtil.getSysTime());
		th.setExpectedDate(t.getExpectedDate());
		th.setMoney(t.getMoney());
		th.setStage(t.getStage());
		th.setTranId(t.getId());
		
		int count3 = tranHistoryDao.save(th);
		if(count3 != 1) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public Tran detail(String id) {

		Tran t = tranDao.detail(id);
		
		return t;
	}

	@Override
	public List<TranHistory> getHistoryListByTranId(String tranId) {
		
		List<TranHistory> list = tranHistoryDao.getHistoryListByTranId(tranId);
		
		return list;
	}

	@Override
	public boolean changeStage(Tran t) {
		boolean flag = true;
		
		//改变交易阶段
		int count1 = tranDao.changeStage(t);
		if(count1 != 1) {
			flag = false;
		}
		
		//生成交易历史
		TranHistory th = new TranHistory();
		
		th.setExpectedDate(t.getExpectedDate());
		th.setMoney(t.getMoney());
		th.setCreateBy(t.getEditBy());
		th.setCreateTime(DateTimeUtil.getSysTime());
		th.setTranId(t.getId());
		th.setStage(t.getStage());
		th.setId(UUIDUtil.getUUID());
		
		int count2 = tranHistoryDao.save(th);
		if(count2 != 1) {
			flag = false;
		}
		
		
		return flag;
	}

	@Override
	public Map<String, Object> getCharts() {
		
		//取得total
		int total = tranDao.getTotal();
		//取得dataList
		List<Map<String, Object>> dataList = tranDao.getCharts();
		//将total和dataList保存到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("dataList", dataList);
		//返回map
		
		return map;
	}
	
	
	
	
}
