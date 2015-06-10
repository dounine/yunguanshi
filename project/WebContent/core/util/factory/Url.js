Ext.define('factory.Url',{
	statics:{
		currentAddUrl:new Array,//添加
		currentDestroyUrl:new Array,//删除
		currentUpdateUrl:new Array,//修改
		currentReadUrl:new Array,//读取
		pushAddUrl:function(url){
			this.currentAddUrl.length=0;
			this.currentAddUrl[0]=url;
		},
		pushDestroyUrl:function(url){
			this.currentDestroyUrl.length=0;
			this.currentDestroyUrl[0]=url;
		},
		pushUpdateUrl:function(url){
			this.currentUpdateUrl.length=0;
			this.currentUpdateUrl[0]=url;
		},
		pushReadUrl:function(url){
			this.currentReadUrl.length=0;
			this.currentReadUrl[0]=url;
		},
		getAddUrl:function(){
			return this.currentAddUrl[0];
		},
		getDestroyUrl:function(){
			return this.currentDestroyUrl[0];
		},
		getUpdateUrl:function(){
			return this.currentUpdateUrl[0];
		},
		getReadUrl:function(){
			return this.currentReadUrl[0];
		}
		
	}
});