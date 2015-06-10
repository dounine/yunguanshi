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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.yunguanshi.annotation.FieldInfo;
import com.yunguanshi.annotation.NodeType;
import com.yunguanshi.constant.TreeNodeType;
import com.yunguanshi.model.extjs.TreeBaseEntity;
/**
 * 部门实体
 * @author zhangshuaipeng
 *
 */
@Entity
@Table(name="ygs_department")
@GenericGenerator(name="systemUUID",strategy="uuid")
public class Department extends TreeBaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3435642518760351220L;
	@Id
	@GeneratedValue(generator="systemUUID")
	@NodeType(type=TreeNodeType.ID)
	@FieldInfo(name="部门编号")
	private String deptId;
	@NodeType(type=TreeNodeType.TEXT)
	@FieldInfo(name="部门名称")
	private String deptName;
	@NodeType(type=TreeNodeType.CODE)
	@FieldInfo(name="部门编码")
	private String deptCode;
	@FieldInfo(name="上级部门")
	@NodeType(type=TreeNodeType.PARENT)
	@ManyToOne(optional=true)
	@JoinColumn(name="PARENT")//只能用大写
	private Department parent;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent",cascade={CascadeType.REMOVE})
	private Set<Department> children=new HashSet<Department>();
	
	@FieldInfo(name="描述信息")
	@NodeType(type=TreeNodeType.DESCRIPTION)
	private String description;
	
	public Department(){
		
	}
	
	public Department(String deptId){
		this.deptId=deptId;
	}

	public String getDeptId() {
		return deptId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Set<Department> getChildren() {
		return children;
	}
	public void setChildren(Set<Department> children) {
		this.children = children;
	}
	
}
