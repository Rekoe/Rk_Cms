<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.add" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	$('#submit').click(function() {
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
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.articleCategory.add" />
	</div>
	<@p.form id="inputForm" action="" method="post" tableClass="input">
		<@p.text label="ArticleCategory.name" id="acqu.name" name="acqu.name" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.tree label="分类" id="acqu.articleCategoryId" name="acqu.articleCategoryId" required="true" class="requireField" list=obj category=false/><@p.tr/>
		<@p.td colspan="1" width="50" label="页面编码"  required="true">
			<@p.select id="acqu.pageEncoding" name="acqu.pageEncoding" value='UTF-8' list={"GBK":"GBK","UTF-8":"UTF-8","gb2312":"gb2312"} required="true"/>
		</@p.td><@p.tr/>
		<@p.textarea label="采集地址" id="acqu.planUrl" name="acqu.planUrl" cols="70" rows="2" class="textbox" required="true" class="requireField" maxlength="240"/><@p.tr/>
		<@p.textarea label="链接开始" id="acqu.linksetStart" name="acqu.linksetStart" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.textarea label="链接结束" id="acqu.linksetEnd" name="acqu.linksetEnd" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.textarea label="标题开始" id="acqu.titleStart" name="acqu.titleStart" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.textarea label="标题结束" id="acqu.titleEnd" name="acqu.titleEnd" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.textarea label="内容开始" id="acqu.contentStart" name="acqu.contentStart" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.textarea label="内容结束" id="acqu.contentEnd" name="acqu.contentEnd" cols="40" rows="2" class="textbox" required="true" class="requireField" maxlength="40"/><@p.tr/>
		<@p.th />
		<@p.td colspan="" hasColon="false">
			<@p.submit code="admin.common.submit" id="submit"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
		</@p.td>
	</@p.form>
</body>
</html>