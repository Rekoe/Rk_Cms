<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.edit" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.articleCategory.edit" />
	</div>
	<@p.form id="inputForm" action="" labelWidth="10" method="post" tableClass="input">
		<@p.hidden name="category.id" value="${obj.id}" />
		<@p.text label="ArticleCategory.name" value="${obj.name}" id="category.name" name="category.name" required="true" class="required" maxlength="40"/><@p.tr/>
		<@p.tree label="ArticleCategory.parent" colspan="2" name="category.parentId" required="true" value="${obj.id}" class="required"/><@p.tr/>			
		<@p.text label="admin.common.order" id="category.order" name="category.order" value="${obj.order}" required="true" class="requireField" help="只允许输入零或正整数"/><@p.tr/>
		<@p.fcolumn title="">
			<span class="tips"><span class="icon">&nbsp;</span>页面关键词、页面描述可以更好的使用户通过搜索引擎搜索到站点</span>
		</@p.fcolumn><#t/>		
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="update"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
		</@p.form>
</body>
</html>