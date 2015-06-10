package com.yunguanshi.controller.rbac;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.extjs.JsonTreeNode;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.IDepartmentService;
import com.yunguanshi.service.rbac.ITreeNode;
import com.yunguanshi.service.rbac.IUserService;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.ModelUtil;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.StringUtil;

@Controller
@RequestMapping("department")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class DepartmentController {
	
	private static final Logger console = LoggerFactory.getLogger(DepartmentController.class);
	
	@Resource
	private ITreeNode pcServiceTemplate;
	
	@Resource
	private IDepartmentService departmentService;
	
	@Resource
	private IUserService userService;
	
	private JsonBuilder jsonBuilder = new JsonBuilder();

	@RequestMapping(value="read.html")
	@ResponseBody
	public String read(Department department,String node,String whereSql,String excludes){
		console.info("读取部门树形");
		String rootId = null;
		if(!StringUtil.isNotEmpty(node)){//如果node为空则加载根节点
			try {
				rootId = departmentService.findByName(PropUtil.get("rootNode")).getDeptId();
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
		}else{
			rootId = node;
		}
		String strDate = null;
		if(department!=null){
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Department.class);
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(node,Department.class.getAnnotation(Table.class).name(), whereSql, template);
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			if(rootId.equalsIgnoreCase(PropUtil.get("rootNode"))){
				strDate=jsonBuilder.buildList(root.getChildren(), excludes);
			}else{
				List<JsonTreeNode> alist = new ArrayList<JsonTreeNode>();
				alist.add(root);
				strDate=jsonBuilder.buildList(root.getChildren(), excludes);	
			}
			return strDate;
		}
		return jsonBuilder.returnFailureJson("无数据", null);
	}
	
	@RequestMapping(value="readarray.html")
	@ResponseBody
	public String readarray(){
		console.info("读取department数组");
		List<Department> departments = new ArrayList<Department>();
		departments = departmentService.finds();
		StringBuilder sb = new StringBuilder("[");
		int i =0;
		for(Department department : departments){
			if(department.getDeptName().equals(PropUtil.get("rootNode"))){
				continue;
			}
			String _s = "{\"deptId\":\""+department.getDeptId()+"\",\"deptName\":\""+department.getDeptName()+"\"}";
			if(i<departments.size()-1){
				sb.append(_s+",");
			}else{
				sb.append(_s);
			}
			i++;
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 根据用户id查找用户所属部门
	 * @param user
	 * @return
	 */
	@RequestMapping(value="readbyuid.html")
	@ResponseBody
	public String readbyuid(String userId,String excludes){
		
		if(!StringUtil.isNotEmpty(userId)){
			return jsonBuilder.returnFailureJson("用户不能为空", null);
		}else {
			User _user = userService.findById(userId);
			Department department = _user.getDepartment();
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Department.class);
			String rootId;
			try {
				rootId = departmentService.findByName(PropUtil.get("rootNode")).getDeptId();
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(rootId,Department.class.getAnnotation(Table.class).name(), null, template);
			for(JsonTreeNode jsonTreeNode : lists){
				if(jsonTreeNode.getId().equals(department.getDeptId())){//判断如果用户的角色和总角色相等则设置成选中状态
					jsonTreeNode.setChecked(true);
					break;
				}
			}
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			return jsonBuilder.buildList(root.getChildren(), excludes);
		}
	}
	
	@RequestMapping(value="delete.html")
	@ResponseBody
	public String delete(String id){
		if(StringUtil.isNotEmpty(id)){
			departmentService.delete(id);
			return jsonBuilder.returnSuccessJson("删除成功", null);
		}
		return jsonBuilder.returnFailureJson("删除失败", null);
	}
	
	@RequestMapping(value="add.html")
	@ResponseBody
	public String add(Department department){
		console.info("添加成功");
		Department _department=null;
		try {
			_department = departmentService.add(department);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("添加成功", _department);
	}
	
	@RequestMapping(value="edit.html")
	@ResponseBody
	public String edit(Department department){
		console.debug("修改成功");
		Department _dDepartment=null;
		try {
			_dDepartment = departmentService.edit(department);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("修改成功", _dDepartment);
	}
	
	@RequestMapping(value="root.html")
	@ResponseBody
	public String getRootId(){
		Department department;
		try {
			department = departmentService.findByName("root");
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("获取成功", department.getDeptId());
	}
}
