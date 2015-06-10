package com.yunguanshi.init;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.service.rbac.IPermissionService;

public class PermissionsData implements IData{

	IPermissionService permissionService;
	public PermissionsData(IPermissionService permissionService){
		this.permissionService = permissionService;
	}
	@Override
	public void insert() throws ValidateFailureException {
		Permission permission = new Permission();//根
		permission.setLocked(true);
		permission.setPermissionName("root");
		permission.setPermissionCode("root");
		permission.setNodeType("root");
		permission.setNodeInfo("root");
		permission.setNodeInfoType("root");
		permission.setDescription("root");
		permission.setResource("root");
		permission.setLayer(0);
		permission.setResAdd(true);
		permissionService.add(permission);
		
		permission = new Permission();//用户管理
		permission.setPermissionName("用户管理");
		permission.setPermissionCode("用户管理");
		permission.setNodeType("用户管理");
		permission.setNodeInfo("用户管理");
		permission.setNodeInfoType("用户管理");
		permission.setDescription("用户添加");
		permission.setResource("user");
		permission.setLayer(1);
		permission.setParent(permissionService.findByName("root"));

		permission.setResAdd(true);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResDelete(true);
		permission.setResAdd(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResUpdate(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResRead(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permissionService.add(permission);
		
		/*=================分组管理=====================*/
		
		permission = new Permission();//用户管理
		permission.setPermissionName("分组管理");
		permission.setPermissionCode("分组管理");
		permission.setNodeType("分组管理");
		permission.setNodeInfo("分组管理");
		permission.setNodeInfoType("分组管理");
		permission.setDescription("分组添加");
		permission.setResource("department");
		permission.setParent(permissionService.findByName("root"));
		permission.setLayer(1);
		
		permission.setResAdd(true);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResDelete(true);
		permission.setResAdd(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResUpdate(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResRead(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permissionService.add(permission);
		
		/*=================角色管理=====================*/
		
		permission = new Permission();//角色管理
		permission.setPermissionName("角色管理");
		permission.setPermissionCode("角色管理");
		permission.setNodeType("角色管理");
		permission.setNodeInfo("角色管理");
		permission.setNodeInfoType("角色管理");
		permission.setDescription("角色添加");
		permission.setResource("department");
		permission.setParent(permissionService.findByName("root"));
		permission.setLayer(1);
		
		permission.setResAdd(true);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResDelete(true);
		permission.setResAdd(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResUpdate(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResRead(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permissionService.add(permission);
		
		/*=================权限管理=====================*/
		
		permission = new Permission();//角色管理
		permission.setPermissionName("权限管理");
		permission.setPermissionCode("权限管理");
		permission.setNodeType("权限管理");
		permission.setNodeInfo("权限管理");
		permission.setNodeInfoType("权限管理");
		permission.setDescription("权限添加");
		permission.setResource("department");
		permission.setParent(permissionService.findByName("root"));
		permission.setLayer(1);
		
		permission.setResAdd(true);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResDelete(true);
		permission.setResAdd(false);
		permission.setResUpdate(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResUpdate(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResRead(false);
		permissionService.add(permission);
		
		permission.setResRead(true);
		permission.setResAdd(false);
		permission.setResDelete(false);
		permission.setResUpdate(false);
		permissionService.add(permission);
	}
}
