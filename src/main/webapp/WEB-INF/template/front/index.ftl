<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${setting.siteName} - Powered By Rekoe Cms</title>
<meta name="author" content="Rekoe Cms Team" />
<meta name="copyright" content="Rekoe Cms" />
<meta name="keywords" content="Rekoe Cms Game" />
<meta name="description" content="Rekoe Cms Gam" />
<link href="${base}/resources/front/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/front/css/article.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
</head>
<body>
	<#include "/template/front/include/header.ftl" />
	<div class="container articleList">
		<#if setting.siteEnabled>
					<div class="result">
					<#if obj?has_content>
						<ul>
							<#list obj as article>
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
			<#else>
			<div class="wrap">
				<div class="error">
					<dl>
					<dt>${setting.siteCloseMessage?html}</dt>
					</dl>
				</div>
			</div>
			
		</#if>
	</div>
	<#include "/template/front/include/footer.ftl" />
</body>
</html>