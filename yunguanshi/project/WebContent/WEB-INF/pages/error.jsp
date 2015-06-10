<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>云管事 系统错误</title>
<link rel="Shortcut Icon" href="images/yunguanshi.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery/jquery-1.10.1.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<body style="background:#88888;">
	
	<div style="width:580px;height:500px;margin:4% auto;position:relative;background:url(images/404.png) no-repeat;" >
		<font onclick="javascript:location.href='admin/index.html'" style="display:block;width:100px;height:30px;cursor:pointer;position:absolute;top:90px;right:50px;"></font>
	</div>

</body>
</html>