<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.permission.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.permission.list" /> <span>(<@s.ma "admin.page.total" "${obj.totalCount}"/>)</span>
	</div>
	<@p.list id="listForm" action="list.rk" method="get">
		<@p.table value=obj;permission,i,has_next><#rt/>
			<@p.column code=" " align="center" class="check" selectAll=true addSpan=false><input type="checkbox" name="ids" value="${permission.id}" /></@p.column><#t/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column code="permission.name" align="center"><@htmlCut s=permission.name len=50 append='...' /></@p.column><#t/>
			<@p.column code="permission.description" align="center"><@htmlCut s=permission.description len=50 append='...' /></@p.column><#t/>
			<@p.column code="admin.common.handle" align="center">
				<a href="edit.rk?id=${permission.id}">[<@s.m "admin.common.edit" />]</a>
			</@p.column><#t/>
			<#t/>
		</@p.table>
		<@p.page value=obj/>
	</@p.list>
</body>
</html>