Ext.define('CC.model.user.User', {
			extend : Ext.data.Model,
			fields:factory.Model.getFields('rbac_User')//获取模型字段
		});