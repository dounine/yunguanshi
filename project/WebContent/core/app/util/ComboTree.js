Ext.define("CC.util.ComboTree", {  
    extend: "Ext.form.field.Picker",  
    autoData:true,//是否自动加载数据
    alias:'widget.combotree',
    editable:false,
    initComponent: function() {  
        var self = this;  
        Ext.apply(self, {  
            fieldLabel: self.fieldLabel,  
            labelWidth: self.labelWidth  
        }); 
        if(this.autoData){
	        self.autoLoadData(this);
        }
          
        self.callParent();  
    },  
    createPicker: function() {  
        var self = this; 
        if(!this.autoData){
	        self.autoLoadData(this);
        }
        return self.picker;  
    },  
    autoLoadData:function(self){
    	var store = Ext.create('Ext.data.TreeStore', {  
        	//model:self.modelObj,
			root : {
				id: self.rootId,  
                text: self.rootText
			},
			proxy:{
				singleton : true, 
				type:'ajax',
				url:self.storeUrl,
				extraParams : this.extraParams,
				render:{
					type:'json'
				},
				autoLoad:true
			},
            listeners:{
            	'load':function(treeview, record, item, index, e, opts){
            		var roleidarrays = [];//选中角色列表id
					var roletextarrays = [];//选中角色列表文字
					console.log('role数据读取完毕');
					Ext.each(item,function(){
						if(this.get('checked')){//判断如是用户的角色信息
							roleidarrays.push(this.get('id'));
							roletextarrays.push(this.get('text'));
						}
					});
					self.setValue(roletextarrays.join(' | '));
            	}
            }//,
            //nodeParameter: self.treeNodeParameter  
        }); 
        self.picker = new Ext.tree.Panel({  
            height: 300,  
            autoScroll: true, 
            floating: true,  
            focusOnToFront: false,  
            shadow: true,  
            editable:this.editable,
            //ownerCt: this.ownerCt,  
            //useArrows: true,  
            store: store,  
            rootVisible: false  
        });  
        self.picker.on({ 
            checkchange: function(record, checked) {  
                var checkModel = self.checkModel;  
                if (checkModel == 'double') {  
                    var root = self.picker.getRootNode();  
                    root.cascadeBy(function(node) {  
                        if (node.get('text') != record.get('text')) {  
                            node.set('checked', false);  
                        }  
                    });  
                    if (record.get('leaf') && checked) {  
  
                        self.setRawValue(record.get('id')); // 隐藏值  
                        self.setValue(record.get('text')); // 显示值  
                    } else {  
                        record.set('checked', false);  
                        self.setRawValue(''); // 隐藏值  
                        self.setValue(''); // 显示值  
                    }  
                } else {  
  
                    var cascade = self.cascade;  
  
                    if (checked == true) {  
                        if (cascade == 'both' || cascade == 'child' || cascade == 'parent') {  
                            if (cascade == 'child' || cascade == 'both') {  
                                if (!record.get("leaf") && checked) record.cascadeBy(function(record) {  
                                    record.set('checked', true);  
                                });  
  
                            }  
                            if (cascade == 'parent' || cascade == 'both') {  
                                pNode = record.parentNode;  
                                for (; pNode != null; pNode = pNode.parentNode) {  
                                    pNode.set("checked", true);  
                                }  
                            }  
  
                        }  
  
                    } else if (checked == false) {  
                        if (cascade == 'both' || cascade == 'child' || cascade == 'parent') {  
                            if (cascade == 'child' || cascade == 'both') {  
                                if (!record.get("leaf") && !checked) record.cascadeBy(function(record) {  
  
                                    record.set('checked', false);  
  
                                });  
                            }  
  
                        }  
  
                    }  
  
                    var records = self.picker.getView().getChecked(),  
                    names = [],  
                    values = [];  
                    Ext.Array.each(records,  
                    function(rec) {  
                    	if(rec.get('text')!='root'){
	                        names.push(rec.get('text'));  
	                        values.push(rec.get('id'));  
                    	}
                    });  
                    self.setRawValue(values.join(',')); // 隐藏值  
                    self.setValue(names.join(' | ')); // 显示值  
                }  
  
            },  
            itemclick: function(tree, record, item, index, e, options) {  
                var checkModel = self.checkModel;  
  
                if (checkModel == 'single') {  
                    if (record.get('leaf')) {  
                        self.setRawValue(record.get('id')); // 隐藏值  
                        self.setValue(record.get('text')); // 显示值  
                    } else {  
                        self.setRawValue(''); // 隐藏值  
                        self.setValue(''); // 显示值  
                    }  
                }  
  
            }  
        });
    },
    alignPicker: function() {  
        var me = this,  
        picker, isAbove, aboveSfx = '-above';  
        if (this.isExpanded) {  
            picker = me.getPicker();  
            if (me.matchFieldWidth) {  
                picker.setWidth(me.bodyEl.getWidth());  
            }  
            if (picker.isFloating()) {  
                picker.alignTo(me.inputEl, "", me.pickerOffset); // ""->tl  
                isAbove = picker.el.getY() < me.inputEl.getY();  
                me.bodyEl[isAbove ? 'addCls': 'removeCls'](me.openCls + aboveSfx);  
                picker.el[isAbove ? 'addCls': 'removeCls'](picker.baseCls + aboveSfx);  
            }  
        }  
    }  
});