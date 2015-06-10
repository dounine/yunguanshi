package com.yunguanshi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.controller.util.ModelController;
import com.yunguanshi.model.Backups;
import com.yunguanshi.service.IBackupsService;
/**
 */
@Controller
@RequestMapping("backups")
public class BackupsController {
	
	private static final Logger console = LoggerFactory.getLogger(ModelController.class);
	
	@Resource
	IBackupsService iBackupsService;
	/**
	 * 添加
	 * @param backups
	 * @return
	 */
	@RequestMapping(value="setBackups.html")
	@ResponseBody
	public boolean setBackups(Backups backups){
		console.info("添加成功");
		return false;
		
	}
	/**
	 * 删除
	 * @param backups
	 * @return
	 */
	@RequestMapping(value="getDelete.html")
	@ResponseBody
	public boolean getDelete(Backups backups){
		console.info("删除成功");
		return false;
		
	}
	/**
	 * 按ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findById.html")
	@ResponseBody
	public String findById(Integer id){
		return null;
	}
	/**
	 * 查询所有
	 * @return
	 */
	@RequestMapping(value="findBackups.html")
	@ResponseBody
	public List<Backups> findBackups(){
		return null;
	}

}
