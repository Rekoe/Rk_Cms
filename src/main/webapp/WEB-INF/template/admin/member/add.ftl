<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.member.add" /> - Powered By Rekoe</title>
<#include "/template/admin/common/head.ftl" />
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
	<@p.form id="inputForm" action="save.rk" method="post" tableClass="input">
		<@p.text label="Member.username" colspan="2" id="username" name="username" required="true" class="required" maxlength="40"/><@p.tr/>
		<@p.password colspan="1" label="Member.password" id="password" name="password" maxlength="100" class="required" required="true"/><@p.tr/>
		<@p.password colspan="1" label="admin.member.rePassword" id="rePassword" name="rePassword" maxlength="100" class="required" required="true"/><@p.tr/>
		<@p.radio colspan="1" label="Member.isLocked" id="isEnabled" name="isEnabled" value="true" list={"false":"否","true":"是"}/><@p.tr/>
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
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>