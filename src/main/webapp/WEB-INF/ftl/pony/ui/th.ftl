<#macro th id=""
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	>
<#if label!="">
<th<#if id?? && id!=""> id="${id}"</#if> width="${labelWidth}%" class="pn-flabel<#if noHeight=='false'> pn-flabel-h</#if>"><#if required!="false"><span class="pn-frequired">*</span></#if><@s.mt code=label text=label/><#if hasColon="true">${colon}</#if></th><#rt/>
<#else>
<th><#rt/>
</th>
</#if>
<#rt/>
</#macro>
