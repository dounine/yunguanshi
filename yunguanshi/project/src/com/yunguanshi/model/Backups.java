package com.yunguanshi.model;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.model.rbac.User;

/**
 * 备份类
 * @author Administrator
 *
 */
public class Backups {
	
	@FieldInfo(name="备份编号")
	private Integer id;
	@FieldInfo(name="用户外键")
	private User user;
	@FieldInfo(name="备份名称")
	private String backupsName;
	@FieldInfo(name="备份路径")
	private String backupsUrl;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getBackupsName() {
		return backupsName;
	}
	public void setBackupsName(String backupsName) {
		this.backupsName = backupsName;
	}
	public String getBackupsUrl() {
		return backupsUrl;
	}
	public void setBackupsUrl(String backupsUrl) {
		this.backupsUrl = backupsUrl;
	}
	
	
}
