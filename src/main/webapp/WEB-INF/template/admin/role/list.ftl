<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.role.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.role.list" /> <span>(<@s.ma "admin.page.total" "${obj.totalCount}"/>)</span>
	</div>
	<@p.list id="listForm" action="list.rk" method="get">
		<@p.table value=obj.list;role,i,has_next><#rt/>
			<@p.column code=" " align="left" class="check" selectAll=true addSpan=false><input type="checkbox" name="ids" value="${role.id}" /></@p.column><#t/>
			<@p.column code="ID" align="left">${i+1}</@p.column><#t/>
			<@p.column code="admin.role.name" align="left">${role.name}</@p.column><#t/>
			<@p.column code="admin.role.description" align="left">${role.description}</@p.column><#t/>
			<@p.column code="admin.common.handle" align="left">
				<a href="edit.rk?id=${role.id}">[<@s.m "admin.common.edit" />]</a></@p.column><#t/>
			<#t/>
		</@p.table>
		<@p.page value=obj/>
	</@p.list>
</body>
</html>