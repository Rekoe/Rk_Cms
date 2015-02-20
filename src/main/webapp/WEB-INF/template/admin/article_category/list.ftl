<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.list" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$().ready(function() {
	var $delete = $("#listTable a.delete");
	$delete.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "<@s.m "admin.dialog.deleteConfirm" />",
			onOk: function() {
				$.ajax({
					url: "delete.rk",
					type: "POST",
					data: {id: $this.attr("val")},
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							$this.closest("tr").remove();
						}
					}
				});
			}
		});
		return false;
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.articleCategory.list" />
	</div>
	<@p.list id="listForm" action="list.rk" method="get">
		<@p.table value=obj;articleCategory,i,has_next><#rt/>
			<@p.column code=" " align="center" class="check" selectAll=true addSpan=false><input type="checkbox" name="ids" value="${articleCategory.id}" /></@p.column><#t/>
			<@p.column code="ID" align="center">${i+1}</@p.column><#t/>
			<@p.column code="ArticleCategory.name" align="center">${articleCategory.name}</@p.column><#t/>
			<@p.column code="admin.common.order" align="center">${articleCategory.order}</@p.column><#t/>			
			<@p.column code="admin.common.handle" align="center">
				<a href="edit.rk?id=${articleCategory.id}">[<@s.m "admin.common.edit" />]</a>
				<a href="javascript:;" class="delete" val="${articleCategory.id}">[<@s.m "admin.common.delete" />]</a>
				<a href="${base}/article/list/${articleCategory.id}.rk" target="_blank">[<@s.m "admin.common.view" />]</a>
			</@p.column><#t/>
			<#t/>
		</@p.table>
	</@p.list>
</body>
</html>