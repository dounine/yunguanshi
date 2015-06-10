Ext.define('CC.view.role.tree', {
			extend : Ext.tree.Panel,
			alias : 'widget.roletree',// 别名为usertree
			autoScroll : true,
			bodyStyle : 'border:none;',
			// singleExpand: true,
			rootVisible : false,
			store : 'role.RolesStore',
			useArrows : true,
			lines : true
			/*columns : [// 列模式
			{
						xtype : 'treecolumn', // this is so we know which
												// column will show the tree
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
						sortable : true
					}]*/
		});