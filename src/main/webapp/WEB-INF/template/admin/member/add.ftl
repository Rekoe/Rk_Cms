<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.member.add" /> - Powered By Rekoe</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
		var $inputForm = $("#inputForm");
		$inputForm.validate({
		rules: {
			username: {
				required: true,
				pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
				minlength: 1,
				maxlength: 10,
				remote: {
					url: "check_username",
					cache: false
				}
			},
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: 1,
				maxlength: 10
			},
			rePassword: {
				required: true,
				equalTo: "#password"
			}
		},
		messages: {
			username: {
				pattern: "<@s.m "admin.validate.illegal" />",
				remote: "<@s.m "admin.member.disabledExist" />"
			},
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
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.member.add" />
	</div>
	<form id="inputForm" action="save" method="post">
		<ul id="tab" class="tab">
			<li><input type="button" value="<@s.m "admin.member.base" />" /></li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th><span class="requiredField">*</span><@s.m "Member.username" />:</th>
				<td><input type="text" name="username" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span><@s.m "Member.password" />:</th>
				<td><input type="password" id="password" name="password" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span><@s.m "admin.member.rePassword" />:</th>
				<td><input type="password" name="rePassword" class="text" maxlength="20" /></td>
			</tr>
			<tr class="roles">
				<th><span class="requiredField">*</span><@s.m "Admin.roles" />:</th>
				<td>
					<span class="fieldSet">
						<#list obj as role>
							<label><input type="checkbox" name="roleIds" value="${role.id}" />${role.name}</label>
						</#list>
					</span>
				</td>
			</tr>
			<tr>
				<th><@s.m "admin.common.setting" />:</th>
				<td>
					<label>
						<input type="checkbox" name="isEnabled" value="true" checked="checked" /><@s.m "Member.isEnabled" />
						<input type="hidden" name="_isEnabled" value="false" />
					</label>
				</td>
			</tr>
		</table>
		<table class="input">
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