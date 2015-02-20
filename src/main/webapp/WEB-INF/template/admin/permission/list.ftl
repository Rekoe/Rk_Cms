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
	<form id="listForm" action="list.rk" method="get">
		<div class="bar">
			<a href="add.rk" class="iconButton">
				<span class="addIcon">&nbsp;</span><@s.m "admin.common.add" />
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span><@s.m "admin.common.delete" />
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span><@s.m "admin.common.refresh" />
				</a>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value=" " maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"class="current" val="title"><@s.m "permission.title" /></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><span><@s.m "permission.name" /></span></th>
				<th><span><@s.m "permission.description" /></span></th>
				<th><span><@s.m "admin.common.handle" /></span></th>
			</tr>
			<#list obj.list as permission>
				<tr>
					<td><input type="checkbox" name="ids" value="${permission.id}" /></td>
					<td><span title="${permission.name}"><@htmlCut s=permission.name len=50 append='...' /></span></td>
					<td><span title="${permission.description}"><@htmlCut s=permission.description len=50 append='...' /></span></td>
					<td><a href="edit.rk?id=${permission.id}">[<@s.m "admin.common.edit" />]</a></td>
				</tr>
			</#list>
		</table>
		<@p.page value=obj/>
	</form>
</body>
</html>