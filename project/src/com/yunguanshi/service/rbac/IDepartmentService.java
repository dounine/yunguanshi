package com.yunguanshi.service.rbac;

import java.util.List;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Department;

public interface IDepartmentService {
	
	public Department findByName(String deptName) throws ValidateFailureException;

	public Department edit(Department department) throws ValidateFailureException;
	
	public void delete(String id);
	
	public Department add(Department department) throws ValidateFailureException ;
	
	public List<Department> finds();
	
}
