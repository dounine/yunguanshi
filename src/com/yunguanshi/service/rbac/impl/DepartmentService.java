package com.yunguanshi.service.rbac.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.service.rbac.IDepartmentService;

@Service
@SuppressWarnings("all")
public class DepartmentService implements IDepartmentService {

	@Resource
	BaseDao<Department> baseDao;

	@Override
	public Department findByName(String deptName) throws ValidateFailureException {
		List<Object> params = new ArrayList<Object>(1);
		params.add(deptName);
		List<Department> departments = baseDao.findByHql(
				"from " + Department.class.getName()
						+ " d where d.deptName=?", deptName);
		if (departments.size()>0) {
			return departments.get(0);
		}
		return null;
	}

	@Override
	public void delete(String id) {
		Department department = new Department(id);
		baseDao.deleteByObj(department);
	}

	@Override
	public Department add(Department department) throws ValidateFailureException {
		if(null!=findByName(department.getDeptName())){
			throw new ValidateFailureException("用户组[ "+department.getDeptName()+" ]已存在");
		}
		return (Department) baseDao.saveByObj(department);
	}

	@Override
	public Department edit(Department department) throws ValidateFailureException {
		Department _Department = baseDao.findById(Department.class, department.getDeptId());
		if(null!=_Department&&!_Department.getDeptName().trim().equals(department.getDeptName().trim())&&_Department.getDeptId().equals(department.getDeptId())){
			List<Department> departments = baseDao.find("from "+Department.class.getName()+" u where u.deptName=?", department.getDeptName());
			if(departments.size()>0){
				throw new ValidateFailureException("数据[ "+department.getDeptName()+" ]已经存在");
			}
		}
		return (Department) baseDao.updateByObj(department);
	}
	
	@Override
	public List<Department> finds() {
		return baseDao.findByClass(Department.class);
	}
}
