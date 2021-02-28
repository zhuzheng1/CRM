package com.crm.workbench.service.Impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;

import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.dao.ClueActivityRelationDao;
import com.crm.workbench.dao.ClueDao;
import com.crm.workbench.dao.ClueRemarkDao;
import com.crm.workbench.dao.ContactsActivityRelationDao;
import com.crm.workbench.dao.ContactsDao;
import com.crm.workbench.dao.ContactsRemarkDao;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.dao.CustomerRemarkDao;
import com.crm.workbench.dao.TranDao;
import com.crm.workbench.dao.TranHistoryDao;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.domain.ClueActivityRelation;
import com.crm.workbench.domain.ClueRemark;
import com.crm.workbench.domain.Contacts;
import com.crm.workbench.domain.ContactsActivityRelation;
import com.crm.workbench.domain.ContactsRemark;
import com.crm.workbench.domain.Customer;
import com.crm.workbench.domain.CustomerRemark;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
	
	//线索相关表
	ClueDao clueDao = SqlSessionUtil.getSession().getMapper(ClueDao.class);
	ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSession().getMapper(ClueActivityRelationDao.class);
	ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSession().getMapper(ClueRemarkDao.class);
	
	//客户相关表
	CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);
	CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSession().getMapper(CustomerRemarkDao.class);
	
	//联系人相关表
	ContactsDao contactsDao = SqlSessionUtil.getSession().getMapper(ContactsDao.class);
	ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSession().getMapper(ContactsRemarkDao.class);
	ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSession().getMapper(ContactsActivityRelationDao.class);
	
	//交易相关表
	TranDao tranDao = SqlSessionUtil.getSession().getMapper(TranDao.class);
	TranHistoryDao tranHistoryDao = SqlSessionUtil.getSession().getMapper(TranHistoryDao.class);
		
	@Override
	public boolean save(Clue clue) {

		boolean flag = true;
		
		int reslut = clueDao.save(clue);
		
		if(reslut != 1) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public Clue detail(String id) {

		Clue clue = clueDao.detail(id);
		
		return clue;
	}

	@Override
	public boolean unbund(String id) {

		boolean flag = true;
		
		int result = clueDao.unbund(id);
		
		if(result != 1) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public boolean bund(String cid, String[] aids) {
		
		boolean flag = true;
		
		for(String aid : aids) {
			//取得每一个aid和cid做关联
			ClueActivityRelation car1 = new ClueActivityRelation();
			car1.setId(UUIDUtil.getUUID());
			car1.setActivityId(aid);
			car1.setClueId(cid);
			int result = clueActivityRelationDao.bund(car1);
			if(result != 1) {
				flag = false;
			}
			
		}
		return flag;
	}

	@Override
	public boolean convert(String clueId, Tran t, String createBy) {
		
		boolean flag = true;
		String createTime = DateTimeUtil.getSysTime();
		
		//(1)通过线索id过去线索对象（线索对象当中封装了下线索的信息）
		Clue clue = clueDao.getById(clueId);
		
		//（2）通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该用户是否存在！）
		String company = clue.getCompany();
		Customer c = customerDao.getCustomerByName(company);
		
		//如果c为null，说明没有这个客户，需要新建一个
		if(c == null) {
			c = new Customer();
			c.setId(UUIDUtil.getUUID());
			c.setAddress(clue.getAddress());
			c.setWebsite(clue.getWebsite());
			c.setPhone(clue.getPhone());
			c.setOwner(clue.getOwner());
			c.setNextContactTime(clue.getNextContactTime());
			c.setName(company);
			c.setDescription(clue.getDescription());
			c.setCreateBy(createBy);
			c.setCreateTime(createTime);
			c.setContactSummary(clue.getContactSummary());
			//添加客户
			int count1 = customerDao.save(c);
			if(count1 != 1) {
				flag = false;
			}
			
		}
		
		/*
		 * 经过第二步处理后，客户的信息我们已经拥有了，将来在处理其他表的时候，如果要使用到客户的id
		 * 直接使用c.getId();
		 */
		
		//（3）通过线索对象提取联系人信息，保存联系人
		Contacts con = new Contacts();
		con.setId(UUIDUtil.getUUID());
		con.setSource(clue.getSource());
		con.setOwner(clue.getOwner());
		con.setNextContactTime(clue.getNextContactTime());
		con.setMphone(clue.getMphone());
		con.setJob(clue.getJob());
		con.setFullname(clue.getFullname());
		con.setEmail(clue.getEmail());
		con.setDescription(clue.getDescription());
		con.setCustomerId(clue.getId());
		con.setCreateBy(createBy);
		con.setCreateTime(createTime);
		con.setContactSummary(clue.getContactSummary());
		con.setAppellation(clue.getAppellation());
		con.setAddress(clue.getAddress());
		
		//添加联系人
		int count2 = contactsDao.save(con);
		if(count2 != 1) {
			flag = false;
		}
		/*
		 * 经过第三步处理后，联系人的信息我们已经拥有了，将来在处理其他表的时候，如果要使用到联系人的id
		 * 直接使用con.getId();
		 */
		
		//（4）线索备注转换到客户备注以及联系人备注，查询出与该线索关联的备注信息列表
		List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
		for (ClueRemark clueRemark : clueRemarkList) {
			//取出每一条线索的备注信息(主要转换到客户备注和联系人备注的就是这个备注信息)
			String noteContent = clueRemark.getNoteContent();
			
			//创建客户备注对象，添加客户备注
			CustomerRemark customerRemark = new CustomerRemark();
			customerRemark.setId(UUIDUtil.getUUID());
			customerRemark.setCreateBy(createBy);
			customerRemark.setCreateTime(createTime);
			customerRemark.setCustomerId(c.getId());
			customerRemark.setEditFlag("0");
			customerRemark.setNoteContent(noteContent);
			int count3 = customerRemarkDao.save(customerRemark);
			if(count3 != 1) {
				flag = false;
			}
			
			//创建联系人备注对象，添加联系人
			ContactsRemark contactsRemark = new ContactsRemark();
			contactsRemark.setId(UUIDUtil.getUUID());
			contactsRemark.setCreateBy(createBy);
			contactsRemark.setCreateTime(createTime);
			contactsRemark.setContactsId(con.getId());
			contactsRemark.setEditFlag("0");
			contactsRemark.setNoteContent(noteContent);
			int count4 = contactsRemarkDao.save(contactsRemark);
			if(count4 != 1) {
				flag = false;
			}
			
		}
		
		//(5)“线索和市场活动” 的关系转换到 “联系人和市场活动” 的关系,查询出与该条线索关联的市场活动，
		//查询与市场活动的关联关系列表
		List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
		//遍历出每一条与市场活动关联的关联关系记录
		for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
			//从每一条遍历出来的记录中取出关联的市场活动id
			String activityId = clueActivityRelation.getActivityId();
			
			//创建  联系人与市场活动的关联关系对象，让第三步生成的联系人与市场或你懂做关联
			ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
			contactsActivityRelation.setId(UUIDUtil.getUUID());
			contactsActivityRelation.setContactsId(con.getId());
			contactsActivityRelation.setActivityId(activityId);
			//添加联系人与市场活动的关联关系
			int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
			if(count5 != 1) {
				flag = false;
			}
			
			
		}
		
		//（6）如果有创建交易的过程，需要创建一条交易
		if(t != null) {
			/*
			 * t对象在controller里面已经封装好的信息如下：
			 * 	id，money，name，expectedDate，stage，activityId，createBy，createTime
				接下来可以通过第一步生成的clue对象，取出一些信息，继续完善t对象的封装
			 */
			t.setSource(clue.getSource());
			t.setOwner(clue.getOwner());
			t.setNextContactTime(clue.getNextContactTime());
			t.setDescription(clue.getDescription());
			t.setCustomerId(c.getId());
			t.setContactSummary(clue.getContactSummary());
			t.setContactsId(con.getId());
			//添加交易
			int count6 = tranDao.save(t);
			if(count6 != 1) {
				flag = false;
			}
			//(7)如果创建了交易，则创建一条该交易下的交易历史
			TranHistory th = new TranHistory();
			th.setId(UUIDUtil.getUUID());
			th.setCreateBy(createBy);
			th.setCreateTime(createTime);
			th.setExpectedDate(t.getExpectedDate());
			th.setMoney(t.getMoney());
			th.setStage(t.getStage());
			th.setTranId(t.getId());
			//添加交易历史
			int count7 = tranHistoryDao.save(th);
			if(count7 != 1) {
				flag = false;
			}
			
		}
		
		//(8)删除线索备注
		for(ClueRemark clueRemark : clueRemarkList) {
			int count8 = clueRemarkDao.delete(clueRemark);
			if(count8 != 1) {
				flag = false;
			}
			
		}
		
		//（9）删除线索和市场活动的关系
		for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
			int count9 = clueActivityRelationDao.delete(clueActivityRelation);
			if(count9 != 1) {
				flag = false;
			}
			
		}
		
		//（10）删除线索
		int count10 = clueDao.delete(clueId);
		if(count10 != 1) {
			flag = false;
		}
		
		return flag;
	}

}
