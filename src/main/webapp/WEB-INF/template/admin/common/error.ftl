<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.error.title" /> - Powered By Rekoe Cms</title>
<#include "/template/admin/common/head.ftl" />
<link href="${base}/resources/admin/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="wrap">
		<div class="error">
			<dl>
			<dt><@s.m "admin.error.message" /></dt>
				<dd>${obj.content}</dd>
				<dd><a href="javascript:;" onclick="window.history.back(); return false;"><@s.m "admin.error.back" /></a></dd>
			</dl>
		</div>
	</div>
</body>
</html>