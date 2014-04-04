<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title> test </title>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js" charset="UTF-8"></script>
<script type="text/javascript">
$(function(){
	$("#inputForm").submit();
})
</script>
</head>
<body>
<div style="margin: 0 auto;height:768px;width:1024px; ">
<iframe scrolling="no" height="768" width="1024" frameborder="0"  allowfullscreen="" msallowfullscreen="" oallowfullscreen="" mozallowfullscreen="" webkitallowfullscreen="" src="javascript:''" name="iframe_canvas_shanggame_https"></iframe>
<form id="inputForm" method=post action="${url}" target="_self">
    <input type="hidden" name="signed_request" value="${obj}">
</form>
</div>
</body>
</html>