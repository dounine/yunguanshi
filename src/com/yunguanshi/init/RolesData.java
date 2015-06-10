package com.yunguanshi.init;

import java.util.HashSet;
import java.util.Set;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.service.rbac.IPermissionService;
import com.yunguanshi.service.rbac.IRoleService;

public class RolesData implements IData{

	IRoleService roleService;
	IPermissionService permissionService;
	public RolesData(IRoleService roleService,IPermissionService permissionService){
		this.roleService = roleService;
		this.permissionService = permissionService;
	}
	@Override
	public void insert() throws ValidateFailureException {
		Set<Permission> permissions = new HashSet<Permission>();
		
		Role role = new Role();
		role.setLayer(0);
		role.setNodeType("root");
		role.setRoleCode("root");
		role.setRoleName("root");
		role.setNodeInfoType("root");
		role.setAvailable(true);
		role.setDescription("root");
		role.setResource("root");
		roleService.add(role);
		
		role = new Role();
		role.setLayer(1);
		role.setNodeType("管理员");
		role.setRoleCode("管理员");
		role.setRoleName("管理员");
		permissions.add(permissionService.findByName("用户管理"));
		role.setPermissions(permissions);
		role.setNodeInfoType("管理员");
		role.setAvailable(true);
		role.setParent(roleService.findByName("root"));
		role.setDescription("管理员");
		role.setResource("admin");
		roleService.add(role);
		
		role = new Role();
		role.setLayer(1);
		role.setNodeType("普通用户");
		role.setRoleCode("普通用户");
		role.setRoleName("普通用户");
		role.setNodeInfoType("普通用户");
		role.setAvailable(true);
		role.setParent(roleService.findByName("root"));
		role.setDescription("普通用户");
		role.setResource("normal");
		roleService.add(role);
	}
}
