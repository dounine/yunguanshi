Ext.define('CC.view.user.UserDepartmentTree', {
	extend:Ext.tree.Panel,
	alias : 'widget.userdepartmenttree',// 别名为userdepartmenttree
    autoScroll:true, 
    border:false,
    singleExpand: true,
    onlyLeafCheckable:true,
    rootVisible: false,
    toggleOnDblClick:false,
    useArrows: true,
    store:'department.DepartmentsStore',
    listeners:{
    	itemclick:function(treeview, record, item, index, e, opts){
    		treeview.toggleOnDblClick = false;//取消双击展开事件
    	}
    }
});