package com.yunguanshi.model;


/**
 * 用户邮箱生成注册码
 * @author Administrator
 *
 */
public class Kaptcha {

	private Long id;
	private String createTime;//创建时间
	private String ipAdress;//ip地址
	private String email;//用户邮箱
	private String kaptcha;//验证码
	private Integer errorCount=0;//验证码输入错误次数
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public String getKaptcha() {
		return kaptcha;
	}
	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}
	
}
