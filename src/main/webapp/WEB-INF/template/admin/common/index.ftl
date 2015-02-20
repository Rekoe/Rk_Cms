<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><@s.m "admin.index.title" /> - Powered By SHOP++</title>
<#include "/template/admin/common/head.ftl" />
<style type="text/css">
.input .powered {
	font-size: 11px;
	text-align: right;
	color: #cccccc;
}
</style>
</head>
<body>
	<div class="path"><@s.m "admin.index.title" /></div>
	<table class="input">
		<tr>
			<th><@s.m "admin.index.systemName" />:</th>
			<td> Cms </td>
			<th><@s.m "admin.index.systemVersion" />:</th>
			<td>1.b.0</td>
		</tr>
		<tr>
			<th><@s.m "admin.index.official" />:</th>
			<td><a href="http://www.rekoe.com" target="_blank">http://www.rekoe.com</a></td>
			<th><@s.m "admin.index.bbs" />:</th>
			<td><a href="http://bbs.rekoe.com" target="_blank">http://bbs.rekoe.com</a></td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<th><@s.m "admin.index.javaVersion" />:</th>
			<td> ${props['java.runtime.name']!} ${props['java.runtime.version']!} </td>
			<th><@s.m "admin.index.javaHome" />:</th>
			<td> ${props['java.vm.name']!} ${props['java.vm.version']!} </td>
		</tr>
		<tr>
			<th><@s.m "admin.index.osName" />:</th>
			<td> ${props['os.name']!} ${props['os.version']!}</td>
			<th><@s.m "admin.index.osArch" />:</th>
			<td>${props['os.arch']!} ${props['sun.arch.data.model']!}位</td>
		</tr>
		<tr>
			<td class="powered" colspan="4">
				COPYRIGHT © 2005-2013 REKOE.COM ALL RIGHTS RESERVED.
			</td>
		</tr>
	</table>
</body>
</html>