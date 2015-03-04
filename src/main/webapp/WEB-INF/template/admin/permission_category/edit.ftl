<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.permissionCategory.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			name: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.permissionCategory.edit" />
	</div>
	<@p.form id="inputForm" action="update.rk" method="post" tableClass="input">
		<@p.hidden name="id" value="${obj.id}" />
		<@p.text label="permissionCategory.name" id="name" name="name" value="${obj.name}" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>