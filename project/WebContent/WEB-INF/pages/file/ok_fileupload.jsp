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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件成功</title>
<script type="text/javascript" src="js/jquery-1.10.1.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<body>
	
	<div style="width:600px;height:50px;margin:20% auto;font-size:50px;font-weight:bold;cursor:pointer;" onclick="javascript:location.href='index.html'">文件上传成功</div>

</body>
</html>