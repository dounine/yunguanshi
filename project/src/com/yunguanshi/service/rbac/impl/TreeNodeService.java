package com.yunguanshi.service.rbac.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunguanshi.constant.NodeTypeC;
import com.yunguanshi.constant.TreeNodeType;
import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.extjs.JsonTreeNode;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.service.rbac.ITreeNode;
import com.yunguanshi.utils.StringUtil;

@Service
@SuppressWarnings("all")
public class TreeNodeService implements ITreeNode {
	
	@Resource
	BaseDao<TreeNodeType> baseDao;

	/* (non-Javadoc)
	 * @see com.yunguanshi.service.impl.IPcServiceTemplate#buildJsonTreeNode(java.util.List, java.lang.String)
	 */
	@Override
	public JsonTreeNode buildJsonTreeNode(List<JsonTreeNode> list,String rootId){
		JsonTreeNode root = new JsonTreeNode();
		for(JsonTreeNode node:list){
			if(!(StringUtil.isNotEmpty(node.getParent()) && node.getId().equalsIgnoreCase(rootId))){
				root=node;
				list.remove(node);
				break;
			}
		}
		createTreeChildren(list, root);
		return root;
	}
	
	/* (non-Javadoc)
	 * @see com.yunguanshi.service.impl.IPcServiceTemplate#createTreeChildren(java.util.List, com.dounine.model.extjs.JsonTreeNode)
	 */
	@Override
	public void createTreeChildren(List<JsonTreeNode> childrens,JsonTreeNode root){
		String parentId=root.getId();
		for(int i=0;i<childrens.size();i++){
			JsonTreeNode node=childrens.get(i);
			if(StringUtil.isNotEmpty(node.getParent()) && node.getParent().equals(parentId)){
				root.getChildren().add(node);
				createTreeChildren(childrens, node);
			}
			if(i==childrens.size()-1){
				if(root.getChildren().size()<1){
					root.setLeaf(true);
				}
				return;
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.yunguanshi.service.impl.IPcServiceTemplate#getTreeList(java.lang.String, com.dounine.util.StringUtil, java.lang.String, com.dounine.model.extjs.JsonTreeNode)
	 */
	@Override
	public List<JsonTreeNode> getTreeList(String rootId,String tableName,String whereSql,JsonTreeNode template){
		List<JsonTreeNode> childrens = new ArrayList<JsonTreeNode>();
		StringBuilder sql = new StringBuilder("select ");
		sql.append("t."+template.getId()+",");
		sql.append("t."+template.getText()+",");
		sql.append("t."+template.getCode()+",");
		sql.append("t."+template.getNodeType()+",");
		sql.append("t."+template.getNodeInfo()+",");
		sql.append("t."+template.getNodeInfoType()+",");
		sql.append("t."+template.getParent()+",");
		sql.append("t."+template.getDescription()+",");
		sql.append("t.orderIndex ");
		if(StringUtil.isNotEmpty(template.getIcon())){
			sql.append("t."+template.getIcon()+",");
		}
		if(StringUtil.isNotEmpty(template.getCls())){
			sql.append("t."+template.getCls());
		}
		sql.append(" from "+tableName+" t where 1=1");
		if(StringUtil.isNotEmpty(whereSql)){
			sql.append(whereSql);
		}
		sql.append(" order by t."+template.getId());
		List<?> list = baseDao.findBySql(sql.toString());
		for(int i =0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			JsonTreeNode node = new JsonTreeNode();
			node.setId((String)obj[0]);
			node.setText((String)obj[1]);
			node.setCode((String)obj[2]);
			if(NodeTypeC.LEAF.equalsIgnoreCase((String)obj[3])){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			node.setNodeInfo((String)obj[4]);
			node.setNodeInfoType((String)obj[5]);
			node.setParent((String)obj[6]);
			node.setDescription((String)obj[7]);
			//node.setOrderIndex(Integer.parseInt(obj[8].toString()));
			childrens.add(node);
		}
		return childrens;
	}
	
}
