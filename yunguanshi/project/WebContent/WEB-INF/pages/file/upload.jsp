<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="css/index.css" />

<body>
	<SCRIPT type=text/javascript>
		function doProgressLoop(prog, max, counter) {
			var x = document.getElementById('progress-content').innerHTML;
			var y = parseInt(x);
			if (!isNaN(y)) {
				prog = y;
			}
			counter = counter + 1;
			if (prog < 100) {
				setTimeout("getProgress()", 1000);
				setTimeout("doProgressLoop(" + prog + "," + max + "," + counter
						+ ")", 1500);
				document.getElementById('progressBarText').innerHTML = 'upload in progress: '
						+ prog + '%';
				document.getElementById('progressBarBoxContent').style.width = parseInt(prog)
						+ '%';

			}
		}

		function getProgress() {
			$.ajax({
						type : "post",
						dataType : "string",
						url : "file/progress.html",
						data : "",
						success : function(data) {
							document.getElementById('progress-content').innerHTML = data;
						},
						error : function(err) {
							document.getElementById('progressBarText').innerHTML = "Error retrieving progress";
						}
					});
		}

		function fSubmit() {
			var button = window.document.getElementById("submitButton");
			button.disabled = true;
			var max = 100;
			var prog = 0;
			var counter = 0;
			document.getElementById('progressBar').style.display = 'block';
			document.getElementById('progressBarText').innerHTML = 'upload in progress: 0%';
			getProgress();
			doProgressLoop(prog, max, counter);
			document.getElementById("form1").submit();
		}
	</SCRIPT>
	<form name="form1" id="form1" action="file/import.html" method="post"
		enctype="multipart/form-data">

		<span>选择文件</span>
		<div style="padding-bottom: 5px;">
			<input type="file" size="48" name="file" id='filedata'>
		</div>

		<div id="progressBar" style="display: none;">

			<div id="theMeter">
				<div id="progressBarText"></div>

				<div id="progressBarBox"
					style="color: Silver; border-width: 1px; border-style: Solid; width: 300px; TEXT-ALIGN: left">
					<div id="progressBarBoxContent"
						style="background-color: #3366FF; height: 15px; width: 0%; TEXT-ALIGN: left"></div>
				</div>
				<div id="progress-content" style="display: none;"></div>
			</div>
		</div>
		<INPUT id='submitButton' tabIndex=3 type=button value="提交"
			onclick='fSubmit();'>
	</form>
	</body>