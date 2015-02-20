<#assign member=obj>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.member.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
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
	<@p.form id="inputForm" action="" labelWidth="10" method="post" tableClass="input">
		<@p.hidden name="id" value="${member.id}" />
		<@p.text label="Member.username" colspan="2" value="${member.name}" id="username" name="username" required="true" class="required" maxlength="40" readonly="true"/><@p.tr/>
		<@p.password colspan="1" label="Member.password" id="password" name="password" maxlength="100" class="required" required="true"/><@p.tr/>
		<@p.password colspan="1" label="admin.member.rePassword" id="rePassword" name="rePassword" maxlength="100" class="required" required="true"/><@p.tr/>
		<@p.radio colspan="1" label="Member.isLocked" id="isEnabled" name="isEnabled" value=member.locked list={"false":"否","true":"是"}/><@p.tr/>
		<@shiro.hasPermission name="admin:role">
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
		</@shiro.hasPermission>
		<@p.text label="admin.common.createDate" colspan="2" value="${member.createDate?string('yyyy-MM-dd HH:mm:ss')}" id="createDate" name="createDate" required="true" class="required" maxlength="40" readonly="true"/><@p.tr/>
		<@p.text label="Member.registerIp" colspan="2" value="${member.registerIp}" id="registerIp" name="registerIp" required="true" class="required" maxlength="40" readonly="true"/><@p.tr/>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="update"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>