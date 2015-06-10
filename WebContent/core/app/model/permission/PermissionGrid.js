Ext.define('CC.model.permission.PermissionGrid', {
			extend : Ext.data.Model,
			fields:factory.Model.getFields('extjs_JsonTreeNode','checked,code,href,parentId,disabled,bigIcon,orderIndex,nodeInfoType,nodeType,nodeInfo,description,icon,hrefTarget')//获取模型字段
		});