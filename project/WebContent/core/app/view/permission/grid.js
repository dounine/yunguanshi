Ext.define('CC.view.permission.grid', {
			extend : Ext.tree.Panel,
			alias : 'widget.permissgrid',// 别名为permissiontree
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
		             	header: 'id', 
		             	dataIndex: 'id',
		             	hidden:true
		             },//前台编辑模式
		             {
		             	xtype : 'treecolumn',
		             	header: '权限名称', 
		             	dataIndex: 'text',
		             	width:200
		             }
		    ]
		});