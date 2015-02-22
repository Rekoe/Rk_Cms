<#macro tree value="" multiple="" headerKey="" headerValue="" listKey="" listValue="" listDeep="" headerButtom="false"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	vld="" category = false
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<select<#rt/>
<#if name!=""> name="${name}"</#if> <#if id!=""> name="${id}"</#if><#rt/>
><#rt/>
<#if category>
<option value=""><@s.m "admin.articleCategory.root" /></option>
</#if>
<@article_category_tree categoryid = value/>
</select>
<#include "control-close.ftl"/><#rt/>
</#macro>