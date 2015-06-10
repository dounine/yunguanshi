Ext.define('CC.store.department.DepartmentsStore', {
			extend : Ext.data.TreeStore,
			defaultRootId:factory.Model.getRootId('department/root.html'),
			autoLoad:true,
			model:'CC.model.department.Department',
			proxy:{
				type:'ajax',
				api:{
					read:'department/read.html'
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
