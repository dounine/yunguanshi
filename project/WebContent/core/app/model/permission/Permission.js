Ext.define('CC.model.permission.Permission', {
			extend : Ext.data.Model,
			listeners:{
				exception:function(proxy,response,operation,options){
					Wb.except(response.responseText);
				}
			},
			fields:factory.Model.getFields('extjs_JsonTreeNode','checked')//获取模型字段
		});