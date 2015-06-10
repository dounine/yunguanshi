package com.yunguanshi.init;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.service.rbac.IDepartmentService;

public class DepartmentsData implements IData{

	IDepartmentService departmentService;
	public DepartmentsData(IDepartmentService departmentService){
		this.departmentService = departmentService;
	}
	
	public void insert() throws ValidateFailureException{
		Department department = new Department();
		department.setLayer(0);
		department.setNodeType("root");
		department.setDeptCode("root");
		department.setDeptName("root");
		department.setNodeInfoType("root");
		departmentService.add(department);
		
		department = new Department();
		department.setLayer(1);
		department.setNodeType("GENERAL");
		department.setDeptCode("VIP会员");
		department.setDeptName("VIP会员");
		department.setParent(departmentService.findByName("root"));
		departmentService.add(department);
		
		department = new Department();
		department.setLayer(1);
		department.setNodeType("管理员");
		department.setDeptCode("管理员");
		department.setDeptName("管理员");
		department.setParent(departmentService.findByName("root"));
		departmentService.add(department);
		
		department = new Department();
		department.setLayer(1);
		department.setNodeType("GENERAL");
		department.setDeptCode("asdf");
		department.setDeptName("普通会员");
		department.setParent(departmentService.findByName("root"));
		departmentService.add(department);
	}
}
