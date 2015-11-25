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
<title>云管事 后台管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="http://yunguanshi.qiniudn.com/images/ygs/yunguanshi.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/js/ext/4.2.1/resources/css/ext-all-neptune.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/js/ext/4.2.1/examples/ux/grid/css/progressColumn.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/js/ext/4.2.1/examples/shared/example.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/css/ygs/icos.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/css/ygs/icon.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="http://yunguanshi.qiniudn.com/css/ygs/admin/index.css" type="text/css" charset="utf-8" />
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/ext/4.2.1/bootstrap.js"></script>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/ext/4.2.1/examples/shared/examples.js"></script>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/ext/4.2.1/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/fusioncharts/fusioncharts/FusionCharts.js"></script>
<script src="core/app.js"></script>

</head>
<body>
<script>
		Ext.getBody().mask("页面初始化中...");
		Ext.onReady(function(){
			Ext.get('logout').on('click',function(){
				Ext.Msg.confirm('提示信息','确定要退出系统吗?',function(btn){
					if(btn=='yes'){
						window.location.href='user/logout.html';
					}
				});
			});
		});
	</script>
</body>
</html>