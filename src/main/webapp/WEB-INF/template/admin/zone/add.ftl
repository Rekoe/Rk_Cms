<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.zone.add" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			title: "required",
			zoneCategoryId: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.zone.add" />
	</div>
	<form id="inputForm" action="save.rk" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "zone.name" />:
				</th>
				<td>
					<input type="text" name="zone.name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "zone.code" />:
				</th>
				<td>
					<input type="text" name="zone.serverid" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "zone.url" />:
				</th>
				<td>
					<input type="text" name="zone.url" class="text" maxlength="1000" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "zone.status" />:
				</th>
				<td>
					<select name="zone.status" >
						<#list  ["繁忙" ,"拥挤" ,"畅通" ,"维护"] as status>
							<option value="${status_index}" <#if status_index=2>selected="selected"</#if>>${status}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "admin.common.recommend" />:
				</th>
				<td>
					<label>
						<input type="checkbox" name="zone.recommend" value="true" checked="checked" /><@s.m "admin.common.recommend" />
						<input type="hidden" name="_zone.recommend" value="false" />
					</label>
					<label>
						<input type="checkbox" name="zone.fresh" value="true" /><@s.m "admin.common.fresh" />
						<input type="hidden" name="_zone.fresh" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="<@s.m "admin.common.submit" />" />
					<input type="button" id="backButton" class="button" value="<@s.m "admin.common.back" />" />
				</td>
			</tr>
		</table>
	</form>
	<script>
KindEditor.ready(function(K) {
		editor = K.create("#editor", {
			height: "350px",
			items: [
				"source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste",
				"plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright",
				"justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript",
				"superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/",
				"formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold",
				"italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image",
				"flash", "media", "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak",
				"anchor", "link", "unlink"
			],
			langType: rkcms.locale,
			syncType: "form",
			filterMode: false,
			pagebreakHtml: '<hr class="pageBreak" \/>',
			allowFileManager: true,
			filePostName: "file",
			fileManagerJson: rkcms.base + "/admin/file/browser.rk",
			uploadJson: rkcms.base + "/admin/file/upload.rk",
			extraFileUploadParams: {
				token: "token"
			},
			afterChange: function() {
				this.sync();
			}
		});
	});
	</script>
</body>
</html>