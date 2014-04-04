<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆 塔防三国志</title>
<style type="text/css">
body,div,p{padding:0;margin:0;}
body{font:12px tahoma,'\5b8b\4f53',sans-serif;color:#efbe69;}
a{outline:0 none;color:#FFFFFF;cursor:pointer;}
.bd{ height: 653px;width: 980px;margin:0 auto;position:relative; background:url(${base}/resources/front/images/bj_login.jpg) no-repeat center top;}
.main{ float:left;width:400px; padding:330px 0 0 335px;}
.btn-start{background:url(${base}/resources/front/images/enter.gif) no-repeat scroll 0 0 transparent;width: 238px;height:85px;
display:block;text-indent:-9999px;margin: 0 auto 20px;}
.main strong{color:#FFFF33;margin-right:18px;font-size:12px}
/*.main { width:760px; height:500px;}*/
.box { width:300px;  text-align:center; height:120px }
.my_select_zone { font-size:12px; background: none no-repeat scroll 0 0 #330000;  border-radius: 2px 2px 2px 2px; text-align:center; padding:5px 10px;font-size:12px;color:#FFFFFF;}
.license{ color:#b7b7b7;text-align:left; width:230px; padding:5px; margin:15px auto; clear:both;background: url("${base}/resources/front/images/liencebox.png") repeat; border-radius: 2px 2px 2px 2px;}
.license a{color:#b7a36e;line-height:18px;}
span.span_ipt { float:left; width:18px; height:32px; display:block; margin-right:2px}
.warmtips{ width:100%; left:0; position:absolute; bottom:0px; height:50px;background-color:#330000 }
.warmtips p{ font-size:12px; line-height:20px; padding:5px 20px; color:#FFFFFF}
.tab-bar{border-bottom:1px solid #6f88b4; height:26px; margin:6px auto 6px auto; position:relative; width:760px; font-weight: bold;}
.tab-bar a{display:block;width:176px;height:27px;background:url(${base}/resources/front/images/tabbg.png) no-repeat 0 0;text-align:center;line-height:27px;float:left;_display:inline;margin-right:4px;color:#2e394c;text-decoration:none;}
.tab-bar .curr{color:#46608b;font-weight:bold;background-position:0 -27px;}
</style>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js" charset="UTF-8"></script>
<script type="text/javascript">
function loginZone(zoneid)
{
	var loginPath = "${base}/app/redirect.rk";
	location.href = loginPath + "?id=" + zoneid;
}

function zoneList()
{
	location.href = "${base}/app/SelectServer";
}
</script>
</head>
<body>
  <div class="bd">
	<div class="main c">
		<div class="box">
	            <a id="login_qzone_btn" href="javascript:void(0);" onclick="javascript:loginZone('${obj.id}');return false;" class="btn-start" >开始游戏</a>
	            <span id="span_desc" class="my_select_zone">您最近登录是<strong>[${obj.name }]</strong><a id="link2allServer" href="javascript:void(0);" onclick="javascript:zoneList();return false;">重新选区</a></span>
		 </div>
	 </div>
	<div class="warmtips"><p>如需帮助，请到<a href="#" target="_blank">【塔防三国志Facebook粉丝页】</a>提交问题，将会有专业的客服人员为您解答问题。</p></div>
</div>
</body>
</html>