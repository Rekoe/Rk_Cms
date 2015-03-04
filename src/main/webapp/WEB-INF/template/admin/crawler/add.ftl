<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<#include "/template/admin/common/head.ftl" />
<script type="text/javascript">
$.validator.methods.leafChannel = function(value, element, param) {
	var i = element.selectedIndex;
	return $(element.options[i]).attr("class")!="sel-disabled";
};
$(function() {
	$('#update').click(function() {  
		$.dialog({
			type: "warn",
			content: '<@s.m "admin.dialog.updateConfirm"/>',
			ok: '<@s.m "admin.dialog.ok"/>',
			cancel: '<@s.m "admin.dialog.cancel"/>',
			onOk: function() {
				$.ajax({
					url: "save.rk",
					type: "POST",
					data: $('#jvForm').serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							window.location.href = "list"
						}
					}
				});
			}
		}); 
		return false;
	}); 
});
//点击滑动JS
function ShowTab(theA,Small,main){
	next=theA+1;
	for(var i=Small;i< main;i++ ){
		if(i==theA){
			document.getElementById('Tab'+i).style.display='';
			document.getElementById('Span'+i).className='choose';
		}else if(i==Small){
			document.getElementById('Tab'+i).style.display='none';
			document.getElementById('Span'+i).className='begin';
		}else if(i==next){
			document.getElementById('Tab'+i).style.display='none';
			document.getElementById('Span'+i).className='next';
		}else{
			document.getElementById('Tab'+i).style.display='none';
			document.getElementById('Span'+i).className='';	
		}		
	}
}
</script>
<style type="text/css">
.pn-ftable{background-color:#B4CFCF;margin-top:5px;}
.pn-fcontent{background-color:#FFFFFF;padding-left:5px;}
.sel-disabled{background-color:#ccc}
.ParamTab{}
.border{border:1px solid #E8E8E8;}
.ParamTab .head div{
	width:80px;
	height:28px;
	border-top:#DBDBDB solid 1px;
	border-right:#DBDBDB solid 1px;
	float:left;
	text-align:center;
	overflow:hidden;
	line-height:30px;
	cursor:pointer;
	color:#666666;	
}
.ParamTab .head{
	padding-top:5px;
	overflow:hidden;
}
.ParamTab .cont{
	overflow:hidden;
	padding-top:3px;
	padding-left:2px;
}
.ParamTab .cont div{
	margin:auto;
}
.ParamTab .head a{
	color:#2143DC;
}
.ParamTab .head .tag{
	float:left;
	line-height:20px;
	padding:0 50px 0 20px;
	font-size:14px;
	font-weight:bold;
}
#Span1{
	border-left:#DBDBDB solid 1px;
}
.ParamTab .head div.choose{
	background:#FFF;
	color:#2143DC;
}

</style>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.rk"><@s.m "admin.path.index" /></a> &raquo; <@s.m "rule.function"/>- <@s.m "admin.common.add"/>
	</div>
<@p.crawler_form id="jvForm" action="#" labelWidth="12" >
	<div class="ParamTab border" style="height:500px">
		<div class="head" style="height:29px">
			<div class="choose" style="width: 120px;" onclick="ShowTab(1,1,6)" id="Span1">
				<@s.m "rule.tabpanel.base"/>
			</div>
			<div class="next" style="width: 120px;" onclick="ShowTab(2,1,6)" id="Span2">
				<@s.m "rule.tabpanel.content"/>
			</div>
			<div class="next" style="width: 120px;" onclick="ShowTab(3,1,6)" id="Span3">
				<@s.m "rule.tabpanel.contentpage"/>
			</div>
			<div class="next" style="width: 120px;" onclick="ShowTab(4,1,6)" id="Span4">
			    <@s.m "rule.tabpanel.comment"/>
			</div>
			<div class="next" style="width: 120px;" onclick="ShowTab(5,1,6)" id="Span5">
				<@s.m "rule.tabpanel.field"/>
			</div>
	</div>
	<!-- 基本属性 -->
	<div class="cont" id="Tab1">
	    <table class="input" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
		       <@p.text colspan="1" width="50" label="rule.name" name="crawler.name" required="true" class="required" maxlength="50"/>
				<@p.tree label="rule.channel" colspan="2" name="crawler.articleCategoryId" required="true" class="required" />
				<@p.tr/>
				<@p.text colspan="2" width="50" label="rule.gatherNum" name="crawler.gatherNum" style="width:50px" required="true" class="required" maxlength="10" help="rule.gatherNum.help"/><@p.tr/>
				<@p.text colspan="1" width="50" label="rule.pageEncoding" name="crawler.pageEncoding" value="GBK" required="true" class="required" maxlength="20" help="rule.pageEncoding.help"/>
				<@p.text colspan="1" width="50" label="rule.pauseTime" name="crawler.pauseTime" value="500" style="width:50px" required="true" class="required" maxlength="10" help="rule.pauseTime.help"/><@p.tr/>
				<@p.radio width="50" colspan="2" label="rule.extractContentRes" id="crawler.extractContentRes" name="crawler.extractContentRes" value="false" list={"true":"rule.radio.yes","false":"rule.radio.no"} help="rule.extractContentRes.help"/><@p.tr/>
				<@p.radio width="50" colspan="2" label="rule.replaceHtmlLink" id="crawler.replaceHtmlLink" name="crawler.replaceHtmlLink" value="false" list={"true":"rule.radio.yes","false":"rule.radio.no"} help="rule.replaceHtmlLink.help"/><@p.tr/>
				<@p.radio width="50" colspan="2" label="rule.repeatCheckType" id="crawler.repeatCheckType" name="crawler.repeatCheckType" value="false" list={"true":"rule.radio.yes","false":"rule.radio.no"} help="rule.repeatCheckType.help"/><@p.tr/>
				<@p.radio width="50" colspan="2" label="rule.useProxy" id="crawler.useProxy" name="crawler.useProxy" value="false" list={"true":"rule.radio.yes","false":"rule.radio.no"} help="rule.useProxy.help"/><@p.tr/>
				<@p.text colspan="1" width="50" label="rule.proxy.address" name="crawler.proxyAddress" required="true" class="required" style="width:200px" maxlength="100"/>
				<@p.text colspan="1" width="50" label="rule.proxy.port" name="crawler.proxyPort" value="80" required="true" class="required" style="width:50px" maxlength="10"/><@p.tr/>
				<@p.textarea colspan="2" label="rule.replaceWords" class="textbox" name="crawler.replaceWords" rows="3" cols="70" help="rule.replaceWords.help" helpPosition="3"/><@p.tr/>
		    </tr>
	    </table>
	</div>
	<!-- 内容属性 -->
	<div style="display: none;" class="cont" id="Tab2">
	<table class="input" cellspacing="1" cellpadding="2" border="0" width="100%">
		<tr>
	   <@p.textarea colspan="2" label="rule.planList" name="crawler.planList" rows="2" cols="70" help="rule.planList.help" helpPosition="3"/><@p.tr/>
		<@p.td colspan="2" label="rule.dynamicAddr">
		<div><input type="text" name="crawler.dynamicAddr" class="text" style="width:450px" maxlength="255"/> <span class="pn-fhelp"><@s.m "rule.dynamicAddr.help"/></span></div>
		<div><@s.m "rule.dynamicPage"/> <@s.m "rule.from"/> <input type="text" class="text" name="crawler.dynamicStart" value="2" size="7"/> &nbsp; <@s.m "rule.to"/>: <input type="text" class="text" name="dynamicEnd" value="10" size="7"/></div> 
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.linkset">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<td align="center" width="50%"><textarea name="crawler.linksetStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.linksetEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.description">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="crawler.descriptionStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.descriptionEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.content">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="crawler.contentStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.contentEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		  </tr>
	    </table>
	</div>
	<!-- 内容分页属性 -->
	<div style="display: none;" class="cont" id="Tab3">
	<table class="input" cellspacing="1" cellpadding="2" border="0" width="100%">
		<tr>
	   <@p.td colspan="2" label="rule.pagelinkarea">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<tr>
		<td align="center" width="50%"><textarea name="crawler.paginationStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.paginationEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		
	</tr>
	    </table>
	</div>
	<!-- 评论属性 -->
	<div style="display: none;" class="cont" id="Tab4">
	<table class="input" cellspacing="1" cellpadding="2" border="0" width="100%">
		<tr>
		<@p.td colspan="2" label="rule.commentIndex">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<tr>
		<td align="center" width="50%"><textarea name="crawler.commentIndexStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.commentIndexEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.commentArea">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="crawler.commentAreaStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.commentAreaEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.comment">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="crawler.commentStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.commentEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.commentLink">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="crawler.commentLinkStart" rows="2" class="textbox" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="crawler.commentLinkEnd" rows="2" class="textbox" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		</tr>
	    </table>
	</div>
	<!-- 扩展字段集 -->
	<div style="display: none;" class="cont" id="Tab5">
	<table class="input" cellspacing="1" cellpadding="2" border="0" width="100%">
		<tr>
		<@p.td label="rule.fields" colspan="2" >
		<div><input type="button" class="button" style="width:80px" onclick="addPicLine();" value="<@s.m "rule.fields.add"/>"/><@s.m "rule.fields.help"/></div>
		<table border="0" width="100%">
			<tr id="picBefore">
				<td align="center" width="25%"><@s.m "rule.fields.name"/></td>
				<td align="center" width="25%"><@s.m "rule.start"/></td>
				<td align="center" width="25%"><@s.m "rule.end"/></td>
				<td align="center" width="25%"><@s.m "rule.fields.opt"/></td>
			</tr>
			<tr id="picTable0">
					<td align="center" width="25%">
						<input type="text" id="fields" class="text" name="fields" style="width:100px"/> 
					</td>
					<td align="center" width="25%">
						<textarea style="width:200px;height:60px;" id="filterStart" name="filterStart" maxlength="255"></textarea>
					</td>
					<td align="center" width="25%">
						<textarea style="width:200px;height:60px;" id="filterEnd" name="filterEnd" maxlength="255"></textarea>
					</td>
					<td align="center" width="25%">
					<a href="javascript:void(0);" onclick="$('#picTable0').remove();" class="pn-opt"><@s.m "content.picturesDel"/></a>
					</td>
			</tr>
		</table>
		
		</@p.td><@p.tr/>
		<textarea id="picTable" style="display:none;">
		<tr id="picTable{0}">
			<td align="center" width="25%">
				<input type="text" id="fields{0}" name="fields" style="width:100px" /> 
			</td>
			<td align="center" width="25%">
				&lt;textarea class="textbox" id="filterStart{0}" name="crawler.filterStart" maxlength="255"&gt;&lt;/textarea&gt;
			</td>
			<td align="center" width="25%">
				&lt;textarea class="textbox" id="filterEnd{0}" name="crawler.filterEnd" maxlength="255"&gt;&lt;/textarea&gt;
			</td>
			<td align="center" width="25%">
				<a href="javascript:void(0);" onclick="$('#picTable{0}').remove();" class="pn-opt"><@s.m "content.picturesDel"/></a>
			</td>
		</tr>
		</textarea>
		<script type="text/javascript">
		var picIndex = 1;
		var picTpl = $.format($("#picTable").val());
		function addPicLine() {
			$('#picBefore').after(picTpl(picIndex++));
		}
		</script>
		</tr>
	    </table>
	</div>
</div>
<script language="JavaScript">ShowTab(1,1,6);</script>
	<@p.th />
	<@p.td colspan="4" hasColon="false">
		<@p.submit code="admin.common.submit" id="update"/> &nbsp; <@p.button code="admin.common.back" id="backButton" class="button"/>
	</@p.td>
</@p.crawler_form>
</body>
</html>