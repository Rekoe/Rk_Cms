<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.permission.edit" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			name: "required",
			description: "required"
		}
	});
});
</script>
<style>
.textbox { BACKGROUND: #FFFFFF; BORDER-TOP: #7F9DB9 1px solid; BORDER-LEFT: #7F9DB9 1px solid; BORDER-RIGHT: #7F9DB9 1px solid; BORDER-BOTTOM: #7F9DB9 1px solid; FONT-FAMILY: "宋体", "Verdana", "Arial", "Helvetica"; FONT-SIZE: 12px; TEXT-ALIGN: LIFT;}
</style>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.permission.edit" />
	</div>
	<form id="inputForm" action="update.rk" method="post">
		<input type="hidden" name="permission.id" value="${permission.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "permission.name" />:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${permission.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "permission.permissionCategory" />:
				</th>
				<td>
					<select name="permission.permissionCategoryId" >
						<#list obj as permissionCategory>
							<option value="${permissionCategory.id}"<#if permission.permissionCategoryId == permissionCategory.id> selected="selected"</#if>>
								${permissionCategory.name}
							</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "permission.description" />:
				</th>
				<td>
					<textarea name="description" cols="100" rows="10" class="textbox">${permission.description?html}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="<@s.m "admin.common.submit" />" />
					<input type="button" id="backButton" class="button" value="<@s.m "admin.common.back" />" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>