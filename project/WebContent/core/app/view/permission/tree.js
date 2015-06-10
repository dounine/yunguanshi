Ext.define('CC.view.permission.tree', {
	extend:Ext.tree.Panel,
	alias : 'widget.permissiontree',// 别名为permissiontree
    autoScroll:true, 
    border:false,
    singleExpand: true,
    rootVisible: false,
    useArrows: true,
    store:'permission.PermissionsStore',
    columns : [// 列模式
			{
					xtype : 'treecolumn', // this is so we know which
					text : '角色名称',
					flex : 2,
					sortable : true,
					dataIndex : 'text'
				}, {
					text : '描述',
					flex : 1,
					dataIndex : 'description',
					sortable : true
				}, {
					text : '状态',
					flex : 1,
					dataIndex : 'description',
					sortable : true,
					hidden:true
				}]
});