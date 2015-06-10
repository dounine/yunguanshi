 Ext.define('CC.view.index.panel', {
	extend:Ext.panel.Panel,
	layout : 'border',
	title:'首页',
	html:'<div style="width:100%;height:100%;background:url(http://yunguanshi.qiniudn.com/images/ygs/admin/home.png) center right no-repeat;"><p style="font-size:20px;margin-top:30px;margin-left:20px;font-weight:bold;color:#666666;font-family:宋体;">欢迎使用GitHub项目<p></div>',
	bodyStyle:'background:#fff;',
	alias:'widget.indexpanel',
	iconCls:'table_home',
	layout:'border',
	items:[{
		region : 'north',
		html : '<p style="font-size:20px;margin-top:30px;margin-left:20px;font-weight:bold;color:#666666;font-family:宋体;">欢迎使用云管事<p>',
		border : false
	}, {
		region : 'west',
		split : true,
		border : false
	}, {
		region : 'center',
		id:'showFusion',
		html:'<div id="showFusion" style="z-index:0;"></div>',
		margins:'40 0 0 0',
		border : true,
		listeners:{
			afterrender:function(){
				var chart = new FusionCharts( "http://yunguanshi.qiniudn.com/js/fusioncharts/MSColumn3DLineDY.swf", "showFusion", "680", "380");
				chart.setDataXML("<chart caption='用户访问量情况' shownames='1' imageSave='1' imageSaveDialogFontColor ='cfbbfc'> <categories><category label='2014-08-15' /><category label='2014-07-16' /> <category label='2014-08-17' /></categories> <dataset seriesName='早上' color='F6BD0F' showValues='1'><set value='60' /> <set value='30' /><set value='25' /></dataset><dataset seriesName='下午' color='000000' showValues='1'><set value='68' /><set value='45' /><set value='66' /></dataset><dataset seriesName='夜间' color='8BBA00' showValues='1'><set value='53' /><set value='82' /><set value='91' /></dataset></chart>");
				chart.render('showFusion');//渲染到哪个id上
			}
		}
		
	}, {
		region : 'east',
		width : 480,
		border:false,
		html:'<div style="width:600px;height:400px;background:url(http://yunguanshi.qiniudn.com/images/ygs/admin/home.png) left center  no-repeat;"></div>'
	}]
});
