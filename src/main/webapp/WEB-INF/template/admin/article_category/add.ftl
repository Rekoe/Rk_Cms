<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.add" /> - Powered By Rekoe Cms</title>
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
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.articleCategory.add" />
	</div>
	<@p.form id="inputForm" action="save.rk" method="post" tableClass="input">
		<@p.text label="ArticleCategory.name" id="name" name="name" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.tree label="Article.articleCategory" name="ac.parentId" required="true" class="requireField" category=true/><@p.tr/>
		<@p.text label="admin.common.order" id="order" name="order" required="true" class="requireField" help="只允许输入零或正整数"/><@p.tr/>	
		<@p.fcolumn title="">
				<span class="tips"><span class="icon">&nbsp;</span>页面关键词、页面描述可以更好的使用户通过搜索引擎搜索到站点</span>
		</@p.fcolumn><#t/>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>