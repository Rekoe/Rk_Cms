<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.article.add" /> - Powered By Rekoe Cms</title>
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
			articleCategoryId: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.article.add" />
	</div>
	<form id="inputForm" action="save.rk" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "Article.title" />:
				</th>
				<td>
					<input type="text" name="title" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "Article.articleCategory" />:
				</th>
				<td>
					<select name="articleCategoryId" >
						<#list obj as articleCategory>
							<option value="${articleCategory.id}">
								<#if articleCategory.grade != 0>
									<#list 1..articleCategory.grade as i>
										&nbsp;&nbsp;
									</#list>
								</#if>
								${articleCategory.name}
							</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "Article.author" />:
				</th>
				<td>
					<input type="text" name="art.author" value="<@shiro.principal property="name"/>" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "admin.common.setting" />:
				</th>
				<td>
					<label>
						<input type="checkbox" name="art.publication" value="true" checked="checked" /><@s.m "Article.isPublication" />
						<input type="hidden" name="_art.publication" value="false" />
					</label>
					<label>
						<input type="checkbox" name="art.top" value="true" /><@s.m "Article.isTop" />
						<input type="hidden" name="_art.top" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "Article.content" />:
				</th>
				<td>
					<textarea id="editor" name="content" class="editor"></textarea>
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