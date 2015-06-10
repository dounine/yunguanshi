Ext.define('CC.view.department.tree', {
	extend:Ext.tree.Panel,
	alias : 'widget.departmenttree',// 别名为usertree
    autoScroll:true, 
    border:false,
    singleExpand: true,
    rootVisible: false,
    useArrows: true,
    store:'department.DepartmentsStore'
});