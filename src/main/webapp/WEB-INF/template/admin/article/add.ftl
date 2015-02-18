<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.add" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/tinyeditor/tinyeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.sel-disabled{background-color:#ccc}
</style>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			title: "required",
			articleCategoryId: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.article.add" />
	</div>
	<@p.form id="jvForm" action="save.rk" labelWidth="10" method="post" tableClass="input">
	<@p.text label="Article.title" colspan="2" id="title" name="title" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.tree label="Article.articleCategory" colspan="2" id="articleCategoryId" required="true" class="required" name="articleCategoryId" list=obj /><@p.tr/>
	<@p.shiroAuthor label="Article.author" colspan="2" id="letter.title" name="letter.title" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.radio width="50" colspan="2" label="admin.common.setting" id="art.publication" name="art.publication" value=0 list={"0":"Article.isPublication","1":"Article.isTop"}/><@p.tr/>
	<@p.editor value="" colspan="2" label="Article.content"  name="letter.content" required="true" /><@p.tr/>
	<@p.th />
	<@p.td colspan="" hasColon="false">
		<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
	</@p.td>
</@p.form>
</body>
</html>