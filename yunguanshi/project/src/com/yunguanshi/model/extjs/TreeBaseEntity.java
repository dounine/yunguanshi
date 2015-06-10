package com.yunguanshi.model.extjs;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.yunguanshi.annotation.NodeType;
import com.yunguanshi.constant.TreeNodeType;

@MappedSuperclass
public class TreeBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1041034353044349151L;
	@NodeType(type = TreeNodeType.LAYER)
	private Integer layer;
	@NodeType(type = TreeNodeType.NODEINFO)
	private String nodeInfo;
	@NodeType(type = TreeNodeType.LEAF)
	private String nodeType;
	@NodeType(type = TreeNodeType.NODEINFOTYPE)
	private String nodeInfoType;
	@NodeType(type=TreeNodeType.RESADD)
	private boolean resAdd=false;//权限添加
	@NodeType(type=TreeNodeType.RESDELETE)
	private boolean resDelete=false;//权限删除
	@NodeType(type=TreeNodeType.RESUPDATE)
	private boolean resUpdate=false;//权限修改
	@NodeType(type=TreeNodeType.RESREAD)
	private boolean resRead=false;//权限
	private Integer orderIndex=0;

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public boolean isResAdd() {
		return resAdd;
	}

	public void setResAdd(boolean resAdd) {
		this.resAdd = resAdd;
	}

	public boolean isResDelete() {
		return resDelete;
	}

	public void setResDelete(boolean resDelete) {
		this.resDelete = resDelete;
	}

	public boolean isResUpdate() {
		return resUpdate;
	}

	public void setResUpdate(boolean resUpdate) {
		this.resUpdate = resUpdate;
	}

	public boolean isResRead() {
		return resRead;
	}

	public void setResRead(boolean resRead) {
		this.resRead = resRead;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeInfoType() {
		return nodeInfoType;
	}

	public void setNodeInfoType(String nodeInfoType) {
		this.nodeInfoType = nodeInfoType;
	}

}
