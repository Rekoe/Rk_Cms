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
	$("#jvForm").validate({
		rules: {
			channelId: {
				required: true,
				leafChannel: true
			}
		},
		messages:{
			channelId: {
				leafChannel: "<@s.m "rule.error.notLeafChannel"/>"
			}
		}
	});
});
var channels = [];
<#list channelList as c>
	channels[${c_index}] = {
		id:${c.id}
	};
</#list>
function channelChange(n) {
	if(n==0) {
		return;
	}
	//0为请选择，所以必须减一。
	n--;
	fetchTopics(channels[n].id);
}
function fetchTopics(channelId) {
	$.getJSON("../topic/by_channel.do",{channelId:channelId},function(topics) {
		var ts = $("#topics");
		ts.empty();
		var len = topics.length;
		for(var i=0;i<len;i++) {
			ts.append("<label><input type='checkbox' name='topicIds' value='"+topics[i].id+"'/>"+topics[i].name+"</label> ");
		}
		ts.parent().toggle(len>0);
	});
}
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
	background:url(out1.gif) left -1px repeat-x;
	overflow:hidden;
	line-height:30px;
	cursor:pointer;
	color:#666666;	
}
.ParamTab .head{
	padding-top:5px;
	overflow:hidden;
	background:url(../img/head_new.gif) repeat-x;
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
<div class="body-box">
<div class="box-positon">
	<div class="rpos"><@s.m "global.position"/>: <@s.m "rule.function"/> - <@s.m "global.add"/></div>
	<form class="ropt">
		<input type="submit" class="return-button" value="<@s.m "global.backToList"/>" onclick="this.form.action='v_list.do';"/>
	</form>
	<div class="clear"></div>
</div>
<@p.crawler_form id="jvForm" action="o_save.do" labelWidth="12" >

<div class="ParamTab border box-positon" style="height:500px">
	<div class="head" style="height:29px">
		<span class="tag" style="width: 220px; padding: 0 1px 0 10px;"><@p.submit code="global.submit"/> &nbsp; <@p.reset code="global.reset"/>&nbsp;<@s.m "rule.tabpanel.title"/></span>
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
	    <table class="pn-ftable" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
		       <@p.text colspan="1" width="50" label="rule.name" name="name" required="true" class="required" maxlength="50"/>
				<@p.td colspan="1" width="50" label="rule.info" required="true">
				<@s.m "rule.type"/>: <@p.select list=typeList name="typeId" listKey="id" listValue="name"/>
				&nbsp; <@s.m "rule.channel"/>:
				<select name="channelId" onchange="channelChange(this.selectedIndex)">
				<#if !channel??>
					<option value="" class="sel-disabled"><@s.m "global.pleaseSelect"/></option>
				</#if>
				<#if channelList?size gt 0>
				<#assign origDeep=channelList[0].deep+1/>
				<#list channelList as c>
					<#if c.hasContent || c.child?size gt 0>
					<option value="${c.id}"<#if c.child?size gt 0> class="sel-disabled"</#if>><#if c.deep gte origDeep><#list origDeep..c.deep as i>&nbsp;&nbsp;</#list>></#if>${c.name}</option>
					</#if>
				</#list>
				</#if>
				</select> <span class="pn-fhelp"><@s.m "rule.channel.help"/></span>
				</@p.td><@p.tr/>
				
				<@p.text colspan="1" width="50" label="rule.pageEncoding" name="pageEncoding" value="GBK" required="true" class="required" maxlength="20" help="rule.pageEncoding.help"/>
				<@p.text colspan="1" width="50" label="rule.pauseTime" name="pauseTime" value="500" style="width:50px" required="true" class="required" maxlength="10" help="rule.pauseTime.help"/><@p.tr/>
				
				<@p.td colspan="1" width="50" label="rule.extractContentRes">
				<input type="radio" value="true" name="titleStart" style=""><@s.m "rule.radio.yes"/>
				<input type="radio" value="false" name="titleStart" checked style=""><@s.m "rule.radio.no"/></br>
				<span class="pn-fhelp"><@s.m "rule.extractContentRes.help"/></span>
				</@p.td>
				<@p.td colspan="1" width="50" label="rule.replaceHtmlLink">
				<input type="radio" value="true" name="titleEnd" style=""><@s.m "rule.radio.yes"/>
				<input type="radio" value="false" name="titleEnd" checked style=""><@s.m "rule.radio.no"/>
				<span class="pn-fhelp"><@s.m "rule.replaceHtmlLink.help"/></span>
				</@p.td>
				<@p.tr/>
				
				<@p.td colspan="1" width="50" label="rule.repeatCheckType">
				<input type="radio" value="true" name="repeatCheckType" style=""><@s.m "rule.radio.yes"/>
				<input type="radio" value="false" name="repeatCheckType" checked style=""><@s.m "rule.radio.no"/></br>
				<span class="pn-fhelp"><@s.m "rule.repeatCheckType.help"/></span>
				</@p.td>
				<@p.td colspan="1" width="50" label="rule.useProxy">
				<input type="radio" value="true" name="useProxy" style=""><@s.m "rule.radio.yes"/>
				<input type="radio" value="false" name="useProxy" checked style=""><@s.m "rule.radio.no"/>
				<span class="pn-fhelp"><@s.m "rule.useProxy.help"/></span>
				</@p.td>
				<@p.tr/>
				
				<@p.td colspan="2" label="rule.proxy">
				<table border="0" width="100%">
				<tr>
				<td><@s.m "rule.proxy.address"/>：<input type="text" name="proxyAddress" style="width:200px" maxlength="100"/><@s.m "rule.proxy.port"/>：<input type="text" name="proxyPort" style="width:50px" maxlength="10"/></td>
				</tr>
				</table>
				</@p.td><@p.tr/>
				
				<@p.td colspan="2" label="content.topicIds">
				<table border="0" width="100%">
				<tr>
				<td width="100%">
				<div style="float:left;padding-left:7px;<#if topicList?size == 0>display:none</#if>">
				&nbsp;<span id="topics"><@p.checkboxlist list=topicList listKey="id" listValue="sname" name="topicIds"/></span>
				</div>
				<div style="clear:both"></div>
				</td>
				</tr>
				</table>
				</@p.td><@p.tr/>
				
				<@p.textarea colspan="2" label="rule.replaceWords" name="replaceWords" rows="3" cols="70" help="rule.replaceWords.help" helpPosition="3"/><@p.tr/>
						       
		    </tr>
	    </table>
	
	</div>
	<!-- 内容属性 -->
	<div style="display: none;" class="cont" id="Tab2">
	<table class="pn-ftable" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
	   <@p.textarea colspan="2" label="rule.planList" name="planList" rows="2" cols="70" help="rule.planList.help" helpPosition="3"/><@p.tr/>
		<@p.td colspan="2" label="rule.dynamicAddr">
		<div><input type="text" name="dynamicAddr" style="width:450px" maxlength="255"/> <span class="pn-fhelp"><@s.m "rule.dynamicAddr.help"/></span></div>
		<div><@s.m "rule.dynamicPage"/> <@s.m "rule.from"/> <input type="text" name="dynamicStart" value="2" size="7"/> &nbsp; <@s.m "rule.to"/>: <input type="text" name="dynamicEnd" value="10" size="7"/></div> 
		</@p.td><@p.tr/>
		
		<@p.td colspan="2" label="rule.linkset">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<td align="center" width="50%"><textarea name="linksetStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="linksetEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		
		<@p.td colspan="2" label="rule.description">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="descriptionStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="descriptionEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.content">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="contentStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="contentEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		  </tr>
	    </table>
	</div>
	<!-- 内容分页属性 -->
	<div style="display: none;" class="cont" id="Tab3">
	<table class="pn-ftable" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
	   <@p.td colspan="2" label="rule.pagelinkarea">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<tr>
		<td align="center" width="50%"><textarea name="paginationStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="paginationEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		
	</tr>
	    </table>
	</div>
	<!-- 评论属性 -->
	<div style="display: none;" class="cont" id="Tab4">
	<table class="pn-ftable" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
	    
		<@p.td colspan="2" label="rule.commentIndex">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><@s.m "rule.start"/></td>
		<td align="center" width="50%"><@s.m "rule.end"/></td>
		</tr>
		<tr>
		<tr>
		<td align="center" width="50%"><textarea name="commentIndexStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="commentIndexEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.commentArea">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="commentAreaStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="commentAreaEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.comment">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="commentStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="commentEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		<@p.td colspan="2" label="rule.commentLink">
		<table border="0" width="100%">
		<tr>
		<td align="center" width="50%"><textarea name="commentLinkStart" rows="2" style="width:85%"></textarea></td>
		<td align="center" width="50%"><textarea name="commentLinkEnd" rows="2" style="width:85%"></textarea></td>
		</tr>
		</table>
		</@p.td><@p.tr/>
		</tr>
	    </table>
	</div>
	<!-- 扩展字段集 -->
	<div style="display: none;" class="cont" id="Tab5">
	<table class="pn-ftable" cellspacing="1" cellpadding="2" border="0" width="100%">
		    <tr>
		
		<@p.td label="rule.fields" colspan="2" >
		<div><input type="button" class="add" style="width:80px" onclick="addPicLine();" value="<@s.m "rule.fields.add"/>"/><@s.m "rule.fields.help"/></div>
		<table border="0" width="100%">
			<tr id="picBefore">
				<td align="center" width="25%"><@s.m "rule.fields.name"/></td>
				<td align="center" width="25%"><@s.m "rule.start"/></td>
				<td align="center" width="25%"><@s.m "rule.end"/></td>
				<td align="center" width="25%"><@s.m "rule.fields.opt"/></td>
			</tr>
			<tr id="picTable0">
					<td align="center" width="25%">
						<input type="text" id="fields0" name="fields" style="width:100px"/> 
					</td>
					<td align="center" width="25%">
						<textarea style="width:200px;height:60px;" id="filterStart0" name="filterStart" maxlength="255"></textarea>
					</td>
					<td align="center" width="25%">
						<textarea style="width:200px;height:60px;" id="filterEnd0" name="filterEnd" maxlength="255"></textarea>
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
				<input type="text" id="fields{0}" name="fields" style="width:100px"/> 
			</td>
			<td align="center" width="25%">
				&lt;textarea style="width:200px;height:60px;" id="filterStart{0}" name="filterStart" maxlength="255"&gt;&lt;/textarea&gt;
			</td>
			<td align="center" width="25%">
				&lt;textarea style="width:200px;height:60px;" id="filterEnd{0}" name="filterEnd" maxlength="255"&gt;&lt;/textarea&gt;
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

</@p.crawler_form>

</div>
</body>
</html>