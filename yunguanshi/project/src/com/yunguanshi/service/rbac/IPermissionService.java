package com.yunguanshi.service.rbac;

import java.util.List;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.utils.hibernate.Pagination;

/**
 * 权限服务接口.
 * @author huanghuanlai
 *
 */
public interface IPermissionService {
	
	/**
	 * 编辑权限中的CRUD
	 * @param role
	 * @return
	 */
	public Permission editCRUD(Permission permission) throws ValidateFailureException;
	
	/**
	 * 编辑单个权限
	 * @param permission
	 */
	public void edit(Permission permission);
	
	/**
	 * 根据名字删除
	 * @param permissionName
	 */
	public void deleteByName(String permissionName);
	
	/**
	 * 删除权限
	 * @param roleId
	 */
	public void delete(String permissionId);
	
	
	/**
	 * 查询所有 的权限
	 * @return
	 */
	public List<Permission> findPermissions();
	
	/**
	 * 获取权限个数
	 * @return
	 */
	public Long getCount();
	
	/**
	 * 创建权限
	 * @param role
	 * @return
	 */
	public Permission add(Permission permission) throws ValidateFailureException;

	/**
	 * 根据权限的增删修查来查询是否权限存在
	 * @param permission
	 * @return
	 */
	public Permission findByPermission(Permission permission);
	
	/**
	 * 所有所有权限
	 * @param pagination
	 * @return
	 */
	public Pagination<Permission> findPermissions(Pagination<Permission> pagination);
	
	/**
	 * 根据id删除权限
	 * 
	 * @param roleId
	 */
	public void deleteById(String permissionId);
	
	/**
	 * 根据id查找权限
	 * @param roleId
	 * @return
	 */
	public Permission findById(String permissionId);
	
	/**
	 * 根据名称查询权限
	 * @param roleName
	 * @return
	 */
	public Permission findByName(String permissionName);
	
	/**
	 * 根据名称查询权限列表
	 * @param roleName
	 * @return
	 */
	public List<Permission> findByNames(String permissionName);
	
	/**
	 * 根据权限的curd查询
	 * @param parentId
	 * @param crudValue
	 * @param crud
	 * @return
	 */
	public Permission findPermissionByCRUD(String parentId,String crud);
}
