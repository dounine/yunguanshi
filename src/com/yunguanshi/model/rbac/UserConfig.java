package com.yunguanshi.model.rbac;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class UserConfig {
	
	public UserConfig(){}

	@Transient
	private Boolean localTime=true;//是否是本地时间
	@Transient
	private String deptId;//部门编号
	@Transient
	private String deptName;//部门名称
	@Transient
	private String[] rolearrays;//角色列表
	
	
	public Boolean getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Boolean localTime) {
		this.localTime = localTime;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String[] getRolearrays() {
		return rolearrays;
	}
	public void setRolearrays(String[] rolearrays) {
		this.rolearrays = rolearrays;
	}
	
}
