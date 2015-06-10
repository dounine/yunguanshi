Ext.define('CC.view.role.permissionGrid', {
			extend : Ext.tree.Panel,
			alias : 'widget.permissionGrid',// 别名为permissiontree
		    autoScroll:true, 
		    singleExpand: true,
		    rootVisible: false,
		    columnLines:true,
		    forceFit:true,//自动填充空白
		    height:230,
		    requires: [
		        'Ext.data.*',
		        'Ext.grid.*',
		        'Ext.tree.*',
		        'Ext.ux.CheckColumn'
		    ],
		    store:'permission.PermissionsGridStore',
	     	columns : [//列模式
	     			{
		             	header: 'permissionId', 
		             	dataIndex: 'permissionId',
		             	hidden:true
		             },//前台编辑模式
		             {
		             	xtype : 'treecolumn',
		             	header: '权限名称', 
		             	dataIndex: 'text',
		             	width:200
		             },
		             {
		             	header: '添加', 
		             	width:50,
		             	dataIndex: 'resAdd',
		             	xtype:'checkcolumn'
		             },
		             {
		             	header: '删除', 
		             	width:50,
		             	xtype:'checkcolumn',
		             	dataIndex: 'resDelete'
		             },
		             {
		             	header: '修改', 
		             	width:50,
		             	dataIndex: 'resUpdate',
		             	xtype:'checkcolumn'
		             },
		             {
		             	header: '查看', 
		             	width:50,
		             	dataIndex: 'resRead',
		             	xtype:'checkcolumn'
		             }
		    ]
		});