Ext.define('CC.view.user.panel', {
	extend:'CC.base.BasePanel',
	closeAction: 'hide',
	layout : 'border',
	closable:true,
	alias:'widget.userpanel',
	bodyStyle:'background:#fff;',
	items : [
			{
				region : 'west',
				width:200,
				layout: 'fit',
				bodyStyle:'border:none;background:#fff;border-right:1px solid #157FCC;',
				layout:'border',
				items:[
					{
						region : 'center',
						margins:'6',
						tbar:[
							{
								xtype:'tbtext',
								text:'<font style="font-weight:700;">所属用户组</font>'
							}
						],
						bodyStyle:'border:none;border-top:1px solid #C0C0C0;',
						xtype:'userdepartmenttree',
						border:true
					},{
						region : 'south',
						xtype:'panel',
						margins:'6',
						bodyStyle:'border:none;border-top:1px solid #C0C0C0;',
						height:'30%',
						layout:'anchor',
						ref:'panel_dept_info',
						autoScroll:true,
						defaults:{
							xtype:'displayfield',
							labelWidth:50,
							labelAlign:'right'
						},
						tbar:[
							{
								xtype:'tbtext',
								text:'<font style="font-weight:700;">用户组信息</font>'
							}
						],
						items:[
							{
								fieldLabel:'名称',
								ref:'deptName'
							},
							{
								fieldLabel:'角色',
								ref:'roleName'
							},
							{
								fieldLabel:'描述',
								ref:'deptDescription'
							}
						]
					}
				]
			}, {
				region : 'center',
				xtype : 'usergrid',
				border:false
			},
			{
				region: 'east',
				width: 2,
				bodyStyle:'border:2px solid #9cf;'
			}]
});
