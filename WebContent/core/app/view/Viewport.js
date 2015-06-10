Ext.define('CC.view.Viewport', {
	extend : Ext.container.Viewport,
	layout : 'border',
	requires: [
        'CC.view.Viewer'
    ],
	items : [{
		region : 'north',
		html : '<div style="height:66px;width:100%;background:url(http://yunguanshi.qiniudn.com/images/ygs/admin/headbg.png) repeat-x;"><div style="background:url(http://yunguanshi.qiniudn.com/images/ygs/admin/adminlog.png) left center no-repeat;width:300px;height:60px;"></div><a id="logout">退出系统</a></div>',
		border : false
	}, {
		region : 'west',
		layout : 'accordion',
		collapsible : true,
		split : true,
		id : 'navigation',
		width : 200,
		border : true,
		title : '菜单导航',
		defaults : {
			activeOnTop : false, // 设置打开的子面板置顶
			fill : true, // 子面板充满父面板的剩余空间
			hideCollapseTool : false, // 显示“展开收缩”按钮
			titleCollapse : true, // 允许通过点击子面板的标题来展开或收缩面板
			animate : true, // 使用动画效果
			autoWidth : true
		},
		tools : [{
					itemId : 'gear',
					type : 'gear',
					callback : function() {
					}
				}],
		items : [{
					title : '内容管理',
					iconCls : 'table_resource',
					items : [{
								xtype : 'contenttree'
							}]
				}, {
					title : '字典管理',
					iconCls : 'Booktabs'
				}, {
					title : '系统管理',
					iconCls : 'table_sys'
				}

		]
	}, {
		region : 'center',
		xtype : 'viewer'
		
	}, {
		region : 'east',
		width : 2,
		bodyStyle : 'border:2px solid #9cf;'
	}, {
		region : 'south',
		height : 30,
		bodyStyle:'padding-top:5px;background:#fff;border-top:none;',
		layout:'border',
		items:[
			{
				region : 'west',
				xtype:'tbtext',
				width:180,
				text:'&nbsp;&nbsp;&nbsp;欢迎 admin 进行管理'
			},
			{
				xtype:'tbtext',
				region : 'center',
				text:'已使用本系统：35 分钟'
			},
			{
				region : 'east',
				xtype:'tbtext',
				width:200,
				text:'系统时间：2014-08-16 17:15:22'
			}
		]
	}]
});