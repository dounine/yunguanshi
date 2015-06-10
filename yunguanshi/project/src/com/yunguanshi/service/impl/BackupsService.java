package com.yunguanshi.service.impl;
import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;


import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.Backups;
import com.yunguanshi.service.IBackupsService;

@Service
public class BackupsService implements IBackupsService {
	BaseDao<Backups> backupsDao;

	@Override
	public boolean add(Backups backups) {
		List<Backups> list = new ArrayList<Backups>();
		boolean b=false;
		list.add(backups);
		backupsDao.batchSave(list);
		b=true;
		return b;
	}

	@Override
	public boolean delete(Integer id) {
		boolean b = false;
		backupsDao.delete(Backups.class, id);
		b = true;
		return b;
	}

	@Override
	public Backups findById(String id) {
		// TODO Auto-generated method stub
		return backupsDao.findById(Backups.class, id);
	}

	@Override
	public List<Backups> findBackups() {
		String sql = "from backups where userId = ?";
		return backupsDao.find(sql);
	}
	
}
