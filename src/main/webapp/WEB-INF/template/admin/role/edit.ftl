<#assign role=obj>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.role.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<style type="text/css">
.authorities label {
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;
}
</style>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	var $selectAll = $("#inputForm .selectAll");
	$selectAll.click(function() {
		var $this = $(this);
		var $thisCheckbox = $this.closest("tr").find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.prop("checked", false);
		} else {
			$thisCheckbox.prop("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			authorities: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.role.edit" />
	</div>
	<form id="inputForm" action="update" method="post">
		<input type="hidden" name="role.id" value="${role.id}" />
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span><@s.m "admin.role.name" />:</th>
				<td><input type="text" name="name" class="text" value="${role.name}" maxlength="200" /></td>
			</tr>
			<tr>
				<th><@s.m "admin.role.description" />:</th>
				<td><input type="text" name="role.description" class="text" value="${role.description}" maxlength="200" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<#list pcList as permissionCategory>
			<tr class="authorities">
				<th><a href="javascript:;" class="selectAll" title="<@s.m "admin.role.selectAll" />">${permissionCategory.name}</a></th>
				<td>
					<span class="fieldSet">
					<#list permissionCategory.permissions as permission>
						<label><input value="${permission.id}" type="checkbox" 
						<#list obj.permissions as p>
							<#if permission.id = p.id>
							 checked="checked"
							<#break>
							</#if>
						</#list>
						name="authorities">${permission.description} </label>
					</#list>
					</span>
				</td>
			</tr>
			</#list>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button" value="<@s.m "admin.common.submit" />"/>
					<input type="button" id="backButton" class="button" value="<@s.m "admin.common.back" />" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>