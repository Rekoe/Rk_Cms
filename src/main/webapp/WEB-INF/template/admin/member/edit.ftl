<#assign member=obj>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.member.edit" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
				pattern: /^[^\s&\"<>]+$/,
				minlength: 3,
				maxlength: 8
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
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.member.edit" />
	</div>
	<form id="inputForm" action="update.rk" method="post">
		<input type="hidden" name="id" value="${member.id}" />
		<table class="input tabContent">
			<tr>
				<th><@s.m "Member.username" />:</th>
				<td>${member.name}</td>
			</tr>
			<tr>
				<th><@s.m "Member.password" />:</th>
				<td><input type="password" id="password" name="password" class="text" maxlength="20" title="<@s.m "admin.member.passwordTitle" />" /></td>
			</tr>
			<tr>
				<th><@s.m "admin.member.rePassword" />:</th>
				<td><input type="password" name="rePassword" class="text" maxlength="20" /></td>
			</tr>
			<tr>
				<th><@s.m "admin.common.setting" />:</th>
				<td>
					<label>
						<input type="checkbox" name="isEnabled"<#if member.locked> checked="checked"</#if> value="true"><@s.m "Member.isEnabled" />
						<input type="hidden" name="_isEnabled" value="false" />
					</label>
				</td>
			</tr>
			<tr class="roles">
				<th><span class="requiredField">*</span><@s.m "Admin.roles" />:</th>
				<td>
					<span class="fieldSet">
						<#list roleList as role>
							<label>
								<input type="checkbox" name="roleIds" value="${role.id}" <#if member.roles?seq_contains(role)> checked="checked"</#if> />${role.name}
							</label>
						</#list>
					</span>
				</td>
			</tr>
			<tr>
				<th><@s.m "admin.common.createDate" />:</th>
				<td>${member.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
			</tr>
			<tr>
				<th><@s.m "Member.registerIp" />:</th>
				<td>${member.registerIp}</td>
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