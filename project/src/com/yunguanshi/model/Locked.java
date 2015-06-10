package com.yunguanshi.model;


/**
 * 锁定
 * @author Administrator
 *
 */
public class Locked {

	public Long id;
	private String ipAdress;//ip地址
	private String beginLockTime;//锁定时间
	private String endLockTime;//结束时间
	private boolean access=true;//是否第一次访问
	private Integer errorCount =0;//操作错误次数
	private Integer longTime = 60;//锁定时间默认60分钟
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public String getBeginLockTime() {
		return beginLockTime;
	}
	public void setBeginLockTime(String beginLockTime) {
		this.beginLockTime = beginLockTime;
	}
	
	public Integer getLongTime() {
		return longTime;
	}
	public void setLongTime(Integer longTime) {
		this.longTime = longTime;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public String getEndLockTime() {
		return endLockTime;
	}
	public void setEndLockTime(String endLockTime) {
		this.endLockTime = endLockTime;
	}
	public boolean isAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	
}
