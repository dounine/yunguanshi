package com.yunguanshi.service.rbac;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Role;

/**
 * 角色服务接口.
 * @author huanghuanlai
 *
 */
public interface IRoleService {
	
	/**
	 * 编辑角色
	 * @param role
	 * @return
	 */
	public Role edit(Role role) throws ValidateFailureException;
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void delete(String roleId);
	
	/**
	 * 获取角色个数
	 * @return
	 */
	public Long getCount();
	
	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
	public Role add(Role role) throws ValidateFailureException ;

	/**
	 * 根据id删除角色
	 * 
	 * @param roleId
	 */
	public void deleteById(String roleId);
	
	/**
	 * 根据id查找角色
	 * @param roleId
	 * @return
	 */
	public Role findById(String roleId);
	
	/**
	 * 根据名称查询角色名称
	 * @param roleName
	 * @return
	 */
	public Role findByName(String roleName) throws ValidateFailureException;

	/**
	 * 添加角色-权限之间关系
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	public void addRelationPermissions(String roleId, String... permissionIds);
	
	/**
	 * 根据名字来查询id
	 * @param roleName
	 * @return
	 */
	public String findByNameId(String roleName);

	/**
	 * 移除角色-权限之间关系
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	public void deleteRelationPermissions(String roleId,
			String... permissionIds);
}
