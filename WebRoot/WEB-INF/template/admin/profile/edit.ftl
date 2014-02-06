<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.profile.edit" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$.validator.addMethod("requiredTo", 
		function(value, element, param) {
			var parameterValue = $(param).val();
			if ($.trim(parameterValue) == "" || ($.trim(parameterValue) != "" && $.trim(value) != "")) {
				return true;
			} else {
				return false;
			}
		},
		"<@s.m "admin.profile.requiredTo" />"
	);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			currentPassword: {
				requiredTo: "#password",
				remote: {
					url: "check_current_password.rk",
					cache: false
				}
			},
			password: {
				pattern: /^[^\s&\"<>]+$/,
				minlength: 4,
				maxlength: 20
			},
			rePassword: {
				equalTo: "#password"
			}
		},
		messages: {
			password: {
				pattern: "<@s.m "admin.validate.illegal" />"
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.profile.edit" />
	</div>
	<form id="inputForm" action="update.rk" method="post">
		<table class="input">
			<tr>
				<th><@s.m "Admin.username" />: </th>
				<td><@shiro.principal property="name"/></td>
			</tr>
			<tr>
				<th><@s.m "admin.profile.currentPassword" />: </th>
				<td><input type="password" name="currentPassword" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th><@s.m "admin.profile.password" />: </th>
				<td><input type="password" id="password" name="password" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th><@s.m "admin.profile.rePassword" />: </th>
				<td><input type="password" name="rePassword" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><span class="tips"><@s.m "admin.profile.tips" /></span></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button" value="<@s.m "admin.common.submit" />" />
					<input type="button" id="backButton" class="button" value="<@s.m "admin.common.back" />" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>