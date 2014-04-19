<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.permissionCategory.list" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
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
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.permissionCategory.list" />
	</div>
	<div class="bar">
		<a href="add.rk" class="iconButton"><span class="addIcon">&nbsp;</span><@s.m "admin.common.add" /></a>
		<a href="javascript:;" id="refreshButton" class="iconButton"><span class="refreshIcon">&nbsp;</span><@s.m "admin.common.refresh" /></a>
	</div>
	<table id="listTable" class="list">
		<tr>
			<th><span><@s.m "permissionCategory.name" /></span></th>
			<th><span><@s.m "admin.common.handle" /></span></th>
		</tr>
		<#list obj.list as permissionCategory>
			<tr>
				<td><span style="margin-left: 40px;">${permissionCategory.name}</span></td>
				<td>
					<a href="edit.rk?id=${permissionCategory.id}">[<@s.m "admin.common.edit" />]</a>
					<a href="javascript:;" class="delete" val="${permissionCategory.id}">[<@s.m "admin.common.delete" />]</a>
					<a href="${base}/admin/permission/list_category/${permissionCategory.id}.rk">[<@s.m "admin.common.view" />]</a>
				</td>
			</tr>
		</#list>
	</table>
		<@p.page value=obj/>
</body>
</html>