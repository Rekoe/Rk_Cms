<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.articleCategory.edit" /> - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $inputForm = $("#inputForm");
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "admin.articleCategory.edit" />
	</div>
	<form id="inputForm" action="update.rk" method="post">
		<input type="hidden" name="id" value="${articleCategory.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span><@s.m "ArticleCategory.name" />:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" value="${articleCategory.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "ArticleCategory.parent" />:
				</th>
				<td>
					<select name="parentId">
						<option value=""><@s.m "admin.articleCategory.root" /></option>
						<#list articleCategoryTree as category>
							<#if category != articleCategory && !children?seq_contains(category)>
								<option value="${category.id}"<#if category == articleCategory.parent> selected="selected"</#if>>
									<#if category.grade != 0>
										<#list 1..category.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${category.name}
								</option>
							</#if>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "ArticleCategory.seoTitle" />:
				</th>
				<td>
					<input type="text" name="seoTitle" class="text" value="${articleCategory.seoTitle}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "ArticleCategory.seoKeywords" />:
				</th>
				<td>
					<input type="text" name="seoKeywords" class="text" value="${articleCategory.seoKeywords}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "ArticleCategory.seoDescription" />:
				</th>
				<td>
					<input type="text" name="seoDescription" class="text" value="${articleCategory.seoDescription}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<@s.m "admin.common.order" />:
				</th>
				<td>
					<input type="text" name="order" class="text" value="${articleCategory.order}" maxlength="9" />
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
</body>
</html>