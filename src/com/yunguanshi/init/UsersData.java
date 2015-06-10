package com.yunguanshi.init;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.IDepartmentService;
import com.yunguanshi.service.rbac.IRoleService;
import com.yunguanshi.service.rbac.IUserService;

public class UsersData implements IData{
	
	IUserService userService;
	IDepartmentService departmentService;
	IRoleService roleService;
	public UsersData(IRoleService roleService,IUserService userService,IDepartmentService departmentService){
		this.userService = userService;
		this.departmentService = departmentService;
		this.roleService = roleService;
	}

	@Override
	public void insert() throws ValidateFailureException {
		User user = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Department department = null;
		Set<Role> roles = null;
		
		for(int i =0;i<2;i++){
			user = new User();
			if(i%5==0)user.setLocked(true);
			user.setBirthday(sdf.format(new Date()));
			user.setGender("男");
			user.setUserCode("admin"+i);
			user.setEmail("123456"+i+"@163.com");
			user.setPassword("admin123");
			user.setSumZone(10240000l);//默认空间大小10m
			user.setProgress(new Random().nextDouble());
			if(i>0){
				user.setRoles(roles);
				roles = new HashSet<Role>();
				roles.add(roleService.findByName("普通用户"));
				user.setRoles(roles);
				department = departmentService.findByName("普通会员");
				user.setDepartment(department);
				user.setUsername("admin"+i);
			}else{
				user.setUsername("admin");
				user.setPassword("lailake");
				user.setLocked(false);
				department = departmentService.findByName("管理员");
				user.setDepartment(department);
				roles = new HashSet<Role>();
				roles.add(roleService.findByName("管理员"));
				user.setRoles(roles);
			}
			userService.add(user);
		}
	}

}
