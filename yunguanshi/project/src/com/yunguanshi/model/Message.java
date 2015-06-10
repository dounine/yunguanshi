package com.yunguanshi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.yunguanshi.annotation.FieldInfo;
/**
 * 消息类.
 * @author huanghuanlai
 *
 */
@SuppressWarnings("all")
public class Message {
	
	@FieldInfo(name="信息编号")
	private Integer id;
	@NotEmpty(message="发送人不能为空")
	@FieldInfo(name="收信人")
	private String sendto;
	@NotEmpty(message="内容不能为空")
	@FieldInfo(name="信息内容")
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
