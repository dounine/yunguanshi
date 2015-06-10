package com.yunguanshi.service.rbac.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.IPermissionService;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.hibernate.Pagination;

/**
 * 权限接口实现.
 * @author huanghuanlai
 *
 */
@Service
@SuppressWarnings("all")
public class PermissionService implements IPermissionService{
	
	@Resource
	BaseDao<Permission> permissionDao;
	
	public Permission findById(String permissionId){
		return permissionDao.get(Permission.class, permissionId);
	}

	public void deleteById(String permissionId) {
		Permission permission = new Permission();
		permission.setPermissionId(permissionId);
		permissionDao.delete(permission);
	}
	
	public void deleteByName(String permissionName) {
		List<Permission> permissions = findByNames(permissionName);
		for(Permission permission2 : permissions){
			permissionDao.delete(permission2);
		}
	}
	
	@Override
	public Pagination<Permission> findPermissions(Pagination<Permission> pagination) {
		return permissionDao.findByClass(Permission.class,pagination,Order.desc("id"));
	}
	
	public List<Permission> findPermissions(){
		return permissionDao.findByClass(Permission.class);
	}
	
	@Override
	public Long getCount() {
		return permissionDao.getClassCount(Permission.class);
	}

	@Override
	public void delete(String permissionId) {
		permissionDao.deleteByObj(findById(permissionId));
	}

	@Override
	public Permission editCRUD(Permission permission) throws ValidateFailureException {
		Permission _permission = permissionDao.findById(Permission.class, permission.getPermissionId());
		boolean isResAdd = permission.isResAdd();//作为临时判断是否锁定用
		boolean isResDelete = permission.isResDelete();
		boolean isResUpdate = permission.isResUpdate();
		boolean isResRead = permission.isResRead();
		List<Permission> _permissions = findByNames(_permission.getPermissionName());
		for(Permission pp : _permissions){
			if(pp.isResAdd()){//查询添加权限
				if(pp.isResAdd()!=isResAdd){//如果不相等则是操作已经修改
					pp.setLocked(!isResAdd);
					edit(pp);
				}
			}
			if(pp.isResDelete()){
				if(pp.isResDelete()!=isResDelete){
					pp.setLocked(!isResDelete);
					edit(pp);
				}
			}
			if(pp.isResUpdate()){
				if(pp.isResUpdate()!=isResUpdate){
					pp.setLocked(!isResUpdate);
					edit(pp);
				}
			}
			if(pp.isResRead()){
				if(pp.isResRead()!=isResRead){
					pp.setLocked(!isResRead);
					edit(pp);
				}
			}
		}
		if(null!=_permission&&!_permission.getPermissionName().trim().equals(permission.getPermissionName().trim())&&_permission.getPermissionId().equals(permission.getPermissionId())){
			List<Permission> permissions = permissionDao.find("from "+Permission.class.getName()+" u where u.permissionName=?", permission.getPermissionName());
			if(permissions.size()>0){
				throw new ValidateFailureException("权限[ "+permission.getPermissionName()+" ]已经存在");
			}
		}
		permission.setResAdd(isResAdd);//恢复
		permission.setResDelete(isResDelete);
		permission.setResUpdate(isResUpdate);
		permission.setResRead(isResRead);
		return (Permission) permissionDao.updateByObj(permission);
	}
	
	
	public void edit(Permission permission){
		permissionDao.updateByObj(permission);
	}
	
	
	public Permission add(Permission permission) throws ValidateFailureException {
		if(null!=findByPermission(permission)){
			throw new ValidateFailureException("权限[ "+permission.getPermissionName()+" ]已存在");
		}
		if(permission.getParent()==null){
			if(!permission.getPermissionName().equals(PropUtil.get("rootNode"))){
				permission.setParent(new Permission(findByName(PropUtil.get("rootNode")).getPermissionId()));
			}
		}else if((permission.getParent()!=null)){
			if(!permission.getPermissionName().equals(PropUtil.get("rootNode"))){
				permission.setParent(new Permission(findByName(PropUtil.get("rootNode")).getPermissionId()));
			}
		}
		Permission permission2 = (Permission) permissionDao.saveByObj(permission);
		return permission2;
		
	}

	@Override
	public Permission findByName(String permissionName) {
		List<Object> params = new ArrayList<Object>(1);
		params.add(permissionName);
		List<Permission> permissions = (List<Permission>) permissionDao.findByHql("from "+Permission.class.getName()+" r where r.permissionName=?",params);
		if(permissions.size()>0){
			return permissions.get(0);
		}
		return null;
	}
	
	@Override
	public List<Permission> findByNames(String permissionName) {
		List<Object> params = new ArrayList<Object>(1);
		params.add(permissionName);
		List<Permission> permissions = (List<Permission>) permissionDao.findByHql("from "+Permission.class.getName()+" r where r.permissionName=?",params);
		return permissions;
	}
	
	public Permission findByPermission(Permission permission){
		List<Object> params = new ArrayList<Object>(1);
		params.add(permission.getPermissionName());
		List<Permission> permissions = (List<Permission>) permissionDao.findByHql("from "+Permission.class.getName()+" r where r.permissionName=?",params);
		if(permissions.size()>0){
			for(Permission permission2 : permissions){
				if(permission2.isResAdd()&&permission.isResAdd()){
					return new Permission();
				}
				if(permission2.isResDelete()&&permission.isResDelete()){
					return new Permission();
				}
				if(permission2.isResUpdate()&&permission.isResUpdate()){
					return new Permission();
				}
				if(permission2.isResRead()&&permission.isResRead()){
					return new Permission();
				}
			}
		}
		return null;
	}
	
	public Permission findPermissionByCRUD(String parentId,String crud){
		List<Object> params = new ArrayList<Object>(1);
		params.add(parentId);
		List<Permission> permissions = (List<Permission>) permissionDao.findByHql("from "+Permission.class.getName()+" r where r.permissionName=? and r."+crud+"=true",params);
		if(permissions.size()>0){
			return permissions.get(0);
		}
		return null;
	}
}
