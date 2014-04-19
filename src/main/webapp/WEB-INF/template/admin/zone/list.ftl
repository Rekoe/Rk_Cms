<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.zone.list" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.zone.list" /> <span>(<@s.ma "admin.page.total" "${obj.totalCount}"/>)</span>
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
							<a href="javascript:;"class="current" val="title"><@s.m "Article.title" /></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><span><@s.m "zone.name" /></span></th>
				<th><span><@s.m "zone.isFresh" /></span></th>
				<th><span><@s.m "zone.serverid" /></th>
				<th><span><@s.m "admin.common.handle" /></span></th>
			</tr>
			<#list obj.list as zone>
				<tr>
					<td><input type="checkbox" name="ids" value="${zone.id}" /></td>
					<td><span title="${zone.name}">${zone.name}</span></td>
					<td><span class="${zone.fresh?string("true", "false")}Icon">&nbsp;</span></td>
					<td><span title="${zone.serverid}">${zone.serverid}</span></td>
					<td>
						<a href="edit.rk?id=${zone.id}">[<@s.m "admin.common.edit" />]</a>
					</td>
				</tr>
			</#list>
		</table>
		<@p.page value=obj/>
	</form>
</body>
</html>