Ext.define('CC.view.role.edit', {
			extend : 'CC.base.BaseWindow',
			title : '添加角色',
			width:800,
			height:500,
			closeaction:'close',
			id:'roleeditwindow',
			alias : 'widget.roleadd',
			isFirstShow:true,//窗口是否是第一次显示
			requires:['CC.base.BaseForm'],
			funDate:{
				action:'',//请求action
				whereSql:'',//表格查询条件
				orderSql:'',//表格排序条件
				tableName:'',//实体全路径
				defaultObj:{create:new Date()},//默认信息,用于表格添加字段默认值
				childFun:[]//子功能
			},
			listeners:{
				close:function(){
					if(this.down('baseform').isDirty()){
					}
				},
				show:function(){
					if(!this.isFirstShow){
					}
					this.isFirstShow = false;
				}
			},
			items : {
				xtype:'baseform',
				bodyStyle:'padding:10px 20px 10px 10px',
				border:false,
				items:[
					{
			         columnWidth:1,  
			         layout:'column',  
			         xtype:'fieldset',  
			         style:'margin-left:10px',  
			         autoHeight:true,  
			         title:'角色信息',  
			         defaultType:'textfield',
			         items:[
			         	{
							xtype:'panel',
							layout:'anchor',
							border:false,
							columnWidth:0.5,
							defaults:{
								xtype:'textfield',
								labelWidth:60,
								anchor:'94%',
								labelAlign:'right'
							},
							items:[
								{
									name:'roleId',
									fieldLabel:'角色编号',
									hidden:true
								},
								{
									name:'roleName',
									fieldLabel:'角色名称'
								},
								{
									name:'resource',
									fieldLabel:'角色标识'
								},
								{
							        layout:'column',
				        			xtype:'panel',
				        			border:false,
							        items:[
							        	{
								        	fieldLabel: '角色编码',
								        	xtype:'textfield',
								        	labelWidth:60,
									        name:'roleCode',
									        height:24,
									        width:314
							        	},{
							        		xtype:'button',
									        qtip : '生成',
							        		iconCls:'Keystart',
									        listeners:{
												'click':function(){
													var text = this.ownerCt.ownerCt.down('textfield[name=roleName]');
													var code = this.ownerCt.ownerCt.down('textfield[name=roleCode]');
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
								},
								{
									xtype: 'radiogroup',
							        fieldLabel: '是否启用',
							        columns: 3,
							        items: [
							            { boxLabel: '是', name: 'locked', inputValue: 'true', checked: true},
							            { boxLabel: '否', name: 'locked', inputValue: 'false'}
							        ]
								}
							]
						},
						{
							xtype:'panel',
							layout:'anchor',
							border:false,
							columnWidth:0.5,
							defaults:{
								xtype:'textfield',
								labelWidth:60,
								labelAlign:'right'
							},
							items:[
								{
									xtype:'numberfield',
									value:0,
									name:'orderIndex',
									fieldLabel:'角色排序',
									anchor:'98%'
								},
								{
									name:'icon',
									fieldLabel:'角色图标',
									anchor:'98%'
								},
								{
									name:'description',
									fieldLabel:'角色描述',
									anchor:'98%'
								}
							] 
						}
			         ]
					},
					{
						xtype:'panel',
						border:false,
						ref:'permissiongridpanel',
						items:[
							{
						         xtype:'fieldset',  
						         style:'margin-left:10px;padding-bottom:8px;',  
						         autoHeight:true,  
						         title:'授权权限',  
						         defaultType:'textfield',
						         items:[
						         	{
										xtype:'permissionGrid'
						         	}
						         ]
							}
						]
						
					}
				]
			}
		});