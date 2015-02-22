<#assign top>
		<div style="border-right:#ffffff 1px solid;border-top:#ffffff 1px solid;background:#dcdcdc;width:600px;border-bottom:#cccccc 1px solid;height:14px;text-align:left">
		<div style="overflow:hidden;width:100%;position:absolute;height:12px">
			<img id="bar" src="${base}/resources/admin/images/vote_bar.gif"/>
		</div>
		<div id="percent" style="font-size:10px;width:600px;color:white;font-family:arial;position:absolute;height:14px;text-align:center"></div>
		</div>
		<p></p>
		<div id="messageBox" style="font-size:11px;width:600px;color:#999999;font-family:arial;position:relative;height:14px;text-align:center"></div>
		<p></p>
</#assign>

<#if list?size gt 0>	
		${top}
		<#else>
		<@s.m "rule.noAcquisitionRunning"/>
</#if>
<#if list?size gt 0>
	<#list list as temp>
		<@s.m "rule.d"/>${temp.id}<@s.m "rule.t"/> 
		${temp.contentUrl}  
		<#if temp.title??>【${temp.title!}】</#if>  
		<@s.m "rule.success"/>
		<br/>
	</#list>
</#if>
<script>
	percent=${obj};
</script>