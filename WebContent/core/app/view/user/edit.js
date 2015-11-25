Ext.define('CC.view.user.edit', {
			extend : 'CC.base.BaseWindow',
			title : '添加用户',
			//closeAction:'hide',//目前有bug
			width:600,
			height:270,
			id:'usereditwindow',
			alias : 'widget.useradd',
			isFirstShow:true,//窗口是否是第一次显示
			requires:['CC.base.BaseForm','CC.util.ComboTree'],
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
						console.log('数据还没有保存、确定要退出吗?');
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
				layout:'column',
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
								name:'userId',
								fieldLabel:'用户编号',
								hidden:true
							},
							{
								name:'username',
								fieldLabel:'用户名'
							},
							{
								name:'password',
								inputType:'password',
								fieldLabel:'密&nbsp;&nbsp;码'
							},
							{
								name:'email',
								fieldLabel:'邮&nbsp;&nbsp;箱'
							},
							{
								xtype:'datetimefield',
								name:'birthday',
								fieldLabel:'注册时间',
								format:'Y-m-d H:i:s'//格式化时间
							},
							{
						        layout:'column',
			        			xtype:'panel',
			        			border:false,
						        items:[
						        	{
							        	fieldLabel: '用户编码',
							        	xtype:'textfield',
							        	labelWidth:60,
								        name:'userCode',
								        height:24,
								        width:238
						        	},{
						        		xtype:'button',
								        qtip : '生成',
						        		iconCls:'Keystart',
								        listeners:{
											'click':function(){
												var text = this.ownerCt.ownerCt.down('textfield[name=username]');
												var code = this.ownerCt.ownerCt.down('textfield[name=userCode]');
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
								xtype: 'radiogroup',
						        fieldLabel: '性&nbsp;&nbsp;别',
						        columns: 3,
						        vertical: true,
						        items: [
						            { boxLabel: '男', name: 'gender', inputValue: '男' },
						            { boxLabel: '女', name: 'gender', inputValue: '女'},
						            { boxLabel: '保密', name: 'gender', inputValue: '保密', checked: true}
						        ]
							},
							{
								xtype: 'radiogroup',
						        fieldLabel: '锁&nbsp;&nbsp;定',
						        columns: 3,
						        vertical: true,
						        items: [
						            { boxLabel: '是', name: 'locked', inputValue: 'true'},
						            { boxLabel: '否', name: 'locked', inputValue: 'false', checked: true}
						        ]
							},
							{
								xtype: 'radiogroup',
						        fieldLabel: '本地时间',
						        columns: 3,
						        items: [
						            { boxLabel: '是', name: 'localTime', inputValue: 'true'},
						            { boxLabel: '否', name: 'localTime', inputValue: 'false', checked: true}
						        ]
							},
							{
				     			xtype:'combobox',
					            store: new Ext.data.JsonStore({  
							        fields : ['deptId','deptName'],  
							        proxy:{
							        	type:'ajax',
								        url : "department/readarray.html",  
								        render:{
								        	type:'json'
								        }
							        },
							        autoLoad:true  
   								}), 
						        name:'deptId', 
						        fieldLabel:"所属分组",  
						        mode: 'client',  
						        triggerAction: 'all',  
						        valueField: 'deptId',  
						        displayField: 'deptName',  
						        hiddenName:'deptName',
						        anchor:'100%',
						        emptyText: '请选择所属组',  
						        blankText: '请选择...', 
				     			editable:false,
				     			queryMode:'local',
				     			listeners:{
									'setValue':function(value){
										this.setValue(value.deptId);
									}				     			
				     			}
			     			},
							{
								name:'rolearrays',
								fieldLabel: '隐藏的角色列表id值',
								hidden:true
							},
							{
								xtype: 'panel', //comboboxtree自定义
								height:28,
								border:false,
								ref:'rolespanel',
								id:'rolespanel',
								value:'',//接收从controller传递过来的值
								extraParams:{checkIds:[]},
								layout:'anchor',
								listeners:{
									'getRoles':function(){
										var self= this;
										var form = this.ownerCt.ownerCt.ownerCt;
										var tree = Ext.getCmp('roleComboboxTree');
										
										if(null==tree){
											tree = Ext.create('CC.util.ComboboxTree',{
												fieldLabel: '用户角色',
												url:form.roleUrl,//读取地址
												extraParams:{checkIds:[]},
												labelWidth:60,
												id:'roleComboboxTree',
												emptyText:'请选择一个角色',
												anchor: '100%'
											});
											self.add(tree);
										}else{
											tree.treeObj.fireEvent('reload',form.roleUrl);
										}
										var rolearrays = self.ownerCt.down('textfield[name=rolearrays]');
										var treeobj = tree;
										var loadCount =false;
										tree.treeObj.store.on('load',function(treeview, record, item, index, e, opts){//数据已经从服务器读取完毕
											var roleidarrays = [];//选中角色列表id
											var roletextarrays = [];//选中角色列表文字
											Ext.each(item,function(){
												if(this.get('checked')){//判断如是用户的角色信息
													roleidarrays.push(this.get('id'));
													roletextarrays.push(this.get('text'));
												}
											});
											rolearrays.setValue(roleidarrays.join(','));//设置角色id的值
											tree.setValue(roletextarrays.join(' | '));//显示所选中的角色
										});
										treeobj.on('collapse',function(){//把所选中的id列表加到textfield中
											if(treeobj.isClick){
												rolearrays.setValue(tree.ids);
											}
										});
									}
									
								}
							},
							{
								xtype:'numberfield',
								name:'sumZone',
								anchor:'100%',
								minValue:0,
								editable:false,
								fieldLabel: '使用空间'
							}
						] 
					}
				]
			}
		});