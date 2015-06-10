package com.yunguanshi.model;

import java.util.Date;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.model.rbac.User;

/**
 * 文字记事实体类
 * @author 莫丽华
 *
 */
public class WordNote {
	
	@FieldInfo(name="记事ID")
	private Long id;
	
	@FieldInfo(name="用户表主键")
	private Integer userId;
	
	@FieldInfo(name="记事标题")
	private String wordTitle;
	
	@FieldInfo(name="记事内容")
	private String wordContent;
	
	@FieldInfo(name="User类")
	private User user;
	
	@FieldInfo(name="创建时间")
	private Date timeCreate;
	
	@FieldInfo(name="更新时间")
	private Date timeUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWordTitle() {
		return wordTitle;
	}

	public void setWordTitle(String wordTitle) {
		this.wordTitle = wordTitle;
	}

	public String getWordContent() {
		return wordContent;
	}

	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
			
	public Date getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}

	public Date getTimeUpdate() {
		return timeUpdate;
	}

	public void setTimeUpdate(Date timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
}
