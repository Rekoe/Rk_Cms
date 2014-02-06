<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.main.title" /> - Powered By Rekoe</title>
<meta name="author" content="Rekoe Team" />
<meta name="copyright" content="Rekoe" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {
	var $nav = $("#nav a:not(:last)");
	var $menu = $("#menu dl");
	var $menuItem = $("#menu a");
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("current");
		$this.addClass("current");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		return false;
	});
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});
});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main">
		<tr>
			<th class="logo">
				<img src="${base}/resources/admin/images/header_logo.gif" alt="Rekoe" />
			</th>
			<th>
				<div id="nav" class="nav">
					<ul>
					<@shiro.hasAnyPermission name = "admin:article,admin:articleCategory">
						<li><a href="#content"><@s.m "admin.main.contentNav" /></a></li>
					</@shiro.hasAnyPermission>
					<@shiro.hasAnyPermission name = "admin:setting,admin:admin,admin:role">
						<li><a href="#system"><@s.m "admin.main.systemNav" /></a></li>
					</@shiro.hasAnyPermission>
						<li><a href="${base}/" target="_blank"><@s.m "admin.main.home" /></a></li>
					</ul>
				</div>
				<div class="link">
					<a href="#" target="_blank"><@s.m "admin.main.official" /></a>|
					<a href="#" target="_blank"><@s.m "admin.main.bbs" /></a>|
					<a href="#" target="_blank"><@s.m "admin.main.about" /></a>
				</div>
				<div class="link">
					<strong><@shiro.principal property="name"/></strong>
					<@s.m "admin.main.hello" />!
					<a href="../admin/profile/edit.rk" target="iframe">[<@s.m "admin.main.profile" />]</a>
					<a href="../logout" target="_top">[<@s.m "admin.main.logout" />]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td id="menu" class="menu">
				<dl id="content">
					<dt><@s.m "admin.main.contentGroup" /></dt>
					<@shiro.hasPermission name="admin:article">
						<dd><a href="../admin/article/list.rk" target="iframe"><@s.m "admin.main.article" /></a></dd>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:articleCategory">
						<dd><a href="../admin/article_category/list.rk" target="iframe"><@s.m "admin.main.articleCategory" /></a></dd>
					</@shiro.hasPermission>
				</dl>
				<dl id="system">
					<dt><@s.m "admin.main.systemGroup" /></dt>
					<@shiro.hasPermission name="admin:setting">
						<dd><a href="../setting/edit.rk" target="iframe"><@s.m "admin.main.setting" /></a></dd>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:admin">
						<dd><a href="../admin/list.rk" target="iframe"><@s.m "admin.main.admin" /></a></dd>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:role">
						<dd><a href="../role/list.rk" target="iframe"><@s.m "admin.main.role" /></a></dd>
					</@shiro.hasPermission>
				</dl>
			</td>
			<td><iframe id="iframe" name="iframe" src="index.rk" frameborder="0"></iframe></td>
		</tr>
	</table>
</body>
</html>