<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.article.list" /> <span>(<@s.ma "admin.page.total" "${obj.totalCount}"/>)</span>
	</div>
	<@p.list id="listForm" action="list.rk" method="get">
		<@p.table value=obj.list;article,i,has_next><#rt/>
			<@p.column code=" " align="center" class="check" selectAll=true addSpan=false><input type="checkbox" name="ids" value="${article.id}" /></@p.column><#t/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column code="Article.title" align="center"><@htmlCut s=article.title len=50 append='...' /></@p.column><#t/>
			<@p.column code="Article.articleCategory" align="center"><a href="${base}/article/list/${article.articleCategory.id}.rk" target="_blank">${article.articleCategory.name}</a></@p.column><#t/>			
			<@p.column code="Article.isPublication" align="center" iconClass="${article.publication?string('true', 'false')}Icon">&nbsp;</@p.column><#t/>
			<@p.column code="admin.common.createDate" align="center">${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</@p.column><#t/>				
			<@p.column code="admin.common.handle" align="center"><a href="edit.rk?id=${article.id}">[<@s.m "admin.common.edit" />]</@p.column><#t/>			
			<#t/>
		</@p.table>
		<@p.page value=obj/>
	</@p.list>
</body>
</html>