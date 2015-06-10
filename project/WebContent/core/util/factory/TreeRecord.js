Ext.define('factory.TreeRecord',{
	statics:{
		treeRecords:new Ext.util.MixedCollection(),
		pushCurrent:function(record){
			this.treeRecords.clear();
			this.treeRecords.add(record);
		},
		getCurrent:function(){
			return this.treeRecords==null?null:this.treeRecords.get(0);
		}
	}
});