Ext.define('CC.store.role.RolesStore', {
			extend : Ext.data.TreeStore,
			model:'CC.model.role.Role',
			root : {
				id : null,
				text : '根节点',
				expanded:true
			},
			proxy:{
				type:'ajax',
				url:'role/read.html',
				extraParams :{excludes: 'checked'},
				reader:{
					type:"json"
				},
				autoLoad:true
			}
		});
