Ext.define('CC.view.Viewer', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.viewer',
    requires: [
        'CC.view.index.panel'
    ],
    activeItem: 0,
    margins:'4',
    layout : 'anchor',
	id : 'tabs',
	//plain: true,
    initComponent: function() {
        this.items = [
        	{
            xtype: 'indexpanel'//默认加载的controller是哪一个
        	}
        ];
        Ext.getBody().unmask();
        this.callParent(arguments);
    }
});