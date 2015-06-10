package com.yunguanshi.model.rbac;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.annotation.NodeType;
import com.yunguanshi.constant.TreeNodeType;
import com.yunguanshi.model.extjs.TreeBaseEntity;

/**
 * 角色类.
 * @author huanghuanlai
 *
 */
@Entity
@Table(name="ygs_role")
@GenericGenerator(name="systemUUID",strategy="uuid")
public class Role extends TreeBaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7583781021567891833L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator="systemUUID")
	@NodeType(type=TreeNodeType.ID)
	@FieldInfo(name="角色编号")
	private String roleId;
	@NodeType(type=TreeNodeType.CODE)
	private String roleCode;
	@NodeType(type=TreeNodeType.TEXT)
	private String roleName;
	@FieldInfo(name="角色标识 程序中判断使用,如admin")
	private String resource;
	@FieldInfo(name="描述")
	@NodeType(type=TreeNodeType.DESCRIPTION)
	private String description;
	@FieldInfo(name="是否可用,如果不可用将不会添加给用户")
	private Boolean available = Boolean.FALSE;
	@FieldInfo(name="用户列表")
	@ManyToMany(mappedBy="roles",cascade=CascadeType.ALL)
	private Set<User> users = new HashSet<User>();
	@FieldInfo(name="上级部门")
	@NodeType(type=TreeNodeType.PARENT)
	@ManyToOne(optional=true)
	@JoinColumn(name="PARENT")
	private Role parent;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent",cascade={CascadeType.REMOVE})
	private Set<Role> children=new HashSet<Role>();
	
	
	@ManyToMany
	private Set<Permission> permissions = new HashSet<Permission>();
	
	@Transient
	private String[] permissionarrays;//权限列表

	public Role() {

	}
	
	public Role(String roleId){
		this.roleId = roleId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public Set<Role> getChildren() {
		return children;
	}

	public void setChildren(Set<Role> children) {
		this.children = children;
	}
	
	public String[] getPermissionarrays() {
		return permissionarrays;
	}

	public void setPermissionarrays(String[] permissionarrays) {
		this.permissionarrays = permissionarrays;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

}
