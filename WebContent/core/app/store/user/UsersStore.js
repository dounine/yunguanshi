Ext.define('CC.store.user.UsersStore', {
			extend : Ext.data.Store,
			model:'CC.model.user.User',
			pageSize: 23,//每页显示条数
			autoLoad:true,
			proxy:{
				type:'ajax',
				url:'user/read.html',
				extraParams:{whereSql:'',excludes:'roles'},
				reader:{
					type:'json',
					root:'users',
					successProperty: 'success'
				}
			}
		});
