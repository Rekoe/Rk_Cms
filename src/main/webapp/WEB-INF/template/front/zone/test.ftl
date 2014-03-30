<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title> test </title>
</head>
<body onload="document.getelementbyidx('gameform').submit()">
<iframe name="_appGame" style="width:400;height:200"></iframe>
<form id="gameform" method=post action="${url}" target="_appGame">
    <input type="hidden" name="signed_request" value="${code}">
    <input type="hidden" name="request_ids" value="${request_ids}">
</form>
</body>
</html>