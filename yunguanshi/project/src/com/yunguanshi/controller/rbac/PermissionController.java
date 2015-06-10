package com.yunguanshi.controller.rbac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtilsBean;
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
import com.yunguanshi.service.rbac.IPermissionService;
import com.yunguanshi.service.rbac.IRoleService;
import com.yunguanshi.service.rbac.ITreeNode;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.ModelUtil;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.StringUtil;

@Controller
@RequestMapping("permission")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class PermissionController {
	
	private static final Logger console = LoggerFactory.getLogger(PermissionController.class);
	
	@Resource
	private ITreeNode pcServiceTemplate;
	
	@Resource
	private IPermissionService permissionService;
	
	@Resource
	private IRoleService roleService;
	
	private JsonBuilder jsonBuilder = new JsonBuilder();

	@RequestMapping(value="read.html")
	@ResponseBody
	public String read(Permission permission,String node,String whereSql,String excludes){
		console.info("读取权限树形");
		String rootId = null;
		if(!StringUtil.isNotEmpty(node)){//如果node为空则加载根节点
			rootId = permissionService.findByName(PropUtil.get("rootNode")).getPermissionId();
		}else{
			rootId = node;
		}
		String strDate = null;
		if(permission!=null){
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Permission.class);
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(node,Permission.class.getAnnotation(Table.class).name(), whereSql, template);
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			strDate=jsonBuilder.buildList(root.getChildren(), excludes);	
			return strDate;
		}
		return jsonBuilder.returnFailureJson("无数据", null);
	}
	
	@RequestMapping(value="readgrid.html")
	@ResponseBody
	public String readgrid(Role role,String node,String whereSql,String excludes) throws ValidateFailureException{
		console.info("方法：readgrid.html");
		String rootId = null;
		if(!StringUtil.isNotEmpty(node)){//如果node为空则加载根节点
			rootId = permissionService.findByName(PropUtil.get("rootNode")).getPermissionId();
		}else{
			rootId = node;
		}
		String strDate = null;
		if(role!=null){
			Role _role = null;
			if(role!=null&&role.getRoleId()!=null){
				_role = roleService.findById(role.getRoleId());
			}
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Permission.class);
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(node,Permission.class.getAnnotation(Table.class).name(), whereSql, template);
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			List<JsonTreeNode> sortJsonTreeNodes = new ArrayList<JsonTreeNode>();
			Map<String, JsonTreeNode> jt = new HashMap<String, JsonTreeNode>();
			for(JsonTreeNode jsonTreeNode : root.getChildren()){
				jt.put(jsonTreeNode.getText(), jsonTreeNode);
			}
			for (JsonTreeNode jsonTreeNode : jt.values()) {//从新组装一个符合extjs显示的列表
				if(_role!=null){
					for(Permission permission2 : _role.getPermissions()){
						if(permission2.getPermissionName().equals(jsonTreeNode.getText())){
							if(permission2.isResAdd()){
								jsonTreeNode.setResAdd(true);
							}
							if(permission2.isResDelete()){
								jsonTreeNode.setResDelete(true);
							}
							if(permission2.isResUpdate()){
								jsonTreeNode.setResUpdate(true);
							}
							if(permission2.isResRead()){
								jsonTreeNode.setResRead(true);
							}
						}
					}
				}
				sortJsonTreeNodes.add(jsonTreeNode);
			}
			strDate=jsonBuilder.buildList(sortJsonTreeNodes, excludes);	
			return strDate;
		}
		return jsonBuilder.returnFailureJson("无数据", null);
	}
	
	@RequestMapping(value="readgrids.html")
	@ResponseBody
	public String readgrids(Role role,String node,String whereSql,String excludes) throws ValidateFailureException{
		console.info("方法：readgrids.html");
		String rootId = null;
		if(!StringUtil.isNotEmpty(node)){//如果node为空则加载根节点
			rootId = permissionService.findByName(PropUtil.get("rootNode")).getPermissionId();
		}else{
			rootId = node;
		}
		String strDate = null;
		if(role!=null){
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Permission.class);
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(node,Permission.class.getAnnotation(Table.class).name(), whereSql, template);
			JsonTreeNode root = pcServiceTemplate.buildJsonTreeNode(lists,rootId);
			List<JsonTreeNode> sortJsonTreeNodes = new ArrayList<JsonTreeNode>();
			Map<String, JsonTreeNode> jt = new HashMap<String, JsonTreeNode>();
			for(JsonTreeNode jsonTreeNode : root.getChildren()){
				jt.put(jsonTreeNode.getText(), jsonTreeNode);
			}
			List<Permission> permissions = permissionService.findPermissions();
			console.info("长度："+permissions.size());
			for (JsonTreeNode jsonTreeNode : jt.values()) {//从新组装一个符合extjs显示的列表
				if(permissions.size()>0){
					for(Permission permission2 : permissions){
						if(permission2.getPermissionName().equals(jsonTreeNode.getText())){
							if(permission2.isResAdd()){
								if(!permission2.getLocked()){
									jsonTreeNode.setResAdd(true);
								}
							}
							if(permission2.isResDelete()){
								if(!permission2.getLocked()){
									jsonTreeNode.setResDelete(true);
								}
							}
							if(permission2.isResUpdate()){
								if(!permission2.getLocked()){
									jsonTreeNode.setResUpdate(true);
								}
							}
							if(permission2.isResRead()){
								if(!permission2.getLocked()){
									jsonTreeNode.setResRead(true);
								}
							}
						}
					}
				}
				sortJsonTreeNodes.add(jsonTreeNode);
			}
			strDate=jsonBuilder.buildList(sortJsonTreeNodes, excludes);	
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
	public String readbyuid(String roleId,String excludes){
		if(!StringUtil.isNotEmpty(roleId)){
			return jsonBuilder.returnFailureJson("角色不能为空", null);
		}else {
			Role _role = roleService.findById(roleId);
			Set<Permission> rolePermissions = _role.getPermissions();
			JsonTreeNode template = ModelUtil.getJSONTreeNodeTemplate(Permission.class);
			String rootId;
			try {
				rootId = roleService.findByName(PropUtil.get("rootNode")).getRoleId();
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
			List<JsonTreeNode> lists = pcServiceTemplate.getTreeList(rootId,Permission.class.getAnnotation(Table.class).name(), null, template);
			for(JsonTreeNode jsonTreeNode : lists){
				for(Permission permission : rolePermissions){
					if(jsonTreeNode.getId().equals(permission.getPermissionId())){//判断如果角色的权限和选中权限相等则设置成选中状态
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
	public String delete(String name){
		if(StringUtil.isNotEmpty(name)){
			permissionService.deleteByName(name);
			return jsonBuilder.returnSuccessJson("删除成功", null);
		}
		return jsonBuilder.returnFailureJson("删除失败", null);
	}
	
	@RequestMapping(value="add.html")
	@ResponseBody
	public String add(Permission permission){
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
		Permission m1 = new Permission();
		Permission m2 = new Permission();
		Permission m3 = new Permission();
		Permission m4 = new Permission();
		try {
			propertyUtilsBean.copyProperties(m1, permission);
			propertyUtilsBean.copyProperties(m2, permission);
			propertyUtilsBean.copyProperties(m3, permission);
			propertyUtilsBean.copyProperties(m4, permission);
		} catch (Exception e1) {
			return jsonBuilder.returnFailureJson("对象复制错误", null);
		}
		try {
			m1.setResAdd(true);
			m1.setResDelete(false);
			m1.setResUpdate(false);
			m1.setResRead(false);
			m1.setLocked(!permission.isResAdd());
			permissionService.add(m1);
			
			m2.setResDelete(true);
			m2.setResAdd(false);
			m2.setResUpdate(false);
			m2.setResRead(false);
			m2.setLocked(!permission.isResDelete());
			permissionService.add(m2);
			
			m3.setResUpdate(true);
			m3.setResAdd(false);
			m3.setResDelete(false);
			m3.setResRead(false);
			m3.setLocked(!permission.isResUpdate());
			permissionService.add(m3);
			
			m4.setResRead(true);
			m4.setResAdd(false);
			m4.setResDelete(false);
			m4.setResUpdate(false);
			m4.setLocked(!permission.isResRead());
			permissionService.add(m4);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("添加成功", m4);
	}
	
	@RequestMapping(value="edit.html")
	@ResponseBody
	public String edit(Permission permission){
		console.debug("修改成功");
		Permission _permission;
		try {
			_permission = permissionService.editCRUD(permission);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(), null);
		}
		return jsonBuilder.returnSuccessJson("修改成功", _permission);
	}
	
	@RequestMapping(value="root.html")
	@ResponseBody
	public String getRootId(){
		Permission permission = permissionService.findByName("root");
		if(permission!=null){
			return jsonBuilder.returnSuccessJson("获取成功", permission.getPermissionId());
		}
		return jsonBuilder.returnFailureJson("获取失败", null);
	}
	
}
