Ext.define('CC.view.user.grid', {
			extend : 'CC.base.BaseGrid',
			alias : 'widget.usergrid',// 别名为userlist
			store:'user.UsersStore',
	     	columns : [//列模式
	     			{
		             	header: 'userId', 
		             	dataIndex: 'userId',
		             	hidden:true
		             },//前台编辑模式
		             {
		             	header: '用户名', 
		             	dataIndex: 'username',
		             	field:{
		             		xtype:"textfield",
		             		allowBlank:false
	             		},
	             		renderer:function(value, cellmeta, record){
	             			if(record.get('locked')==1){
	             				return "<del>&nbsp;&nbsp;"+value+"&nbsp;&nbsp;</del>";
	             			}else{
	             				return value;
	             			}
	             		}
		             },//前台编辑模式
		             {
		             	header: '邮箱', 
		             	dataIndex: 'email',
		             	field:{
		             		xtype:"textfield",
		             		allowBlank:false
		             	}
		             },
		             {
		             	header: '注册时间', 
		             	dataIndex: 'birthday',
		             	width:50,
		             	align:'center',
		             	field:{
		             		xtype:"textfield",
		             		allowBlank:false
		             	}
		             },
		             {
		             	header: '所属组', 
		             	dataIndex: 'department',
		             	width:50,
		             	align:'center',
		             	hidden:true
		             }
		             ,
		             {
		             	text: "空间剩余量", 
					    dataIndex: 'progress',
					    align:'center',
					    xtype: 'dvp_progresscolumn',
					    menuDisabled : true,
					    hideable:false,
					    resizable:false,
					    width: 190,
					    editor: {
					        xtype:'numberfield',
					        allowBlank: false
					    }
		             }
		             ,
		             {
		             	header:'角色',
		             	dataIndex:'roles',
		             	hidden:true
		             },
		             {
		             	header: '性别', 
		             	dataIndex: 'gender',
		             	width:20,
		             	align:'center',
		             	field:{
		             		xtype:"textfield",
		             		allowBlank:false
		             	}
		             },
		             {
		             	header: '是否锁定', 
		             	dataIndex: 'locked',
		             	width:20,
		             	align:'center',
		             	field:{
		             		xtype:"textfield",
		             		allowBlank:false
	             		},
	             		renderer:function(value){
	             			if(value=='1')return '是'; 
	             			else return '否';
	             		}
		             }//前台编辑模式
		    ]
		});