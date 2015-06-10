package com.yunguanshi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.Locked;
import com.yunguanshi.service.ILockedService;
import com.yunguanshi.utils.DateUtil;

@Service
public class LockedServiceImpl implements ILockedService{
	
	@Resource
	BaseDao<Locked> baseDao;

	@Override
	public List<Locked> findLockeds(String ipAdress) {
		List<Object> params = new ArrayList<Object>(1);
		params.add(ipAdress);
		return baseDao.findByhql("from Locked l where l.ipAdress=?", params);
	}

	@Override
	public void add(Locked locked) {
		baseDao.save(locked);
	}

	@Override
	public void deleteAll(String ipAdress) {
		List<String> mList = new ArrayList<String>(1);
		mList.add(ipAdress);
		baseDao.deleteByhql("delete Locked  where ipAdress=?",mList);
	}

	@Override
	public void updateAccess(String ipAdress) {
		List<Locked> list = findLockeds(ipAdress);
		List<Object> mList = new ArrayList<Object>(1);
		mList.add(ipAdress);
		list.get(0).setAccess(false);
		baseDao.update(list.get(0));
	}
	
	public void updateErrorCount(String ipAdress,Integer count){
		List<Locked> lockeds = findLockeds(ipAdress);
		if(lockeds!=null&&lockeds.size()>0){
			lockeds.get(0).setErrorCount(count!=null?count:lockeds.get(0).getErrorCount()+1);
			baseDao.update(lockeds.get(0));
		}else{
			Locked locked = new Locked();
			locked.setBeginLockTime(DateUtil.getCurLongDateTime());
			locked.setEndLockTime(DateUtil.incrementMin(new Date(),locked.getLongTime()));
			locked.setIpAdress(ipAdress);
			locked.setErrorCount(count!=null?count:1);
			baseDao.save(locked);
		}
	}

}
