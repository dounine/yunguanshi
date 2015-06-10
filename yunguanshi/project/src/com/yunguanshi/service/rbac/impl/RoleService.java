package com.yunguanshi.service.rbac.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.service.rbac.IRoleService;
import com.yunguanshi.utils.StringUtil;

/**
 * 角色接口实现.
 * @author huanghuanlai
 *
 */
@Service
@SuppressWarnings("all")
public class RoleService implements IRoleService{
	private static final Logger console = LoggerFactory.getLogger(RoleService.class);
	@Resource
	BaseDao<Role> roleDao;
	
	public Role add(Role role) throws ValidateFailureException {
		if(null!=findByName(role.getRoleName())){
			throw new ValidateFailureException("角色[ "+role.getRoleName()+" ]已存在");
		}
		return roleDao.save(role);
	}
	
	public Role findById(String roldId){
		return roleDao.get(Role.class, roldId);
	}

	public void deleteById(String roleId) {
		Role role = new Role();
		role.setRoleId(roleId);
		roleDao.delete(role);
	}

	@Override
	public void delete(String roleId) {
		Role role = new Role(roleId);
		roleDao.deleteByObj(role);
	}
	
	@Override
	public Long getCount() {
		return roleDao.getClassCount(Role.class);
	}

	@Override
	public Role edit(Role role) throws ValidateFailureException {
		Role _role = roleDao.findById(Role.class, role.getRoleId());
		if(null!=_role&&!_role.getRoleName().trim().equals(role.getRoleName().trim())&&_role.getRoleId().equals(role.getRoleId())){
			List<Role> roles = roleDao.find("from "+Role.class.getName()+" u where u.roleName=?", role.getRoleName());
			if(roles.size()>0){
				throw new ValidateFailureException("角色[ "+role.getRoleName()+" ]已经存在");
			}
		}
		return (Role) roleDao.updateByObj(role);
	}

	public void addRelationPermissions(String roleId, String... permissionIds) {
		Role role = findById(roleId);//查找角色
		Set<Permission> permissions = role.getPermissions();//获取角色中的所有权限
		Permission permission = null;
		for(String permissionId : permissionIds){
			permission = new Permission();
			permission.setPermissionId(permissionId);
			permissions.add(permission);
		}
	}

	public void deleteRelationPermissions(String roleId, String... permissionIds) {
		Role role = findById(roleId);//查找角色
		Set<Permission> permissions = role.getPermissions();//获取角色中的所有权限
		Permission permission = null;
		for(String permissionId : permissionIds){
			permission = new Permission();
			permission.setPermissionId(permissionId);
			permissions.remove(permission);
		}
	}

	@Override
	public Role findByName(String roleName) throws ValidateFailureException {
		List<Object> params = new ArrayList<Object>(1);
		params.add(roleName);
		List<Role> roles = (List<Role>) roleDao.findByHql("from "+Role.class.getName()+" r where r.roleName=?",params);
		if(roles.size()>0){
			return roles.get(0);
		}
		return null;
	}
	
	@Override
	public String findByNameId(String roleName){
		List<Object> params = new ArrayList<Object>(1);
		params.add(roleName);
		Table table = Role.class.getAnnotation(Table.class);
		String tableName = table.name();
		console.info("查询的表名为："+tableName);
		Object objects = roleDao.findBySqlArray("select roleId from "+tableName+" where roleName=?",params);
		return StringUtil.isNotEmpty(objects)?objects.toString():null;
	}
	
}
