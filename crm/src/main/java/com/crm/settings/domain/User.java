package com.crm.settings.domain;

public class User {

	/*
	 * 关于字符串中表现的日期及时间
	 * 我们在市场上常用的有两种方式
	 * 日期，年月日
	 * 		yyyy-MM-dd  十位字符串
	 * 
	 * 日期+时间，年月日时分秒
	 * 		yyyy-MM-dd HH:mm:ss  19位字符串
	 */
	
	private String id;  //编号，主键
	private String loginAct; //登录账号
	private String name;  //用户真实姓名
	private String loginPwd;  //登录密码
	private String email;  //邮箱
	private String expireTime; //失效时间  19位
	private String lockState; //锁定状态  0：锁定    1：启用
	private String deptno; //部门编号
	private String allowIps;  //允许访问的IP地址
	private String createTime;  //创建时间  19位
	private String createBy;//创建人
	private String editTime; //修改时间  19位
	private String editBy; //修改人
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allowIps == null) ? 0 : allowIps.hashCode());
		result = prime * result
				+ ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((deptno == null) ? 0 : deptno.hashCode());
		result = prime * result + ((editBy == null) ? 0 : editBy.hashCode());
		result = prime * result
				+ ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((expireTime == null) ? 0 : expireTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lockState == null) ? 0 : lockState.hashCode());
		result = prime * result
				+ ((loginAct == null) ? 0 : loginAct.hashCode());
		result = prime * result
				+ ((loginPwd == null) ? 0 : loginPwd.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (allowIps == null) {
			if (other.allowIps != null)
				return false;
		} else if (!allowIps.equals(other.allowIps))
			return false;
		if (createBy == null) {
			if (other.createBy != null)
				return false;
		} else if (!createBy.equals(other.createBy))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (deptno == null) {
			if (other.deptno != null)
				return false;
		} else if (!deptno.equals(other.deptno))
			return false;
		if (editBy == null) {
			if (other.editBy != null)
				return false;
		} else if (!editBy.equals(other.editBy))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (expireTime == null) {
			if (other.expireTime != null)
				return false;
		} else if (!expireTime.equals(other.expireTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lockState == null) {
			if (other.lockState != null)
				return false;
		} else if (!lockState.equals(other.lockState))
			return false;
		if (loginAct == null) {
			if (other.loginAct != null)
				return false;
		} else if (!loginAct.equals(other.loginAct))
			return false;
		if (loginPwd == null) {
			if (other.loginPwd != null)
				return false;
		} else if (!loginPwd.equals(other.loginPwd))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", loginAct=" + loginAct + ", name=" + name
				+ ", loginPwd=" + loginPwd + ", email=" + email
				+ ", expireTime=" + expireTime + ", lockState=" + lockState
				+ ", deptno=" + deptno + ", allowIps=" + allowIps
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", editTime=" + editTime + ", editBy=" + editBy + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginAct() {
		return loginAct;
	}
	public void setLoginAct(String loginAct) {
		this.loginAct = loginAct;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getLockState() {
		return lockState;
	}
	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public String getAllowIps() {
		return allowIps;
	}
	public void setAllowIps(String allowIps) {
		this.allowIps = allowIps;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}

}
