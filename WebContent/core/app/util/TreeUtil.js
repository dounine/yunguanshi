Ext.define('CC.util.TreeUtil',{
	eachChildNode:function(node){
		var self = this;
		var list = new Ext.util.MixedCollection();//键值对集合
		list.add(node.get('id'),node);
		node.eachChild(function(n){
			var tempList = self.eachChildNode(n);
			tempList.eachKey(function(key){
				list.add(key,tempList.get(key));
			});
		});
		return list;
	}
});