Ext.define('CC.controller.user.Controller',{
	extend:Ext.app.Controller,
	ctr:{},
	isload:false,
	isRegister:false,
	mixins:{
		searchField:'Ext.ux.form.SearchField',
		treeUtil:'CC.util.TreeUtil',
		progress:'Ext.ux.grid.column.Progress'
	},
	views : [
		'user.grid',
		'user.UserDepartmentTree'
	],
	stores:[
		'user.UsersStore',
		'department.DepartmentsStore'
	],
	models:[
		'user.User',
		'role.Role',
		'department.Department'
	],
	userBtn:function(){
		var self = this;
		var userBtn = {
			'basegrid button[ref=add]':{
				click:function(btn){
					Ext.require('CC.view.user.edit',function(){
						var baseGrid = btn.up('basegrid');//向上查找grid
						var tree = baseGrid.ownerCt.down('userdepartmenttree');
						var node = tree.getSelectionModel().getSelection();
						var baseForm = Ext.getCmp('usereditwindow');
						if(null==baseForm){
							Ext.create('CC.view.user.edit',{
								roleUrl:'role/read.html',
								deptUrl:'department/read.html'
								});//第一次创建添加显示窗口
						}
						baseForm = Ext.getCmp('usereditwindow');
						baseForm.show();
						baseForm.setTitle('用户添加');
							
						var form = baseForm.items.get(0);//获取表单对象
						form.form.reset();
						form.form.findField('birthday').setValue(new Date());//给表单对象的时间赋值
						form.down('panel[ref=rolespanel]').fireEvent('getRoles');//手动触发事件
						var baseStore = baseGrid.store;//拿到数据集
						var saveButton = baseForm.down('button[ref=formSave]');//添加按钮
						saveButton.setText('修改');
						saveButton.on('click',function(){
							self.submitAdd(baseForm,form.form,node,baseStore);
						});
					});
				}
			},
			'basegrid button[ref=edit]':{
				click:function(btn){
					Ext.require('CC.view.user.edit',function(){
						var baseGrid = btn.up('basegrid').down('gridview');
						var records = baseGrid.getSelectionModel().getSelection();//获取选中的record
						if(records.length==1){
							
							var baseForm = Ext.getCmp('usereditwindow');
							if(null==baseForm){
								Ext.create('CC.view.user.edit',{});//第一次创建添加显示窗口
									console.log('创建窗口');
							}
							baseForm = Ext.getCmp('usereditwindow');
							baseForm.setTitle('用户信息修改');
							
							baseForm.show();
							Ext.apply(baseForm,{
								roleUrl:'role/readbyuid.html?userId='+records[0].get('userId'),
								deptUrl:'department/readbyuid.html?userId='+records[0].get('userId')
								});
							var form = baseForm.items.get(0);//获取表单对象
							form.loadRecord(records[0]);//将reocrd填充到表单中
							form.down('textfield[name=deptId]').fireEvent('setValue',records[0].get('department'));//手动触发事件
							form.down('panel[ref=rolespanel]').fireEvent('getRoles');//手动触发事件
							var saveButton = baseForm.down('button[ref=formSave]');//保存按钮
							saveButton.setText('修改');
							saveButton.on('click',function(){
								self.submitEdit(baseForm,form,baseGrid);
							});
						}else{
							Ext.Msg.alert('提示','请选择一条数据进行操作!!!');
						}
					});
				}
			},
			'basegrid button[ref=destory]':{
				click:function(btn){
					var grid = btn.up('basegrid');//获取到grid
					var records = grid.getSelectionModel().getSelection();//获取选中的数据集
					if(records.length==0){
						Ext.Msg.alert('提示','请选择一条数据进行操作!!!');
					}else{
						Ext.Msg.confirm('提示','您确定要删除这 <font style="color:red;font-size:14px;font-weight:700;">'+records.length+'</font> 条数据吗？',function(info){
							if(info=='yes'){
								var ids = [];
								Ext.Array.each(records,function(model){
									ids.push(model.get('userId'));
								});
								Ext.Ajax.request({
									url:'user/destory.html',
									params:{ids:ids.join(',')},
									success:function(response){
										date=Ext.decode(response.responseText);
										if(date.success){
											var store = grid.getStore();
											store.remove(records);
											grid.store.reload();//重新加载亲折数据记录
											Ext.example.msg('提示',date.info);
										}else{
											date=Ext.decode(response.responseText).info;
											Ext.Msg.alert('提示',date);
										}
									}
								});
							}
						});
					}
				}
			},
			'userdepartmenttree':{
				itemclick:function(tree, record, item, index, e, eOpts){
					var node = tree.getSelectionModel().getSelection();
					/*if(node.length==1){
						tree.up('basepanel').down('button[ref=add]').setDisabled(false);//按钮显示
					}*/
					var panelDeptInfo = tree.up('basepanel').down('panel[ref=panel_dept_info]');
					if(panelDeptInfo!=null){
						var deptName = panelDeptInfo.items.get(0);//用户组名称
						var deptRole = panelDeptInfo.items.get(1);//用户组角色
						var deptDescription = panelDeptInfo.items.get(2);//用户组描述信息
						deptName.setValue(record.get('text'));
						deptRole.setValue('<font style="color:red;">无</font>');
						deptDescription.setValue(record.get('description'));
					}
				},
				itemdblclick:function(tree, record, item, index, e, eOpts){
					var panel = tree.up('basepanel');
					var grid = panel.down('basegrid');	
					var store = grid.getStore();
					var ids = new Array();
					var map = self.eachChildNode(record);
					map.each(function(m){
						ids.push(m.get('id'));
					});
					store.getProxy().extraParams={whereSql:ids};//把id传到后台
					store.load();
				}
			}
		};
		Ext.apply(self.ctr,userBtn);
	},
	submitAdd:function(baseForm,form,node,baseStore){
			baseForm.hide();
			form.doAction('submit',{
				url:'user/add.html',//添加用户请求地址
				method:'POST',
				params:{'department.deptId':node[0].get('id')},
				waitTitle: "请稍候", 
				waitMsg: '正在添加数据...',
				success:function(response,result){
					baseStore.insert(0,result.result.model);
					baseStore.removeAt(2,1);
					baseForm.close();
					Ext.example.msg('提示',result.result.info,function(){
						clickQuick = false;
					});
				},
				failure:function(response,result){
					Ext.Msg.alert('提示',result.result.info,function(){
						baseForm.show();
						clickQuick = false;
					});
				}
			});
	},
	submitEdit:function(baseForm,form,baseGrid){
			console.log("submitEdit提交");
			baseForm.hide();
			form.getForm().doAction('submit',{
				url:'user/edit.html',//添加用户请求地址
				method:'POST',
				waitTitle: "请稍候", 
				waitMsg: '正在保存数据...', 
				success:function(response,result){
					baseForm.close();
					baseGrid.store.reload();//重新加载亲折数据记录
					Ext.example.msg('提示',result.result.info);
				},
				failure:function(response,result){
					Ext.Msg.alert('提示',result.result.info,function(){
						baseForm.show();
					});
				}
			});
	},
	init:function(){
		var self = this;
		this.userBtn();
		this.control(self.ctr);
	}
});