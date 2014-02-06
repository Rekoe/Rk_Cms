<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${obj.title} - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<meta name="keywords" content="Rekoe Cms" />
<meta name="description" content="Rekoe Cms" />
<link href="${base}/resources/front/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/front/css/article.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $hits = $("#hits");
	$.ajax({
		url: "${base}/article/hits/${obj.id}.rk",
		type: "GET",
		success: function(data) {
			$hits.text(data);
		}
	});
});
</script>
</head>
<body>
	<#assign articleCategory = obj.articleCategory />
	<#include "/template/front/include/header.ftl" />
	<div class="container articleContent">
		<div class="span6">
			<div class="hotArticleCategory">
				<div class="title"><@s.m "front.article.articleCategory" /></div>
				<@article_category_root_list count = 10>
					<#list articleCategories as category>
						<dl<#if !category_has_next> class="last"</#if>>
							<dt>
								<a href="${base}/article/list/${category.id}.rk">${category.name}</a>
							</dt>
							<#list category.children as articleCategory>
								<#if articleCategory_index == 6>
									<#break />
								</#if>
								<#if articleCategory.id != category.id>
								<dd>
									&nbsp;&nbsp;<a href="${base}/article/list/${articleCategory.id}.rk">${articleCategory.name}</a>
								</dd>
								</#if>
							</#list>
						</dl>
					</#list>
				</@article_category_root_list>
			</div>
			<div class="hotArticle">
				<div class="title"><@s.m "front.article.hotArticle" /></div>
				<ul>
					<@article_list articleCategoryId = articleCategory.id count = 10 desc="hits">
						<#list articles as article>
							<li>
								<a href="${base}/article/view/${article.id}.rk" title="${article.title}"><@htmlCut s=article.title len=18 append="..."/></a>
							</li>
						</#list>
					</@article_list>
				</ul>
			</div>
		</div>
		<div class="span18 last">
			<div class="path">
				<ul>
					<li>
						<a href="${base}/"><@s.m "path.home" /></a>
					</li>
					<@article_category_parent_list articleCategoryId = articleCategory.id>
						<#list articleCategories as articleCategory>
							<li>
								<a href="${base}/article/list/${articleCategory.id}.rk">${articleCategory.name}</a>
							</li>
						</#list>
					</@article_category_parent_list>
					<li>
						<a href="${base}/article/list/${articleCategory.id}.rk">${articleCategory.name}</a>
					</li>
				</ul>
			</div>
			<div class="main">
				<h1 class="title">${obj.title}</h1>
				<div class="info">
					<@s.m "front.article.createDate" />: ${obj.createDate?string("yyyy-MM-dd HH:mm:ss")}
					<@s.m "front.article.author" />: ${obj.author}
					<@s.m "front.article.hits" />: <span id="hits">&nbsp;</span>
				</div>
				<div class="content">${obj.content}</div>
			</div>
		</div>
	</div>
	<#include "/template/front/include/footer.ftl" />
</body>
</html>