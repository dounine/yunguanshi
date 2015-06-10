Ext.define('CC.store.permission.PermissionsTreeStore', {
			extend : Ext.data.TreeStore,
			defaultRootId:'root',
			model:'CC.model.permission.PermissionGrid',
			proxy:{
				type:'ajax',
				api:{
					read:'',
					update:''
				},
				extraParams :{excludes: 'checked'},
				reader:{
					type:'json'
				},
				writer:{
					type:'json'
				}
			},
			listeners:{
				'beforeload':function(){
					this.proxy.api.read=factory.Url.getReadUrl();
					this.proxy.api.update=factory.Url.getUpdateUrl();
				}
			}
		});
