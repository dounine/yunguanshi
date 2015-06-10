Ext.define('CC.util.SuperUtil',{
	ajax:function(config){
		var date={};
		var request = {
			method:'POST',
			async:false,
			success:function(response){
				date = Ext.decode(Ext.value(response.responseText,''));
			},
			failure:function(response){
				date = Ext.decode(Ext.value(response.responseText,''));
			}
		};
		var request = Ext.apply(request,config);//合并参数操作
		Ext.Ajax.request(request);
		return date;
	}
});