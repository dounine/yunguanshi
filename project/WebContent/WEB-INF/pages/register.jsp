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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云管事 用户注册</title>
<link rel="Shortcut Icon" href="http://yunguanshi.qiniudn.com/images/ygs/yunguanshi.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="http://yunguanshi.qiniudn.com/css/ygs/login.css"/>
<link rel="stylesheet" type="text/css" href="http://yunguanshi.qiniudn.com/css/plugins/dounine-dialog.css"/>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/jquery/1.10.1/jquery-1.10.1.js"></script>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/ext/yunguanshi/login.js"></script>
<script type="text/javascript" src="http://yunguanshi.qiniudn.com/js/plugins/dounine-dialog.js"></script>
<script type="text/javascript">
function changeImg() { 
    var imgSrc = $("#imgObj");
    imgSrc.attr("src", chgUrl("captcha.html")); 
} 
function chgUrl(url) { 
    var timestamp = (new Date()).valueOf(); 
    if ((url.indexOf("&") >= 0)) { 
        url = url + "×tamp=" + timestamp; 
    } else { 
        url = url + "?timestamp=" + timestamp; 
    } 
    return url; 
}
var timelose = false;
var registerButton = true;
var dounineDialog = new BlackBox({
	'clickOverlayEffect' : 'shake', // 点击覆盖层效果，默认为抖动 'shake'，可选关闭 'close'
	'overlayColor' : '#000', // 覆盖层颜色，默认为黑色 #000
	'overlayOpacity' : .3, // 覆盖层透明度，默认为 .6
	'allowPromptBlank' : false, // 允许prompt时输入内容为空，默认否，即为空时提交会抖动
	'displayClose' : false, // 在alert,confirm和prompt模式中显示关闭按钮
	'enableKeyPress' : true
	// 使用快捷键确定和取消
});
function kaptchaPutClick(){
		if(registerButton){
			if($('#username').val().trim().length==0){
				$('#errormsg').text('邮箱不能为空');
				$('#username').css({
					'border-color':'#FA7C7C'
				});
				return;
			}
			if($('#password').val().trim().length==0){
				$('#errormsg').text('密码不能为空');
				$('#password').css({
					'border-color':'#FA7C7C'
				});
				return;
			}
			$('#username').css({
				'border-color':'#dddddd'
			});
			$('#password').css({
				'border-color':'#dddddd'
			});
			registerButton = false;
			$('#kaptchaPut').css({
				'background':'#ccc'
			}).val('注册中...');
			$.post('user/register.html',
					{'email':$('#username').val(),'password':$('#password').val(),'captcha':$('#captcha').val()},
					function(data){
						var obj = eval('(' + data + ')');
						if(obj.success){
							dounineDialog.alert('恭喜您，通过邮箱注册成功。', function () {
								registerButton = true;
								 $('#kaptchaPut').css({
									'background':'#3F89EC'
								}).val('注册');
								 window.location.href='admin/login.html';
						    }, {
						        title: '系统提示',
						        value: '确定'
						    });
						}else{
							dounineDialog.alert(obj.info, function () {
								registerButton = true;
								 $('#kaptchaPut').css({
									'background':'#3F89EC'
								}).val('注册'); 
						    }, {
						        title: '系统提示',
						        value: '确定'
						    });
						}
						
					}
				);
		} 
}
$(function(){
	$("#KaptchaValidate").click(function(){
		if($('#username').val().trim().length==0){
			$('#errormsg').text('邮箱不能为空');
			$('#username').css({
				'border-color':'#FA7C7C'
			});
			return;
		}
		$('#username').css({
			'border-color':'#dddddd'
		});
		if(!timelose){
			var time = 60;
			var $self = $(this);
			$self.css({
				'color':'#ccc',
				'cursor':'default'
			});
			$self.text('验证信息发送中...');
			$.post('util/sendmail.html',
				{'email':$('#username').val()},
				function(data){
					if(eval('(' + data + ')').success){
						$self.text(time+'秒后可重新获取');
						var tt = setInterval(function(){
							time--;
							$self.text(time+' 秒后可重新获取');
							if(time<=0){
								$self.css({
									'color':'#1B66C7',
									'cursor':'pointer'	
									});
								$self.text('重新获取验证码');
								clearInterval(tt);
								timelose = false;
							}
						},1000);
					}else{
						dounineDialog.alert(eval('(' + data + ')').info, function () {
							$self.text('系统锁定无法发送');
					    }, {
					        title: '系统提示',
					        value: '确定'
					    });
						//$('#errormsg').text(eval('(' + data + ')').info);
					}
				}
			);
		}
		timelose = true;
	});
});

</script>
</head>
<body>
	<div id="register">
		<ul id="register_top">
			<li>用户注册</li>
		</ul>
		<ul id="register_form">
			<li><label class="register-label">邮&nbsp;箱</label><input id="username" name="username" value="${user.username}" class="username" autocomplete ="off" type="text" placeholder="邮&nbsp;箱" /></li>
			<li><label class="register-label">密&nbsp;码</label><input id="password" name="password" type="password" value="" class="password" autocomplete ="off" type="text" placeholder="密&nbsp;码" /></li>
			<li id="register_captcha"><label class="register-label">验证码</label><input id="captcha" name="captcha" class="captcha" autocomplete ="off" type="text" placeholder="验证码" /><img style="display: none;" id="imgObj" width="90" height="42" src="captcha.html" /><a style="width:120px;height:38px;text-align:center;line-height:38px;border:1px solid #ccc;margin-left:18px;" href="javascript:void(0)" id="KaptchaValidate" >免费获取验证码</a></li>
		</ul>
		<ul id="register_protocol">
			<li><input type="checkbox" id="checkbox" checked="checked" /><label for="checkbox">我已阅读并接受《<a href="#">云管事用户协议</a>》</label></li>
		</ul>
		<ul id="register_submit">
			<li><input type="button" id="kaptchaPut" onclick="kaptchaPutClick()" value="注册" /></li>
			<li><p id="errormsg" style="color:#FA5F5F;">${authenticationErrorMessage}</p><a href="admin/index.html">立即登录</a></li>
		</ul>
	</div>
</body>
</html>