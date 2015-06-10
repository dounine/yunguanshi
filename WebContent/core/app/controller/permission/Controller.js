Ext.define('CC.controller.permission.Controller',{
	extend:Ext.app.Controller,
	ctr:{},
	views : [
		'permission.tree',
		'permission.grid'
	],
	stores:[
		'permission.PermissionsStore',
		'permission.PermissionsGridStore'
	],
	models:[
		'permission.Permission'
	],
	isload:false,
	deptBtnCtr:function(){
		var self = this;
		var panelName = 'permissionpanel';
		factory.Url.pushReadUrl('permission/readgrids.html');
		var deptBtnCtr = {
			'permissionpanel button[ref=add]':{
				click:function(btn){
					var tree = btn.up(panelName).down('permissgrid');
					var permissioninfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					permissioninfobtn.setText('添加');
					var panel = permissioninfobtn.ownerCt.ownerCt.show();//获取保存按钮的父窗口
					var form = panel.up('form').form;//请空表单
					form.reset();
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(false);
					}
 					Ext.apply(form,{type:'add'});
					form.url='permission/add.html';//为编辑表单填充url地址 
				}
			},
			'permissionpanel button[ref=edit]':{
				click:function(btn){
					var permissioninfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					permissioninfobtn.setText('编辑');
					var panel = permissioninfobtn.ownerCt.ownerCt.show();//获取保存按钮的父窗口
					var form = panel.up('form').form;//清空表单
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(false);
					}
					form.url='permission/edit.html';//为编辑表单填充url地址 
					Ext.apply(form,{type:'edit'});
				}
			},
			'permissionpanel button[ref=delete]':{
				click:function(btn){
					var permissioninfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					permissioninfobtn.setText('编辑');
					var panel = permissioninfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					var tree = btn.up(panelName).down('permissgrid');
					var node = tree.getSelectionModel().getLastSelected();
					Ext.Msg.confirm('提示','确定要删除这条数据嘛',function(info){
						if(info=='yes'){
							Ext.Ajax.request({
								url:'permission/delete.html',
								method:'POST',
								params:{'name':node.get('text')},
								success:function(response){
									node.remove();
									Ext.example.msg('提示','删除成功');
									panel.up('form').form.reset();//请空表单
								}
							});
						}
					});
				}
			},
			'permissionpanel button[ref=save]':{
				click:function(btn){
					var form = btn.up('form').form;
					var tree = btn.up(panelName).down('permissgrid');
					var editButton = btn.up(panelName).down('button[ref=edit]');
					var deleteButton = btn.up(panelName).down('button[ref=delete]');
					var node = tree.getSelectionModel().getLastSelected();
					var values = form.getValues();
					var permissioninfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					var panel = permissioninfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					if(node==null){
						node = tree.getRootNode();
					}
					var params = {
						permissionName:values.text,
						permissionCode:values.code
					};
					if(form.type=='add'){
						Ext.apply(params,{'parent.permissionId':node.getId(),layer:node.getDepth()+1});
					}else if(form.type=='edit'){
						Ext.apply(params,{permissionId:node.getId(),layer:node.getDepth(),'parent.permissionId':node.get('parent')});
					}
					form.doAction('submit',{
									method:'POST',
									waitTitle: "请稍候",
									params : params,
									waitMsg: '正在保存数据...', 
									success:function(response,result){
										if(form.type=='add'){
											node.set('leaf',false);
											var m = node.appendChild({
												layer:node.getDepth()+1,
												text:result.result.model.permissionName,
												id:result.result.model.permissionId,
												parent:result.result.model.parent.permissionId,
												leaf:true
												});
											node.expand();
											tree.selectPath(m.getPath());
											tree.fireEvent('itemclick',tree.getView(),m);
										}else{
											node.set('text',result.result.model.permissionName);
											node.commit();
										}
										/*editButton.setDisabled(true);
										deleteButton.setDisabled(true);*/
										panel.hide();
										Ext.example.msg('提示',result.result.info);
									},
									failure:function(response,result){
										Ext.Msg.alert('提示',result.result.info);
									}
								});
				}
			},
			'permissionpanel button[ref=refresh]':{
				click:function(btn){
					var tree = btn.up(panelName).down('permissgrid');
					tree.getStore().reload();
					var editButton = btn.up(panelName).down('button[ref=edit]');
					var deleteButton = btn.up(panelName).down('button[ref=delete]');
					var form = btn.up(panelName).down('form').form;
					form.reset();
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(true);
					}
					editButton.setDisabled(true);
					deleteButton.setDisabled(true);
				}
			},
			'permissgrid':{
				itemclick:function(tree, record, item, index, e, eOpts){
					var deptTree = tree.ownerCt;
					var deleteBtn = deptTree.up('basepanel').down('button[ref=delete]');
					var editBtn = deptTree.up('basepanel').down('button[ref=edit]');
					var form;
					editBtn.setDisabled(false);
					deleteBtn.setDisabled(false);
					var tabs = Ext.getCmp('tabs');
					var first = tabs.getComponent('functionTab4');
					if(null==first){
						tabs.add(Ext.create('CC.view.permission.edit',{}));
						var ii = tabs.setActiveTab(0);
						form = ii.down('form');
					}else{
						form = first.down('form');
					}
					form.loadRecord(record);
					var permissioninfobtn = tree.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					var panel = permissioninfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					panel.hide();
					
				}
			}
		};
		Ext.apply(self.ctr,deptBtnCtr);
	},
	init:function(){
		var self = this;
		this.deptBtnCtr();
		this.control(self.ctr);
	}
});