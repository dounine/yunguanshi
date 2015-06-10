Ext.define('CC.view.role.panel', {
	extend:'CC.base.BasePanel',
	layout : 'border',
	closable:true,
	alias:'widget.rolepanel',
	bodyStyle:'background:#fff;',
	items : [{
				region : 'west',
				width:'50%',
				margins:'5',
				bodyStyle:'border:1px solid #C0C0C0;border-top:none;',
				tbar:[
					{text:'添加',ref:'add',iconCls:'Tableadd'},
					{text:'编辑',ref:'edit',disabled:true,iconCls:'Tableedit'},
					{text:'删除',ref:'delete',disabled:true,iconCls:'Tabledelete'},'->',
					{iconCls:'Arrowrefresh',ref:'refresh'}
				],
				layout: 'fit',
				items:[
					{
						xtype:'roletree'
					}
				]
			}, {
				region : 'center',
				xtype : 'panel',
				layout:'border',
				border:false,
				bodyStyle:'background:#fff;',
				items:[
				       {
				       		region : 'center',
				       		xtype:'form',
				       		margins:'5',
				       		layout:'border',
							autoScroll : true,
				       		bodyStyle:'padding:5px 0px;border:1px solid #C0C0C0;background:#fff;',
				       		tbar:[
				       			{
				       				xtype:'button',
				       				disabled:true,
									iconCls: 'table_dic'
				       			},
								{
									xtype:'tbtext',
									text:'<font style="font-weight:700;">角色信息</font>'
								}
							],
				       		items:[
				       			{
				       				region:'north',
				       				layout:'form',
				       				margins:'10 0 0 0',
				       				border:false,
				       				items:[
						       			{
											border:false,
											layout:'form',
											bodyStyle:'padding:0 10px 0 0;',
											defaults:{
												xtype:'textfield',
												labelWidth:70,
												readOnly :true,
												labelAlign:'right'
											},
											items:[
												{
													name:'id',
													fieldLabel:'角色编号',
													hidden:true
												},
												{
													name:'text',
													fieldLabel:'角色名称',
													fieldCls:'x-form-field-my'
												},
												{
													name:'ressource',
													fieldLabel:'角色标识',
													fieldCls:'x-form-field-my'
												},
												{
													name:'code',
													fieldLabel:'角色编码',
													fieldCls:'x-form-field-my'
												}
												,
												{
													name:'orderIndex',
													fieldLabel:'角色排序',
													fieldCls:'x-form-field-my'
												},
												{
													name:'icon',
													fieldLabel:'角色图标',
													fieldCls:'x-form-field-my'
												},
												{
													name:'description',
													fieldLabel:'角色描述',
													fieldCls:'x-form-field-my'
												},
												{
													name:'permissionarrays',
													fieldLabel: '隐藏的权限列表id值',
													hidden:true
												},
												{
													xtype: 'panel', //comboboxtree自定义
													height:28,
													border:false,
													ref:'permissionspanel',
													id:'permissionspanel',
													value:'',//接收从controller传递过来的值
													extraParams:{checkIds:[]},
													layout:'anchor',
													listeners:{
														'getPermissions':function(){
															var self= this;
															var form = this.ownerCt.ownerCt.ownerCt;
															var tree = Ext.getCmp('permissionComboboxTree');
															
															if(null==tree){
																tree = Ext.create('CC.util.ComboboxTree',{
																	fieldLabel: '用户角色',
																	url:form.permissionUrl,//读取地址
																	extraParams:{checkIds:[]},
																	labelWidth:60,
																	id:'permissionComboboxTree',
																	emptyText:'请选择权限',
																	anchor: '100%'
																});
																self.add(tree);
															}else{
																tree.treeObj.fireEvent('reload',form.roleUrl);
															}
															var permissionarrays = self.ownerCt.down('textfield[name=permissionarrays]');
															var treeobj = tree;
															var loadCount =false;
															tree.treeObj.store.on('load',function(treeview, record, item, index, e, opts){//数据已经从服务器读取完毕
																var permissionidarrays = [];//选中权限列表id
																var permissiontextarrays = [];//选中权限列表文字
																Ext.each(item,function(){
																	if(this.get('checked')){//判断如是角色权限信息
																		permissionidarrays.push(this.get('id'));
																		permissiontextarrays.push(this.get('text'));
																	}
																});
																permissionarrays.setValue(permissionidarrays.join(','));//设置角色id的值
																tree.setValue(permissiontextarrays.join(' | '));//显示所选中的角色
															});
															treeobj.on('collapse',function(){//把所选中的id列表加到textfield中
																if(treeobj.isClick){
																	permissionarrays.setValue(tree.ids);
																}
															});
														}
														
													}
												}
											]
						       			}
				       				]
				       			},{
				       				region : 'south',
				       				xtype:'panel',
				       				alias:'widget.roleinfobtn',
				       				bodyStyle:'border:none;border-top:1px solid #157FCC;',
				       				hidden : true,
				       				buttons:[
				       					{
				       						text:'保存',
				       						ref:'save'
				       					}
				       				]
				       			}
				       		]
				       	}
				       ]
			},
			{
				region: 'east',
				width: 2,
				bodyStyle:'border:2px solid #9cf;'
			}]
});
