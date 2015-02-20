<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.role.add" /> - Powered By Rekoe Cms</title>
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
			authorities: "required",
			description : "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.role.add" />
	</div>
	<@p.form id="inputForm" action="save.rk" method="post" tableClass="input">
		<@p.text label="admin.role.name" id="name" name="name" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.text label="admin.role.description" id="description" name="description" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.td colspan="2">
		</@p.td><@p.tr/>
		<#list obj as permissionCategory>
		<tr class="authorities">
			<th><a href="javascript:;" class="selectAll" title="<@s.m "admin.role.selectAll" />">${permissionCategory.name}</a></th>
			<td>
				<span class="fieldSet">
				<#list permissionCategory.permissions as permission>
					<label><input value="${permission.id}" type="checkbox" name="authorities">${permission.description} </label>
				</#list>
				</span>
			</td>
		</tr>
		</#list>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>