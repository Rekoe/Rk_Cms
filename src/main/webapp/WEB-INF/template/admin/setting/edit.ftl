<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.setting.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	$browserButton.browser();
	$inputForm.validate({
		rules: {
			siteName: "required",
			certtext: "required",
			siteCloseMessage: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.setting.edit" />
	</div>
	<form id="inputForm" action="update.rk" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="<@s.m "admin.setting.base" />" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "Setting.siteName" />:
				</th>
				<td>
					<input type="text" name="siteName" class="text" value="${obj.siteName}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "Setting.certtext" />:
				</th>
				<td>
					<input type="text" name="certtext" class="text" value="${obj.certtext}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "Setting.logo" />:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logo" class="text" value="" maxlength="200" />
						<input type="button" class="button browserButton" value="<@s.m "admin.browser.select" />" />
						<a href="/" target="_blank"><@s.m "admin.common.view" /></a>
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "Setting.isSiteEnabled" />:
				</th>
				<td>
					<input type="checkbox" name="siteEnabled" value="true"<#if obj.siteEnabled> checked="checked"</#if> />
					<input type="hidden" name="_siteEnabled" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "Setting.siteCloseMessage" />:
				</th>
				<td>
					<textarea name="siteCloseMessage" class="text">${obj.siteCloseMessage?html}</textarea>
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