Ext.define('CC.util.ComboboxTree',{  
    extend : 'Ext.form.field.ComboBox',  
    alias: 'widget.ComboboxTree',  
    editable : false,  
    allowBlank:true,  
    listConfig : {resizable:false,minWidth:200,maxWidth:450},  
    callback : Ext.emptyFn,  
    treeObj : null, 
    ids:[],
    ownerId:'',
    rootUrl:'',
    modelObj:'',
    value:'',
    isClick:false,
    extraParams:{checkIds:[]},
    initComponent : function(){  
    	var self = this;
    	var store = Ext.create('Ext.data.TreeStore', {
				autoLoad:false,
				root : {
					id : null,
					text : '根节点',
					expanded:true
				},
				proxy:{
					type:'ajax',
					url:self.url,
					extraParams : this.extraParams,
					render:{
						type:'json'
					}
				},
				listeners:{
					'load':function(){
						console.log('我去服务器读取数据回来了');					
					}
				}
            });
            self.treeObj = Ext.create('Ext.tree.Panel', {
                rootVisible: false,
                autoScroll: true,
                expanded:false,
                height: 200,
                store: store,
				listeners:{
					'reload':function(url){
						console.log('去服务器加载数据');
						this.store = store;
						this.store.proxy.url=url;
						this.store.reload();
					}
				}
            });
        self.treeRenderId = Ext.id();  
        self.tpl = "<tpl><div id='"+this.treeRenderId+"'></div></tpl>";       
        self.on({  
            'expand' : function(){  //展开
                if(!self.treeObj.rendered&&self.treeObj&&!self.readOnly){  
                    Ext.defer(function(){  
                        self.treeObj.render(self.treeRenderId);  
                    },100,this);  
                }  
        	}
    	});  
        self.treeObj.on('itemclick',function(view,node){
        	self.isClick = true;
           node.set('checked',!node.get('checked'));
           var checked = node.get('checked');
           var eachParent = function(node,checked){
           		if(!node.isRoot()&&checked){
	           		if(!Ext.isEmpty(node.get('checked'))){
	           			node.set('checked',checked);
	           		}
           			eachParent(node.parentNode,checked);
           		}
           };
           eachParent(node,node.get('checked'));
           var eachChild = function(node,checked){
           		node.eachChild(function(n){
           			if(!Ext.isEmpty(n.get('checked'))){
           				n.set('checked',checked);
           			}
           			eachChild(n,checked);
           		});
           };
           eachChild(node,checked);
           
           var checks = self.treeObj.getChecked();//获取所有选中的节点
           var ids = new Array();//保存选中节点中id值
           var list = new Array();//保存选中节点文件的值
           Ext.each(checks,function(node){
           	ids.push(node.get('id'));//把值压到数组中去
           	list.push(node.get('text'));
           });
           Ext.apply(self,{'ids':ids});//对com
           this.ids = ids;//给当前树形控件赋值
           self.setValue(list.join(' | '));
        },self);
        
        self.callParent(arguments);
    }
});