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
	<form id="inputForm" action="save.rk" method="post">
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span><@s.m "permission.name" />:</th>
				<td><input type="text" name="name" class="text" maxlength="200" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span><@s.m "permission.permissionCategory" />:</th>
				<td>
					<select name="permissionCategoryId" >
						<#list obj as permissionCategory>
							<option value="${permissionCategory.id}">
								${permissionCategory.name}
							</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span><@s.m "permission.description" />:</th>
				<td><textarea name="description" cols="100" rows="10" class="textbox"></textarea></td>
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