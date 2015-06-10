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

import org.hibernate.annotations.GenericGenerator;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.annotation.NodeType;
import com.yunguanshi.constant.TreeNodeType;
import com.yunguanshi.model.extjs.TreeBaseEntity;
/**
 * 权限类.
 * @author huanghuanlai
 *
 */
@Entity
@Table(name="ygs_permission")
@GenericGenerator(name="systemUUID",strategy="uuid")
public class Permission extends TreeBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2903110989530229865L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator="systemUUID")
	@NodeType(type=TreeNodeType.ID)
	@FieldInfo(name="权限编号")
	private String permissionId;
	@NodeType(type=TreeNodeType.CODE)
	private String permissionCode;
	@NodeType(type=TreeNodeType.TEXT)
	private String permissionName;
	@FieldInfo(name="权限标识符")
	private String resource;
	@NodeType(type=TreeNodeType.PARENT)
	@ManyToOne(optional=true)
	@JoinColumn(name="PARENT")
	private Permission parent;
	@FieldInfo(name="描述")
	@NodeType(type=TreeNodeType.DESCRIPTION)
	private String description;
	@FieldInfo(name="是否可用,如果不可用将不会添加给用户")
	private Boolean locked = Boolean.FALSE;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent",cascade={CascadeType.ALL})
	private Set<Permission> children=new HashSet<Permission>();
	
	
	@FieldInfo(name="角色")
	@ManyToMany
	private Set<Role> roles = new HashSet<Role>();

	public Permission() {
	}
	

	public Permission(String permissionId){
		this.permissionId = permissionId;
	}

	public Permission(String resource, String description, Boolean locked) {
		this.resource = resource;
		this.description = description;
		this.locked = locked;
	}


	public Boolean getLocked() {
		return locked;
	}


	public void setLocked(Boolean locked) {
		this.locked = locked;
	}


	public String getPermissionId() {
		return permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	public Set<Permission> getChildren() {
		return children;
	}

	public void setChildren(Set<Permission> children) {
		this.children = children;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Permission permission = (Permission) o;

		if (permissionId != null ? !permissionId.equals(permission.permissionId) : permission.permissionId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return permissionId != null ? permissionId.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + permissionId + ", permission='" + resource + '\''
				+ ", description='" + description + '\'' + ", available="
				+ locked + '}';
	}

}
