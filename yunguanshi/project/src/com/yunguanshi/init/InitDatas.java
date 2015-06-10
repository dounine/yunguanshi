package com.yunguanshi.init;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunguanshi.auth.ShiroDBRealm;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.impl.MsgService;
import com.yunguanshi.service.rbac.impl.DepartmentService;
import com.yunguanshi.service.rbac.impl.TreeNodeService;
import com.yunguanshi.service.rbac.impl.PermissionService;
import com.yunguanshi.service.rbac.impl.RoleService;
import com.yunguanshi.service.rbac.impl.UserService;

/**
 * 数据初始化.
 * @author huanghaunlai
 * 
 */
@SuppressWarnings("all")
public class InitDatas implements Runnable{
	private static final Logger logger = LoggerFactory
			.getLogger(InitDatas.class);
	private MsgService msgService = null;
	private UserService userService = null;
	private RoleService roleService = null;
	private PermissionService permissionService = null;
	private DepartmentService departmentService = null;
	
	public InitDatas(ShiroDBRealm shiroDBRealm) {
		this.msgService = shiroDBRealm.msgService;
		this.userService = shiroDBRealm.userService;
		this.roleService = shiroDBRealm.roleService;
		this.permissionService = shiroDBRealm.permissionService;
		this.departmentService = shiroDBRealm.departmentService;
	}

	public void init() {
		logger.info("================> 数据写入开始 <================");
		try {
			logger.info("================> 部门数据写入完成 <================");
			addDepartment();
			logger.info("================> 部门数据写入完成 <================");
			logger.info("================> 权限数据写入开始 <================");
			addPermission();
			logger.info("================> 权限数据写入完成 <================");
			logger.info("================> 角色数据写入开始 <================");
			addRole();
			logger.info("================> 角色数据写入完成 <================");
			logger.info("================> 用户数据写入开始 <================");
			addUser();
			logger.info("================> 用户数据写入完成 <================");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("================> 数据写入完毕 <================");
	}
	
	public void addDepartment() throws Exception{
		DepartmentsData departmentData = new DepartmentsData(departmentService);
		departmentData.insert();
	}
	
	public void addUser() throws Exception{
		UsersData usersData = new UsersData(roleService,userService,departmentService);
		usersData.insert();
		
	}
	
	public void addRole() throws Exception{
		RolesData rolesData = new RolesData(roleService,permissionService);
		rolesData.insert();
	}
	
	public void addPermission() throws Exception{
		PermissionsData permissionsData = new PermissionsData(permissionService);
		permissionsData.insert();
	}
	

	@Override
 	public void run() {
		try {
			Thread.sleep(3000);
			init();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
