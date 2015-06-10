Ext.Loader.setConfig({enabled: true});
Ext.application({
	name : 'CC',
	 paths: {
        'Ext.ux': 'http://yunguanshi.qiniudn.com/js/ext/4.2.1/examples/ux',
        'baseUx':'http://yunguanshi.qiniudn.com/js/ext/yunguanshi/core/ux/base',
        'factory':'http://yunguanshi.qiniudn.com/js/ext/yunguanshi/core/util/factory'
    },
	appFolder : 'core/app',
	controllers : ['MainController'],
	autoCreateViewport: true
});
