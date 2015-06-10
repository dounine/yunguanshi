package com.yunguanshi.controller.rbac;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.extjs.JsonTreeNode;
import com.yunguanshi.model.rbac.Permission;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.ITreeNode;
import com.yunguanshi.service.rbac.IPermissionService;
import com.yunguanshi.service.rbac.IRoleService;
import com.yunguanshi.service.rbac.IUserService;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.ModelUtil;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.StringUtil;

@Controller
@RequestMapping("role")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class RoleController {
	
	private static final Logger console = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	ITreeNode pcServiceTemplate;
	
	@Resource
	IUserService userService;
	
	@Resource
	IRoleService roleService;
	
	@Resource
	IPermissionService permissionService;
	
	private JsonBuilder jsonBuilder = new JsonBuilder();

	@RequestMapping(value="read.html")
	@ResponseBody
	public String read(Role role,String node,String whereSql,String excludes){
		console.info("读取角色树形");
		String rootId = null;
		if(!StringUtil.isNotEmpty(node)){//如果node为空则加载根节点
			try {
				rootId = roleService.findByName(PropUtil.get("rootNode")).getRoleId();
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
		}else{
			rootId = node;
		}
		String strDate = null;
		if(role!=null){
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Role.class);
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(rootId,Role.class.getAnnotation(Table.class).name(), whereSql, template);
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			strDate=jsonBuilder.buildList(root.getChildren(), excludes);	
			return strDate;
		}
		return jsonBuilder.returnFailureJson("无数据", null);
	}
	
	/**
	 * 根据用户id查找用户角色列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="readbyuid.html")
	@ResponseBody
	public String readbyuid(String userId,String excludes){
		console.info("进来了"+userId);
		if(!StringUtil.isNotEmpty(userId)){
			return jsonBuilder.returnFailureJson("用户不能为空", null);
		}else {
			User _user = userService.findById(userId);
			Set<Role> userRoles = _user.getRoles();
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Role.class);
			String rootId;
			try {
				rootId = roleService.findByName(PropUtil.get("rootNode")).getRoleId();
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(rootId,Role.class.getAnnotation(Table.class).name(), null, template);
			for(JsonTreeNode jsonTreeNode : lists){
				for(Role role : userRoles){
					if(jsonTreeNode.getId().equals(role.getRoleId())){//判断如果用户的角色和总角色相等则设置成选中状态
						jsonTreeNode.setChecked(true);
						break;
					}
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
			roleService.delete(id);
			return jsonBuilder.returnSuccessJson("删除成功", null);
		}
		return jsonBuilder.returnFailureJson("删除失败", null);
	}
	
	@RequestMapping(value="add.html")
	@ResponseBody
	public String add(HttpServletRequest request,Role role,String excludes) throws IOException{
		Object[] objects = jsonBuilder.requestToEntity(request,JsonTreeNode.class,excludes);
		console.info("读取json对象的长度为："+objects.length);
		Set<Permission> permissions = new HashSet<Permission>();
		for(Object object : objects){
			JsonTreeNode _json = (JsonTreeNode) object;
			Permission curdPermission = null;
			if(!_json.getId().equalsIgnoreCase(PropUtil.get("rootNode"))){
				if(_json.isResAdd()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resAdd");
					permissions.add(curdPermission);
				}
				if(_json.isResDelete()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resDelete");
					permissions.add(curdPermission);
				}
				if(_json.isResUpdate()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resUpdate");
					permissions.add(curdPermission);
				}
				if(_json.isResRead()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resRead");
					permissions.add(curdPermission);
				}
			}
		}
		role.setPermissions(permissions);
		if(role!=null&&!StringUtil.isNotEmpty(role.getParent())){
			try {
				role.setParent(roleService.findByName(PropUtil.get("rootNode")));
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson("异常", null);
			}
		}
		try {
			role = roleService.add(role);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		role.setParent(null);
		role.setChildren(null);
		role.setPermissions(null);
		return jsonBuilder.returnSuccessJson("成功", role);
	}
	
	@RequestMapping(value="update.html")
	@ResponseBody
	public String update(HttpServletRequest request,Role role,String excludes) throws IOException{
		Object[] objects = jsonBuilder.requestToEntity(request,JsonTreeNode.class,excludes);
		console.info("读取json对象的长度为："+objects.length);
		Set<Permission> permissions = new HashSet<Permission>();
		for(Object object : objects){
			JsonTreeNode _json = (JsonTreeNode) object;
			Permission curdPermission = null;
			if(!_json.getId().equalsIgnoreCase(PropUtil.get("rootNode"))){
				if(_json.isResAdd()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resAdd");
					permissions.add(curdPermission);
				}
				if(_json.isResDelete()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resDelete");
					permissions.add(curdPermission);
				}
				if(_json.isResUpdate()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resUpdate");
					permissions.add(curdPermission);
				}
				if(_json.isResRead()){
					curdPermission = permissionService.findPermissionByCRUD(_json.getText(), "resRead");
					permissions.add(curdPermission);
				}
			}
		}
		console.info("权限长度："+permissions.size());
		if(role.getParent()==null){
			try {
				role.setParent(roleService.findByName(PropUtil.get("rootNode")));
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson("异常", null);
			}
		}
		try {
			role.setPermissions(null);
			role = roleService.edit(role);
			role.setPermissions(permissions);
			role = roleService.edit(role);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		role.setParent(null);
		role.setChildren(null);
		role.setPermissions(null);
		return jsonBuilder.returnSuccessJson("成功", role);
	}
	
	@RequestMapping(value="edit.html")
	@ResponseBody
	public String edit(Role role){
		console.debug("修改成功");
		Role _role;
		try {
			_role = roleService.edit(role);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("修改成功", _role);
	}
	
	@RequestMapping(value="root.html")
	@ResponseBody
	public String getRootId(){
		Role role;
		try {
			role = roleService.findByName("root");
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("获取成功", role.getRoleId());
	}
	
}
