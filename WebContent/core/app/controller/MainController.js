/**
 * 程序控制器
 */
Ext.define('CC.controller.MainController', {
	extend : Ext.app.Controller,
	requires : [
		'CC.util.GridActionUtil',
		'CC.util.SuperUtil',
		'baseUx.form.datetime.DateTimePicker',
     	'baseUx.form.datetime.DateTime',
		'factory.Model',
		'factory.TreeRecord',
		'factory.Url',
		'CC.util.ComboboxTree'
	],
	views : [
		'content.tree'
	],
	ctr : {},
	init : function() {
		this.control({
			'contenttree' : {
				itemclick : this.loadFunction
			}
		});
	},
	loadFunction:function(view,rec,item,index,eventObj){
		var self = this;
		var tabs = Ext.getCmp('tabs');//获取主选项卡
		var tabId = 'functionTab'+rec.get('functionId');//获取记录的的值
		var tab = tabs.queryById(tabId);//根据id获取选项卡
		if(null!=tab){
			 tabs.setActiveTab(tab);//激活选项卡
		}else{
			tabs.getEl().mask('加载中...');
			Ext.require('CC.controller.'+rec.get('functionController'),function() {//加载须要的控制器
					Ext.require(rec.get('functionPanel'),function(){//加载需要的控制面板
						self.getController(rec.get('functionController'));//控制器导入
						var tab = Ext.create(rec.get('functionPanel'),{//创建tablpanel
							title:rec.get('text'),
							id:tabId,
							iconCls:rec.get('iconCls')
							});
						tabs.insert(1,tab);//添加到第一位中
						tabs.setActiveTab(tab);//激活
						tabs.unmask();//等待取消
					});
				});
		}
	}
});