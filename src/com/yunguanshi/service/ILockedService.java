package com.yunguanshi.service;

import java.util.List;

import com.yunguanshi.model.Locked;

public interface ILockedService {

	public List<Locked> findLockeds(String ipAdress);
	
	public void add(Locked locked);
	
	public void deleteAll(String ipAdress);
	
	public void updateAccess(String ipAdress);
	
	public void updateErrorCount(String ipAdress,Integer count);
}
