/**
 * 得到默认值对象
 */
Ext.define('CC.util.GridActionUtil',{
	getDefaultValue:function(defaultObj){
		var date={};
		for(var field in defaultObj){
			var value = defaultObj[field];
			date[field]=value;
		}
		return date;
	}
});