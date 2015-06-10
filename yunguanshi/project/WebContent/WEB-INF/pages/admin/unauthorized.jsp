<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="images/yunguanshi.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GitHub开源项目授权</title>
<script type="text/javascript" src="js/jquery/jquery-1.10.1.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<body>
		<div style="width:600px;height:50px;margin:20% auto;font-size:50px;font-weight:bold;cursor:pointer;" onclick="javascript:location.href='index.html'">授权失败</div>
</body>
</html>