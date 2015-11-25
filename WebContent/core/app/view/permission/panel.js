Ext.define('CC.view.permission.panel', {
	extend:'CC.base.BasePanel',
	layout : 'border',
	closable:true,
	alias:'widget.permissionpanel',
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
						xtype:'permissgrid'
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
				       		region : 'north',
				       		height:300,
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
									text:'<font style="font-weight:700;">权限信息</font>'
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
													fieldLabel:'权限编号',
													hidden:true
												},
												{
													name:'text',
													fieldLabel:'权限名称'
												},
												{
													name:'source',
													fieldLabel:'权限资源'
												}
												,
												{
													xtype:'panel',
													height:28,
											        layout:'border',
								        			border:false,
											        items:[
											        	{
											        		region:'center',
											        		border:false,
											        		layout:'form',
											        		items:[
											        			{
														        	fieldLabel: '权限编码',
														        	xtype:'textfield',
														        	labelWidth:70,
															        name:'code',
															        labelAlign:'right'
											        			}
											        		]
											        	},{
											        		region:'east',
													        width:26,
													        border:false,
											        		items:[
											        			{
													        		xtype:'button',
															        qtip : '生成',
													        		iconCls:'Keystart',
															        listeners:{
																		'click':function(){
																			var text = this.up('basepanel').down('textfield[name=text]');
																			var code = this.ownerCt.ownerCt.down('textfield[name=code]');
																			if(text.getValue().trim().length>0){
																				Ext.Ajax.request({
																					url:'util/convert.html',
																					timeout:3000,
																					params:{chinese:text.getValue()},
																					success:function(response,result){
																						code.setValue(Ext.decode(response.responseText).info);
																					}
																				});
																			}
																		}
																	}
											        			}
											        		]
											        	}
											        ]
												},
												{
													xtype:'numberfield',
													minValue: 0,
													value:'0',
													name:'orderIndex',
													fieldLabel:'权限排序'
												},
												{
													xtype: 'checkboxgroup',
											        fieldLabel: '操作标识',
											        items: [
											            { boxLabel: '增加', name: 'resAdd'},
											            { boxLabel: '删除', name: 'resDelete'},
											            { boxLabel: '修改', name: 'resUpdate'},
											            { boxLabel: '查询', name: 'resRead'}
											        ]
												},
												{
													name:'icon',
													fieldLabel:'权限图标'
												}
												,
												{
													name:'description',
													fieldLabel:'权限描述'
												}
											]
						       			}
				       				]
				       			},{
				       				region : 'south',
				       				xtype:'panel',
				       				alias:'widget.permissioninfobtn',
				       				bodyStyle:'border:none;border-top:1px solid #C0C0C0;',
				       				hidden : true,
				       				buttons:[
				       					{
				       						text:'保存',
				       						ref:'save'
				       					}
				       				]
				       			}
				       		]
				       	},
				       	{
				       		region : 'center',
				       		xtype:'panel',
				       		bodyStyle:'border:1px solid #C0C0C0',
				       		margins:'5',
				       		tbar:[
				       			{
				       				xtype:'button',
				       				disabled:true,
									iconCls: 'Cmy'
				       			},
								{
									xtype:'tbtext',
									text:'<font style="font-weight:700;">信息提示</font>'
								}
							],
							layout:'form',
							items:[
								{
									xtype:'panel',
									border:false,
									height:40,
									bodyStyle:'line-height:40px;margin-left:20px;color:#666;',
									html:'<font style="font-weight:700;">1：点击权限编码后是根据输入的中文转成拼音的。</font>'
								},
								{
									xtype:'panel',
									border:false,
									height:40,
									bodyStyle:'line-height:40px;margin-left:20px;color:#666;',
									html:'<font style="font-weight:700;">2：点击某个权限之后、就可以在其之下添加新的权限了。</font>'
								},
								{
									xtype:'panel',
									border:false,
									height:40,
									bodyStyle:'line-height:40px;margin-left:20px;color:#666;',
									html:'<font style="font-weight:700;">3：点击添加或编辑按钮之后、用户组信息下才会出现提交按钮。</font>'
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
