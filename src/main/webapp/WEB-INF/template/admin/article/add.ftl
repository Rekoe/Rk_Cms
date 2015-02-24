<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.add" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
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
	$('#submit').click(function() {
			editor.post();
			$.ajax({
				url : "save",
				type : "POST",
				data : $('#jvForm').serialize(),
				dataType : "json",
				cache : false,
				success : function(message) {
					$.message(message);
					if (message.type == "success") {
						window.location.href = "list"
					}
				}
			});
			return false;
		});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.article.add" />
	</div>
	<@p.form id="jvForm" action="" labelWidth="10" method="post" tableClass="input">
	<@p.text label="Article.title" colspan="2" id="art.title" name="art.title" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.tree label="Article.articleCategory" colspan="2" name="art.articleCategoryId" required="true" class="required" /><@p.tr/>
	<@p.shiroAuthor label="Article.author" colspan="2" id="art.author" name="art.author" required="true" class="required" maxlength="40"/><@p.tr/>
	<@p.radio width="50" colspan="2" label="Article.isTop" id="art.top" name="art.top" value="false" list={"false":"否","true":"是"}/><@p.tr/>
	<@p.radio width="50" colspan="2" label="Article.isPublication" id="art.publication" name="art.publication" value="true" list={"false":"否","true":"是"}/><@p.tr/>	
	<@p.editor value="" colspan="2" label="Article.content"  name="art.content" required="true" /><@p.tr/>
	<@p.th />
	<@p.td colspan="" hasColon="false">
		<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
	</@p.td>
</@p.form>
</body>
</html>