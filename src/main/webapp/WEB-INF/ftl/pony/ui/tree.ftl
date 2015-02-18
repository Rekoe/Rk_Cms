<#macro tree
	list value="" multiple="" headerKey="" headerValue="" listKey="" listValue="" listDeep="" headerButtom="false"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	vld=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<select<#rt/>
<#if name!=""> name="${name}"</#if> <#if id!=""> name="${id}"</#if><#rt/>
><#rt/>
<#if list?is_sequence>
<#list list as categoryTree>
	<option value="${categoryTree.id}" <#if categoryTree.id==value> selected="selected"</#if>>
		<#if categoryTree.grade != 0>
			<#list 1..categoryTree.grade as i>
				&nbsp;&nbsp;
				<#if categoryTree.grade ==i>
					|-
				</#if>
			</#list>
		</#if>
		${categoryTree.name}
	</option>
</#list>
</#if>
</select>
<#include "control-close.ftl"/><#rt/>
</#macro>
