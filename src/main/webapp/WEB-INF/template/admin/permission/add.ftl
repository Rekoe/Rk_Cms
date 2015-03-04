<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.permission.add" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			name: "required",
			permissionCategoryId: "required",
			description: "required"
		}
	});
});
</script>
<style>
.textbox { BACKGROUND: #FFFFFF; BORDER-TOP: #7F9DB9 1px solid; BORDER-LEFT: #7F9DB9 1px solid; BORDER-RIGHT: #7F9DB9 1px solid; BORDER-BOTTOM: #7F9DB9 1px solid; FONT-FAMILY: "宋体", "Verdana", "Arial", "Helvetica"; FONT-SIZE: 12px; TEXT-ALIGN: LIFT;}
</style>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.permission.add" />
	</div>
	<@p.form id="inputForm" action="save.rk" method="post" tableClass="input">
		<@p.text label="permission.name" id="name" name="name" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.td colspan="1" label="permission.permissionCategory"  required="true">
			<@p.select id="permissionCategoryId" name="permissionCategoryId" listKey="id" listValue="name" list=obj required="true"/>
		</@p.td><@p.tr/>
		<@p.textarea label="permission.description" id="description" name="description" cols="60" rows="4" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>