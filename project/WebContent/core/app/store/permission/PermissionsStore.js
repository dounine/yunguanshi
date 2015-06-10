Ext.define('CC.store.permission.PermissionsStore', {
			extend : Ext.data.TreeStore,
			defaultRootId:factory.Model.getRootId('permission/root.html'),
			autoLoad:true,
			model:'CC.model.permission.Permission',
			proxy:{
				type:'ajax',
				api:{
					read:'permission/read.html'
				},
				extraParams :{excludes: 'checked'},
				reader:{
					type:'json'
				},
				writer:{
					type:'json'
				},
				autoLoad:true
			}
		});
