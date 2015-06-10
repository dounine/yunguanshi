package com.yunguanshi.service.rbac;

import java.util.List;
import java.util.Set;

import com.yunguanshi.exception.InfoNotEditException;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.utils.hibernate.Pagination;

/**
 * 用户服务接口.
 * @author huanghuanlai
 *
 */
public interface IUserService {
	
	/**
	 * 创建用户
	 * @param user
	 */
	public User add(User user) throws ValidateFailureException;
	
	/**
	 * 删除用户
	 * @param ids
	 */
	public void delete(String ids) throws ValidateFailureException;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public User update(User user) throws InfoNotEditException,ValidateFailureException;
	
	/**
	 * 根据用户id查找用户信息
	 * @param userId
	 * @return
	 */
	public User findById(String userId);
	
	/**
	 * 查询所有用户信息
	 * @return
	 */
	public List<User> findUsers();
	
	/**
	 * 查询所有用户信息
	 * @return
	 */
	public Pagination<User> findUsers(Pagination<User> pagination);
	
	/**
	 * 修改密码  
	 * @param userId
	 * @param newPassword
	 */
    public void updatePassword(String userId, String newPassword);
    
    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void addRelationRoles(String userId, String... roleIds);
    
    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void deleteRelationRoles(String userId, String... roleIds);
    
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByName(String username) throws ValidateFailureException;
    
    public User findByEmail(String email);
    /**
     * 根据用户名查找其角色  
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);
    
    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    
    /**
     * 获取用户个数
     * @return
     */
    public Long getCount();
    
    /**
     * 用户名验证
     * @param username
     * @throws ValidateFailureException
     */
    public void userNameValidate(String username) throws ValidateFailureException;
    
    /**
     * 密码验证
     * @param password
     * @throws ValidateFailureException
     */
    public void passwordValidate(String password) throws ValidateFailureException;
    
    public String findAdminId();
}
