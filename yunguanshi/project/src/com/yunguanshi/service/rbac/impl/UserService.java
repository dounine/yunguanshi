package com.yunguanshi.service.rbac.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yunguanshi.auth.PasswordHash;
import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.exception.InfoNotEditException;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.IUserService;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.StringUtil;
import com.yunguanshi.utils.hibernate.Pagination;

/**
 * 用户接口实现.
 * @author huanghuanlai
 *
 */
@Service
public class UserService implements IUserService{
	private static final Logger console = LoggerFactory.getLogger(UserService.class);
	@Resource
	BaseDao<User> userDao;

	public synchronized User add(User user) throws ValidateFailureException {
		//userNameValidate(user.getUsername());//用户名验证
		passwordValidate(user.getPassword());//密码验证
		User _user = findByName(user.getEmail());
		if(null!=_user) throw new ValidateFailureException("邮箱[ "+user.getEmail()+" ]已经注册过了。");
		try {
			user.setPassword(PasswordHash.createHash(user.getPassword()));//加密
		} catch (NoSuchAlgorithmException e) {
			console.info("密码加密异常");
		} catch (InvalidKeySpecException e) {
			console.info("密码加密异常");
		}
		return userDao.save(user);
	}
	
	public void userNameValidate(String username) throws ValidateFailureException{
		int un = StringUtil.isPassUserName(username);//判断用户名是否合法
		if(un==StringUtil.USERNAMENULL){
			throw new ValidateFailureException("用户名不能为空");
		}else if(un==StringUtil.USERNAMEFORMAT){
			throw new ValidateFailureException("用户名只能由(字母+数字)或者(3~8位中文字符)组成");
		}else if(un==StringUtil.USERNAMELENGTH){
			throw new ValidateFailureException("用户名长度在4~16位之间");
		}
	}
	
	public void passwordValidate(String password) throws ValidateFailureException{
		int un = StringUtil.isPassPassword(password);//判断密码是否合法
		if(un==StringUtil.USERNAMENULL){
			throw new ValidateFailureException("密码不能为空");
		}else if(un==StringUtil.USERNAMEFORMAT){
			throw new ValidateFailureException("密码由(字母+数字)组成");
		}else if(un==StringUtil.USERNAMELENGTH){
			throw new ValidateFailureException("密码长度在6~18位之间");
		}
	}

	public User findByHql(String hql, String perms) {
		return userDao.get(hql, perms);
	}
	
	public User findById(String userId){
		return userDao.get(User.class, userId);
	}

	public void updatePassword(String userId, String newPassword) {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(newPassword);
		userDao.update(user);
	}

	public User findByEmail(String email) {
		return userDao.get("from "+User.class.getName()+" as u where u.email = ?", email);
	}
	
	public User findByName(String username) {
		return userDao.get("from "+User.class.getName()+" as u where u.username = ?", username);
	}

	@SuppressWarnings("unchecked")
	public Set<String> findRoles(String username) {
		Set<String> set = new HashSet<String>();
		List<Object> objects = new ArrayList<Object>(1);
		objects.add(username);
		List<Object> list =  (List<Object>) userDao.findBySql("select u.roles from "+User.class.getName()+" u where u.username=?",objects);
		for(Object object : list){
			set.add(((Role)object).getResource());
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<String> findPermissions(String username) {
		Set<String> set = new HashSet<String>();
		List<Object> objects = new ArrayList<Object>(1);
		objects.add(username);
		List<Object> list =  (List<Object>) userDao.findBySql("select u.roles from "+User.class.getName()+" u where u.username=?",objects);
		for(Object object : list){
			Set<Permission> permissions = ((Role)object).getPermissions();
			for(Permission permission : permissions){
				set.add(permission.getResource());
			}
		}
		return set;
	}

	public void addRelationRoles(String userId, String... roleIds) {
		User user = findById(userId);
		Set<Role> roles = user.getRoles();//获取用户中的所有角色
		Role role = null;
		for(String roleId : roleIds){
			role = new Role();
			role.setRoleId(roleId);
			roles.add(role);
		}
	}

	public void deleteRelationRoles(String userId, String... roleIds) {
		User user = findById(userId);
		Set<Role> roles = user.getRoles();//获取用户中的所有角色
		Role role = null;
		for(String roleId : roleIds){
			role = new Role();
			role.setRoleId(roleId);
			roles.remove(role);
		}
	}

	@Override
	public List<User> findUsers() {
		return userDao.findByClass(User.class);
	}

	@Override
	public Pagination<User> findUsers(Pagination<User> pagination) {
		return userDao.findByClass(User.class,pagination,Order.desc("id"));
	}

	@Override
	public Long getCount() {
		return userDao.getClassCount(User.class);
	}

	@Override
	public User update(User user) throws InfoNotEditException, ValidateFailureException {
		User _user = userDao.findById(User.class, user.getUserId());
		if(user.getUsername().equals("admin")){
			throw new ValidateFailureException("网站管理员用户名不能修改");
		}
		if(_user!=null&&!_user.getUsername().equals(user.getUsername())){
			List<User> users = userDao.find("from "+User.class.getName()+" u where u.username=?", user.getUsername());
			if(users.size()>0){
				throw new ValidateFailureException("用户名已经存在");
			}
		}
		if(_user.equals(user)){
			throw new InfoNotEditException("对不起、您没有作出任何修改!");
		}
		if(user.getPassword()!=null&&!user.getPassword().trim().equals("")){
			try {
				user.setPassword(PasswordHash.createHash(user.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}else{
			user.setPassword(_user.getPassword());
		}
		return userDao.update(user);
	}

	@Override
	public void delete(String ids) throws ValidateFailureException {
		String[] _ids = ids.split(PropUtil.get("split"));
		for(String id : _ids){
			if(id.equals(findAdminId())){
				throw new ValidateFailureException("网站管理员不能删除");
			}
			User user = new User();
			user.setUserId(id);
			userDao.delete(user);
		}
	}

	@Override
	public String findAdminId() {
		List<User> user = userDao.findByHql("from "+User.class.getName()+" as u where u.username = ?", PropUtil.get("administrator"));
		if(user.size()==1){
			return user.get(0).getUserId();
		}
		return null;
	}

}
