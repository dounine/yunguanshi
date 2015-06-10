package com.yunguanshi.service;

import java.util.List;

import com.yunguanshi.model.Backups;
/**
 * 备份
 * @author Administrator
 *
 */
public interface IBackupsService {

		/**
		 * 添加
		 * @param voice
		 * @return
		 */
		public boolean add(Backups backups);
		
		/**
		 * 删除
		 * @param id
		 * @return
		 */
		public boolean delete(Integer id);
		
		/**
		 * ID查询
		 * @param id
		 * @return
		 */
		public Backups findById(String id);
		
		/**
		 * 查询所有
		 * @return
		 */
		public List<Backups> findBackups();
	}

