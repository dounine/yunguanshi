package com.yunguanshi.model;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.model.rbac.User;

public class Voice {

	public Voice() {
		// TODO Auto-generated constructor stub
	}
	
	@FieldInfo(name = "语音记事编号")
	private Long id;
	@FieldInfo(name = "语音记事标题")
	private String voiceTitle;
	@FieldInfo(name = "语音记事路径")
	private String voiceSrc;
	@FieldInfo(name = "语音记事创建时间")
	private String voiceTimeStart;
	@FieldInfo(name = "用户表外间ID")
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVoiceTitle() {
		return voiceTitle;
	}
	public void setVoiceTitle(String voiceTitle) {
		this.voiceTitle = voiceTitle;
	}
	public String getVoiceSrc() {
		return voiceSrc;
	}
	public void setVoiceSrc(String voiceSrc) {
		this.voiceSrc = voiceSrc;
	}
	public String getVoiceTimeStart() {
		return voiceTimeStart;
	}
	public void setVoiceTimeStart(String voiceTimeStart) {
		this.voiceTimeStart = voiceTimeStart;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
