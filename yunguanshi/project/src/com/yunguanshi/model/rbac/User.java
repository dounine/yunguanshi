package com.yunguanshi.model.rbac;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.yunguanshi.annotation.FieldInfo;

/**
 * 用户类.
 * 
 * @author huanghuanlai
 * 
 */
@Entity
@Table(name = "ygs_user")
@GenericGenerator(name = "systemUUID", strategy = "uuid")
public class User extends UserConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8584493499619991072L;
	@Id
	@GeneratedValue(generator = "systemUUID")
	@FieldInfo(name = "用户编号")
	private String userId;
	//@Length(min = 4, max = 16, message = "用户长度在4~16位之间")
	//@Pattern(regexp = "^([a-zA-Z].+\\d*)|([\\u4e00-\\u9fa5]{3,8})$", message = "用户名只能由(字母+数字)或者(3~8位中文字符)组成")
	@FieldInfo(name = "用户登录名")
	private String username;
	@FieldInfo(name = "用户密码")
	private String password;
	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "请输入正确的邮箱地址")
	@FieldInfo(name = "用户邮箱")
	private String email;
	@FieldInfo(name = "是否锁定")
	private boolean locked = false;
	@NotEmpty(message = "性别不能为空")
	@FieldInfo(name = "性别(男，女，保密)")
	private String gender = "保密";
	@NotEmpty(message = "注册时间不能为空")
	@FieldInfo(name = "注册时间")
	private String birthday;
	@FieldInfo(name = "用户编码")
	private String userCode;
	@FieldInfo(name = "部门")
	@ManyToOne
	@JoinColumn(name="department_deptId")
	private Department department;

	@FieldInfo(name = "用户图标")
	private String icon;
	@FieldInfo(name = "部门名称")
	private Double progress;//空间进度
	private Long useZone;//已经使用
	private Long sumZone=10240l;//总量kb

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REMOVE,fetch=FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();// 多个角色
	@Transient
	private String[] rolearrays;
	
	@Transient
	private String deptarrays;

	public User() {
	}
	
	public User(String userId){
		this.userId = userId;
	}

	public String[] getRolearrays() {
		return rolearrays;
	}


	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Long getUseZone() {
		return useZone;
	}

	public void setUseZone(Long useZone) {
		this.useZone = useZone;
	}

	public Long getSumZone() {
		return sumZone;
	}

	public void setSumZone(Long sumZone) {
		this.sumZone = sumZone;
	}

	public void setRolearrays(String[] rolearrays) {
		this.rolearrays = rolearrays;
	}

	public User(String username, String password, String email, boolean locked,
			String gender, String birthday) {
		this.email = email;
		this.locked = locked;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
	}

	public String getDeptarrays() {
		return deptarrays;
	}

	public void setDeptarrays(String deptarrays) {
		this.deptarrays = deptarrays;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	public String toString() {
		return username;
	}

	public boolean equals(Object o) {
		boolean equals = false;
		if (!(o instanceof User)) {
			return false;
		} else {
			User user = (User) o;
			
			if (user.locked == locked && user.email.equals(email)
					&& user.getGender().equals(gender)
					&& user.password.equals("")
					&& user.getDeptId().equals(getDeptId())
					&& isSetEqual(user.getRoles(),roles)
					) {
				equals = true;
			}
		}
		return equals;
	}
	
	@SuppressWarnings("all")
	public boolean isSetEqual(Set set1, Set set2) {//判断两个set集成的值 是否相等
		
		if(set1 == null && set2 == null){
			return true; //Both are null
		}

		if (set1 == null || set2 == null || set1.size() != set2.size()
				|| set1.size() == 0 || set2.size() == 0) {
			return false;
		}

		Iterator ite1 = set1.iterator();
		Iterator ite2 = set2.iterator();

		boolean isFullEqual = true;
		
		while (ite2.hasNext()) {
			if (!set1.contains(ite2.next())) {
				isFullEqual = false;
			}
		}

		return isFullEqual;
	}
}
