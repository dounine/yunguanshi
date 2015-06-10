Ext.define('CC.controller.role.Controller',{
	extend:Ext.app.Controller,
	ctr:{},
	isload:false,
	views : [
		'role.tree',
		'role.permissionGrid'
	],
	stores:[
		'role.RolesStore',
		'permission.PermissionsGridStore'
	],
	models:[
		'role.Role'
	],
	roleBtnCtr:function(){
		var self = this;
		var panelName = 'rolepanel';
		var roleBtnCtr = {
			'rolepanel button[ref=add]':{
				click:function(btn){
					var basepanel = btn.up('basepanel');
					var node = factory.TreeRecord.getCurrent();
					if(node==null){
						var roleTree = basepanel.down('roletree');
						node = roleTree.getRootNode();
					}
					factory.Url.pushReadUrl('permission/readgrid.html');
					factory.Url.pushUpdateUrl('role/add.html?parent.roleId='+node.get('id'));
					Ext.require('CC.view.role.edit',function(){
						var roleeditwindow =Ext.create('CC.view.role.edit',{
							permissionUrl:'permission/read.html'
						});
						roleeditwindow.show();
						roleeditwindow.setTitle('角色添加');
						var formSave = roleeditwindow.down('button[ref=formSave]');
						var grid = roleeditwindow.down('permissionGrid');
						var form = roleeditwindow.down('baseform');
						formSave.on('click',function(){
							roleeditwindow.hide();
							var sumbForm = form.form;
							var values = form.getValues();
							var roleName = sumbForm.findField('roleName');//角色名称
							var resource = sumbForm.findField('resource');//角色标识
							var roleCode = sumbForm.findField('roleCode');//角色编码
							var locked = sumbForm.findField('locked');//是否锁定
							var orderIndex = sumbForm.findField('orderIndex');//排序
							var icon = sumbForm.findField('icon');//角色图标
							var description = sumbForm.findField('description');//描述
							
							Ext.apply(grid.getStore().proxy.extraParams,form.getValues());
							Ext.apply(grid.getStore().proxy.extraParams,{excludes:'checked,parent,children,parentId'});
							grid.getStore().sync({success:function(){
								Ext.example.msg('提交','添加成功');
								roleeditwindow.close();
								node.set('leaf',false);
								basepanel.down('roletree').getStore().reload();
							},failure:function(){
								pWindow.close();
								Ext.Msg.alert('提交','添加失败',function(){
									roleeditwindow.show();
								});
							}});
						});
					});
				}
			},
			'rolepanel button[ref=edit]':{
				click:function(btn){
					//var roleinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					//roleinfobtn.setText('编辑');
					/*var panel = roleinfobtn.ownerCt.ownerCt.show();//获取保存按钮的父窗口
					var form = panel.up('form').form;//清空表单
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(false);
					}
					form.url='role/edit.html';//为编辑表单填充url地址 
					Ext.apply(form,{type:'edit'});*/
					
					var basepanel = btn.up('basepanel');
					var node = factory.TreeRecord.getCurrent();
					factory.Url.pushReadUrl('permission/readgrid.html?roleId='+node.get('id'));
					factory.Url.pushUpdateUrl('role/update.html');
					Ext.require('CC.view.role.edit',function(){
						var roleeditwindow =Ext.create('CC.view.role.edit',{
							permissionUrl:'permission/read.html'
						});;
						roleeditwindow.show();
						roleeditwindow.setTitle('角色添加');
						var formSave = roleeditwindow.down('button[ref=formSave]');
						var grid = roleeditwindow.down('permissionGrid');
						var form = roleeditwindow.down('baseform');
						
						var sumbForm = form.form;
						var values = form.getValues();
						var roleId = sumbForm.findField('roleId');//角色编号
						var roleName = sumbForm.findField('roleName');//角色名称
						var resource = sumbForm.findField('resource');//角色标识
						var roleCode = sumbForm.findField('roleCode');//角色编码
						var locked = sumbForm.findField('locked');//是否锁定
						var orderIndex = sumbForm.findField('orderIndex');//排序
						var icon = sumbForm.findField('icon');//角色图标
						var description = sumbForm.findField('description');//描述
						
						roleId.setValue(node.get('id'));
						roleName.setValue(node.get('text'));
						description.setValue(node.get('description'));
						roleCode.setValue(node.get('code'));
						orderIndex.setValue(node.get('locked'));
						icon.setValue(node.get('icon'));
						resource.setValue(node.get('resource'));
						
						
						formSave.on('click',function(){
							var pWindow = Ext.create('Ext.window.Window',{
								width:300,
								height:100,
								id:'pWindow',
								renderTo:roleeditwindow.getEl(),
								title:'提示'
							}).show();
							var p = Ext.create('Ext.ProgressBar', {
							   renderTo: Ext.getCmp('pWindow').getEl(),
							   width: 200
							});
							p.wait({
							   interval: 500, //bar will move fast!
							   duration: 50000,
							   increment: 15,
							   text: '添加中...',
							   scope: this,
							   fn: function(){
							      p.updateText('网络超时!');
							   }
							});
							roleeditwindow.hide();
							
							
							Ext.apply(grid.getStore().proxy.extraParams,form.getValues());
							Ext.apply(grid.getStore().proxy.extraParams,{excludes:'checked,parent,children,parentId'});
							grid.getStore().sync({success:function(){
								Ext.example.msg('提交','添加成功');
								roleeditwindow.close();
								pWindow.close();
							},failure:function(){
								pWindow.close();
								Ext.Msg.alert('提交','添加失败');
								roleeditwindow.show();
							}});
							
							
						});
					});
				}
			},
			'rolepanel button[ref=delete]':{
				click:function(btn){
					var roleinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					roleinfobtn.setText('编辑');
					var panel = roleinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					var tree = btn.up(panelName).down('roletree');
					var node = tree.getSelectionModel().getLastSelected();
					Ext.Msg.confirm('提示','确定要删除这条数据嘛',function(info){
						if(info=='yes'){
							Ext.Ajax.request({
								url:'role/delete.html',
								method:'POST',
								params:{id:node.getId()},
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
			'rolepanel button[ref=save]':{
				click:function(btn){
					var form = btn.up('form').form;
					var tree = btn.up(panelName).down('roletree');
					var editButton = btn.up(panelName).down('button[ref=edit]');
					var deleteButton = btn.up(panelName).down('button[ref=delete]');
					var node = tree.getSelectionModel().getLastSelected();
					var values = form.getValues();
					var roleinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					var panel = roleinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					if(node==null){
						node = tree.getRootNode();
					}
					var params = {
						roleName:values.text,
						roleCode:values.code
					};
					if(form.type=='add'){
						Ext.apply(params,{'parent.roleId':node.getId(),layer:node.getDepth()+1});
					}else if(form.type=='edit'){
						Ext.apply(params,{roleId:node.getId(),layer:node.getDepth(),'parent.roleId':node.get('parent')});
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
												text:result.result.model.roleName,
												id:result.result.model.roleId,
												parent:result.result.model.parent.roleId,
												leaf:true
												});
											node.expand();
											tree.selectPath(m.getPath());
											tree.fireEvent('itemclick',tree.getView(),m);
										}else{
											node.set('text',result.result.model.roleName);
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
			'rolepanel button[ref=refresh]':{
				click:function(btn){
					var tree = btn.up(panelName).down('roletree');
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
			'roletree':{
				itemclick:function(tree, record, item, index, e, eOpts){
					var deptTree = tree.ownerCt;
					var deleteBtn = deptTree.up('basepanel').down('button[ref=delete]');
					var editBtn = deptTree.up('basepanel').down('button[ref=edit]');
					var form;
					editBtn.setDisabled(false);
					deleteBtn.setDisabled(false);
					var tabs = Ext.getCmp('tabs');
					var first = tabs.getComponent('functionTab3');
					/*if(null==first){
						tabs.add(Ext.create('CC.view.role.edit',{}));
						var ii = tabs.setActiveTab(0);
						form = ii.down('form');*/
					//}else{
					form = first.down('form');
					//}
					form.loadRecord(record);
					factory.TreeRecord.pushCurrent(record);
					factory.Url.pushReadUrl('asdfasdfasdfasdf');
					//var roleinfobtn = tree.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					//var panel = roleinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					//panel.hide();
					
				}
			}
		};
		Ext.apply(self.ctr,roleBtnCtr);
	},
	init:function(){
		var self = this;
		this.roleBtnCtr();
		this.control(self.ctr);
	}
});