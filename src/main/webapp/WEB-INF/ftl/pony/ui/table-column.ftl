<#--
表格列标签：展示数据列。
	title：标题（列头）。直接显示字符串。默认""。
	code：标题（列头）。显示国际化信息。默认""。
	width：列宽。默认""。
	align：对齐方式。
	class：css样式class
	style：css样式style
-->
<#macro column title="" code="" width="" align="" class="" style="" rowspan="" iconClass="" addSpan=true selectAll=false>
<#if title="" && code=""><td>title and code all not assign!</td><#return></#if>
<#if i==-1>
	<th <#if width!=""> width="${width}"</#if><#if class!=""> class="${class}"</#if><#if style!=""> style="${style}"</#if>><#if addSpan><span></#if><#if selectAll><input type="checkbox" id="selectAll" /></#if><#if code!=""><@s.mt code=code text=code/><#else>${title}</#if><#if addSpan></span></#if></th><#rt/>
<#else>
	<td nowrap <#if align!=""> align="${align}"</#if><#if rowspan!=""> rowspan="${rowspan}"</#if><#if class!=""> class="${class}"</#if><#if style!=""> style="${style}"</#if> <#if title!=""> title="${title}" </#if>><#if iconClass !=""><span class=${iconClass}></#if><#nested/><#if iconClass !=""></span></#if></td><#rt/>
</#if>
</#macro>