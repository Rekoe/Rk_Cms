<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Rekoe.com - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<meta name="keywords" content="Rekoe Cms Game" />
<meta name="description" content="Rekoe Cms Gam" />
<link href="${base}/resources/front/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/front/css/article.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $articleSearchForm = $("#articleSearchForm");
	var $keyword = $("#articleSearchForm input");
	var $articleForm = $("#articleForm");
	var $pageNumber = $("#pageNumber");
	var defaultKeyword = "<@s.m "front.article.keyword" />";
	$keyword.focus(function() {
		if ($keyword.val() == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($keyword.val() == "") {
			$keyword.val(defaultKeyword);
		}
	});

	$articleSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});
	
	$articleForm.submit(function() {
		if ($pageNumber.val() == "" || $pageNumber.val() == "1") {
			$pageNumber.prop("disabled", true)
		}
	});
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$articleForm.submit();
		return false;
	}
});
</script>
</head>
<body>
	<#include "/template/front/include/header.ftl" />
	<div class="container articleList">
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
								<a href="${base}/article/view/${article.id}.rk" title="${article.title}.rk"><@htmlCut s=article.title len=17 append="..."/></a>
							</li>
						</#list>
					</@article_list>
				</ul>
 		</div>
			<div class="articleSearch">
				<div class="title"><@s.m "front.article.search" /></div>
				<div class="content">
					<form id="articleSearchForm" action="${base}/article/search.rk" method="post">
						<input type="text" name="keyword" value="<@s.m "front.article.keyword" />" maxlength="30" />
						<button type="submit"><@s.m "front.article.searchSubmit" /></button>
					</form>
				</div>
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
					<li class="last">${articleCategory.name}</li>
				</ul>
			</div>
			<form id="articleForm" action="${base}/article/list/${articleCategory.id}.rk" method="post">
				<input type="hidden" id="pageNumber" name="pageNumber" value="${obj.pageNo}" />
				<div class="result">
					<#if obj.list?has_content>
						<ul>
							<#list obj.list as article>
								<li<#if !article_has_next> class="last"</#if>>
									<a href="${base}/article/view/${article.id}.rk" title="${article.title}">${article.title}</a>
									<@s.m "front.article.author" /> : ${article.author}
									<span title="${article.createDate?string("yyyy-MM-dd")}">${article.createDate?string("yyyy-MM-dd ")}</span>
									<p><@htmlCut s=article.content len=110 append="..."/><a href="${base}/article/view/${article.id}.rk">[<@s.m "front.article.view" />]</a></p>
								</li>
							</#list>
						</ul>
					<#else>
						<@s.m "front.article.noResult" />
					</#if>
				</div>
				<@pagination pageNumber = obj.pageNo totalPages = obj.totalPage pattern = "javascript: $.pageSkip({pageNumber});">
					<#include "/template/front/include/pagination.ftl">
				</@pagination>
			</form>
		</div>
	</div>
	<#include "/template/front/include/footer.ftl" />
</body>
</html>