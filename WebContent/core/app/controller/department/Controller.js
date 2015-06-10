Ext.define('CC.controller.department.Controller',{
	extend:Ext.app.Controller,
	ctr:{},
	views : [
		'department.tree'
	],
	stores:[
		'department.DepartmentsStore'
	],
	models:[
		'department.Department'
	],
	isload:false,
	deptBtnCtr:function(){
		var self = this;
		var panelName = 'deptpanel';
		var deptBtnCtr = {
			'deptpanel button[ref=add]':{
				click:function(btn){
					var tree = btn.up(panelName).down('departmenttree');
					var deptinfobtn = btn.up('deptpanel').down('button[ref=save]');//获取用户组信息的保存按钮
					deptinfobtn.setText('添加');
					var panel = deptinfobtn.ownerCt.ownerCt.show();//获取保存按钮的父窗口
					var form = panel.up('form').getForm();//请空表单
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(false);
					}
					form.reset();
 					Ext.apply(form,{type:'add'});
					form.url='department/add.html';//为编辑表单填充url地址 
				}
			},
			'deptpanel button[ref=edit]':{
				click:function(btn){
					var deptinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					deptinfobtn.setText('编辑');
					var panel = deptinfobtn.ownerCt.ownerCt.show();//获取保存按钮的父窗口
					var form = panel.up('form').form;//请空表单
					for(var m in form.getValues()){
						form.findField(m).setReadOnly(false);
					}
					form.url='department/edit.html';//为编辑表单填充url地址 
					Ext.apply(form,{type:'edit'});
				}
			},
			'deptpanel button[ref=delete]':{
				click:function(btn){
					var deptinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					deptinfobtn.setText('编辑');
					var panel = deptinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					var tree = btn.up('deptpanel').down('departmenttree');
					var node = tree.getSelectionModel().getLastSelected();
					Ext.Msg.confirm('提示','确定要删除这条数据嘛',function(info){
						if(info=='yes'){
							Ext.Ajax.request({
								url:'department/delete.html',
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
			'deptpanel button[ref=save]':{
				click:function(btn){
					var form = btn.up('form').form;
					var tree = btn.up(panelName).down('departmenttree');
					var editButton = btn.up(panelName).down('button[ref=edit]');
					var deleteButton = btn.up(panelName).down('button[ref=delete]');
					var node = tree.getSelectionModel().getLastSelected();
					var values = form.getValues();
					var deptinfobtn = btn.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					var panel = deptinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
					if(node==null){
						node = tree.getRootNode();
					}
					var params = {
						deptName:values.text,
						deptCode:values.code
					};
					if(form.type=='add'){
						Ext.apply(params,{'parent.deptId':node.getId(),layer:node.getDepth()+1});
					}else if(form.type=='edit'){
						Ext.apply(params,{deptId:node.getId(),layer:node.getDepth(),'parent.deptId':node.get('parent')});
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
												text:result.result.model.deptName,
												id:result.result.model.deptId,
												parent:result.result.model.parent.deptId,
												leaf:true
												});
											node.expand();
											tree.selectPath(m.getPath());
											tree.fireEvent('itemclick',tree.getView(),m);
										}else{
											node.set('text',result.result.model.deptName);
											node.commit();
										}
										/*editButton.setDisabled(true);
										deleteButton.setDisabled(true);*/
										panel.hide();
										Ext.example.msg('提示',result.result.info);
										for(var m in form.getValues()){
											form.findField(m).setReadOnly(true);
										}
									},
									failure:function(response,result){
										Ext.Msg.alert('提示',result.result.info);
									}
								});
				}
			},
			'deptpanel button[ref=refresh]':{
				click:function(btn){
					var tree = btn.up(panelName).down('departmenttree');
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
			'departmenttree':{
				itemclick:function(tree, record, item, index, e, eOpts){
					var deptTree = tree.ownerCt;
					var deleteBtn = deptTree.up('basepanel').down('button[ref=delete]');
					var editBtn = deptTree.up('basepanel').down('button[ref=edit]');
					var form;
					editBtn.setDisabled(false);
					deleteBtn.setDisabled(false);
					var tabs = Ext.getCmp('tabs');
					var first = tabs.getComponent('functionTab2');
					if(null==first){
						tabs.add(Ext.create('CC.view.department.edit',{}));
						var ii = tabs.setActiveTab(0);
						form = ii.down('form');
					}else{
						form = first.down('form');
					}
					form.loadRecord(record);
					var deptinfobtn = tree.up(panelName).down('button[ref=save]');//获取用户组信息的保存按钮
					var panel = deptinfobtn.ownerCt.ownerCt;//获取保存按钮的父窗口
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