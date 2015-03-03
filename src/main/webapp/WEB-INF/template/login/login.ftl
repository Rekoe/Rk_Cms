<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.login.login"/> - Powered By Rekoe</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="Rekoe Team" />
<meta name="copyright" content="Rekoe.Com" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript">
	$().ready( function() {
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "${base}/captcha.rk?&timestamp=" + (new Date()).valueOf());
		});
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "请输入用户名");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "请输入密码");
				return false;
			}
			if ($captcha.val() == "") {
				$.message("warn", "请输入验证码");
				return false;
			}
			$enPassword.val($password.val());
		});
		<#if obj??>
			$.message("error", "${obj}");
		</#if>
	});
</script>
</head>
<body>
	<div class="login">
		<form id="loginForm" action="${base}/admin/login.rk" method="post">
			<input type="hidden" id="enPassword" name="enPassword" />
			<input type="hidden" id="rememberMe" name="rememberMe" value="true" />
			<table>
				<tr>
					<td width="190" rowspan="2" align="center" valign="bottom"><img src="${base}/resources/admin/images/login_logo.gif" alt="Rekoe" /></td>
					<th><@s.m "login_name"/>:</th>
					<td><input type="text" id="username" name="username" class="text" maxlength="20" /></td>
				</tr>
				<tr>
					<th><@s.m "login_password"/>:</th>
					<td><input type="password" id="password" name ="password" class="text" maxlength="20" autocomplete="off" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<th><@s.m "login_captcha"/>:</th>
					<td><input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="${base}/captcha.rk" title="<@s.m "login_captcha"/>" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td><input type="button" class="homeButton" value="" onclick="location.href='${base}/'" /><input type="submit" class="loginButton" value="登陆" /></td>
				</tr>
			</table>
			<div class="powered">COPYRIGHT © 2005-2014 REKOE.COM ALL RIGHTS RESERVED.</div>
			<div class="link">
				<a href="${base}/"><@s.m "admin.path.index"/></a> |
				<a href="#"><@s.m "admin.login.official"/></a> |
				<a href="#"><@s.m "admin.login.bbs"/></a> |
				<a href="#"><@s.m "admin.login.about"/></a> |
				<a href="#"><@s.m "admin.login.contact"/></a> |
				<a href="#"><@s.m "admin.login.license"/></a>
			</div>
		</form>
	</div>
</body>
</html>