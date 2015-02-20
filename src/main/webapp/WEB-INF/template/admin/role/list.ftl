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
	<form id="listForm" action="list.rk" method="get">
		<div class="bar">
			<a href="add.rk" class="iconButton"><span class="addIcon">&nbsp;</span><@s.m "admin.common.add" /></a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled"><span class="deleteIcon">&nbsp;</span><@s.m "admin.common.delete" /></a>
				<a href="javascript:;" id="refreshButton" class="iconButton"><span class="refreshIcon">&nbsp;</span><@s.m "admin.common.refresh" /></a>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><span><@s.m "admin.role.name" /></span></th>
				<th><span><@s.m "admin.role.description" /></span></th>
				<th><span><@s.m "admin.common.handle" /></span></th>
			</tr>
			<#list obj.list as role>
				<tr>
					<td><input type="checkbox" name="ids" value="${role.id}" /></td>
					<td>${role.name}</td>
					<td>${role.description}</td>
					<td><a href="edit.rk?id=${role.id}">[<@s.m "admin.common.edit" />]</a></td>
				</tr>
			</#list>
		</table>
		<@p.page value=obj/>
	</form>
</body>
</html>