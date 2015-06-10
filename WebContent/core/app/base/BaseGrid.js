Ext.define('CC.base.BaseGrid', {
			extend : Ext.grid.Panel,
			alias : 'widget.basegrid',// 别名为userlist
			forceFit:true,//自动填充空白
			columnLines:true,
	     	columns : [//列模式
	     	],
	     	tbar:[//工具栏
	     		{ref:'add',text:'添加',iconCls:'Useradd'},
	     		{ref:'edit',text:'修改',iconCls:'Useredit'},
	     		{ref:'destory',text:'删除',iconCls:'Userdelete'},
	     		{ref:'export',text:'导出',iconCls:'Chartbaradd'},
	     		{ref:'import',text:'导入',iconCls:'Chartbar'},
	     		'->',
	     		{
	     			xtype:'combobox',
	     			fieldLabel:'筛选',
	     			labelWidth:40,
	     			queryMode:'local',
	     			displayField:'name',
	     			valueField:'id',
	     			width:140,
	     			editable:false,
	     			value:'username',
	     			store:Ext.create(Ext.data.ArrayStore,{
	     				autoLoad:true,
	     				fields:['id','name'],
	     				proxy:{
	     					type:'memory',
	     					data:[
	     						['username','用户名'],
	     						['birthday','注册时间'],
	     						['birthday','邮箱'],
	     						['birthday','锁定']
	     						],
	     					reader:{
	     						type:'array'
	     					}
	     				}
	     			})
	     			
	     			},
	     		{  xtype: 'searchfield', store: 'user.UsersStore',paramName:'username'}
	     	],
	     	/*bbar: [{
                xtype: 'pagingtoolbar',
                pageSize: 10,
                store: store,
                displayInfo: true
              
                //plugins: new Ext.ux.ProgressBarPager()
            }],*/
	     	dockedItems:[
	     			{//分页
		     		xtype:'pagingtoolbar',
		     		store:'user.UsersStore',
		     		dock:'bottom',
		     		displayInfo:true,//是否显示分页信息
		     		emptyMsg: '没有记录',
		     		items:[
		     			{
		     				xtype:'combobox',
		     				labelWidth:90,
		     				width:170,//宽度
		     				store:['10条','15条','20条'],
		     				value:'15条',
		     				fieldLabel:'每页显示条数'
		     			}
		     			],
		     		listeners:{
		     			change:function(){//当grid重刷新的时候把选中的去掉、为的是防止有选中数据记录撒手不手
		     					var baseGrid = this.up('basegrid');
		     					var records = baseGrid.getSelectionModel();//获取选中的record
		     					records.deselectAll();
		     				}
		     			}
		     		}
	     	],
	     	selType:'checkboxmodel',//设定选择模式为多选框
	     	multiSelect:true,//多选模式 
	     	initComponent:function(){
	     		this.callParent(arguments);
	     	}
		});