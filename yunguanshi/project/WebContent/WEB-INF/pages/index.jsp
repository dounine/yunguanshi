<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>GitHub开源项目</title>
<link rel="Shortcut Icon" href="images/yunguanshi.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://localhost:8081/ext/resources/css/ext-all-neptune.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://localhost:8081/ext/examples/shared/example.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="css/icos.css" type="text/css" charset="utf-8" />
<script type="text/javascript" src="http://localhost:8081/ext/bootstrap.js"></script>
<script type="text/javascript" src="http://localhost:8081/ext/examples/shared/examples.js"></script>
<script src="js/jquery-1.10.1.js" type="text/javascript" charset="utf-8"></script>
<script src="http://localhost:8081/ext/locale/ext-lang-zh_CN.js"></script>
<script src="js/ext.js"></script>
<link rel="Shortcut Icon" href="images/yunguanshi.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery/jquery-1.10.1.js" charset="utf-8"></script>
<script type="text/javascript" src="js/FusionCharts.js" charset="utf-8"></script>

</head>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<body>
		<div id="box">
			<ul>
				<li class="left"><a href="register.html">注册用户</a></li>
			</ul>
			<ul>
				<li class="left"><a href="admin/index.html">用户登录</a></li>
			</ul>
			<ul>
				<li class="left"><a href="file/import.html">文件上传</a></li>
			</ul>
			<ul>
				<li class="left"><a href="excel/exce.html">Excel数据导出</a></li>
			</ul>
			<ul>
				<li class="left"><a href="excel/import.html">Excel数据导入</a></li>
			</ul>
			<ul>
				<li class="left"><a href="javascript:cc()">图表数据导入</a></li>
			</ul>
		</div>
		<div id="showFusion"></div>
		<script type="text/javascript">
		function cc(){
			$.post("fusion.html",function(data){
				alert(data);
			});
		}
			$(function(){
				var chart1 = new FusionCharts( "fusionchart/MSColumn3DLineDY.swf", "showFusion", "680", "380");
				//chart1.setDataXML("<chart baseFont='SunSim' baseFontSize='12' caption='分析' subcaption='' yAxisMinValue='0' xaxisname='地区 'yaxisname='数量' hovercapbg='FFECAA' hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='0' showvalues='1' numdivlines='10'numVdivlines='0' shownames='1' rotateNames='1'><categories font='Arial' fontSize='11' fontColor='000000'><category name='N. America' hoverText='North America' /><category name='Asia' /><category name='Europe' /><category name='Australia' /><category name='Africa' /></categories><dataset seriesname='Rice' color='FDC12E' alpha='100'><set value='30' /><set value='26' /><set value='29' /><set value='31' /><set value='34' /></dataset><dataset seriesname='Wheat' color='56B9F9' showValues='1'alpha='100'><set value='67' /><set value='98' /><set value='79' /><set value='73' /><set value='80' /></dataset></chart>"); 
				chart1.setDataXML("<chart caption='对比图' shownames='1' imageSave='1' imageSaveDialogFontColor ='cfbbfc'> <categories><category label='资产负债表(%25)' /><category label='负债与所有者权益比率(%25)' /> <category label='负债与有形净资产比率(%25)' /></categories> <dataset seriesName='2006' color='F6BD0F' showValues='1'><set value='60.9300' /> <set value='30.1900' /><set value='25.4900' /></dataset><dataset seriesName='2007' color='000000' showValues='1'><set value='68.4200' /><set value='45.8100' /><set value='66.8200' /></dataset><dataset seriesName='2008' color='8BBA00' showValues='1'><set value='53.34' /><set value='82.22' /><set value='91.21' /></dataset></chart>"); 
				chart1.render("showFusion");
			});
		</script>
</body>
</html>