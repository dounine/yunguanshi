package com.yunguanshi.service.rbac;

import java.util.List;

import com.yunguanshi.model.extjs.JsonTreeNode;

public interface ITreeNode {
	
	public abstract JsonTreeNode buildJsonTreeNode(List<JsonTreeNode> list,
			String rootId);

	public abstract void createTreeChildren(List<JsonTreeNode> childrens,
			JsonTreeNode root);

	public abstract List<JsonTreeNode> getTreeList(String rootId,
			String tableName, String whereSql, JsonTreeNode template);
	
}