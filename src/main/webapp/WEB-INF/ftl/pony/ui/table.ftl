<#macro table value class="list">
<table class="${class}" id="listTable">
<#if value?is_sequence><#local pageList=value/><#else><#local pageList=value.list/></#if>
<#list pageList as row>
<#if row_index==0>
<#assign i=-1/>
<tr><#nested row,i,true/></tr>
</#if>
<#assign i=row_index has_next=row_has_next/>
<#if row_index==0><tr><#else><tr></#if><#nested row,row_index,row_has_next/>
<#if !row_has_next>
</tr>
<#else>
</tr>
</#if>
</#list>
</table>
</#macro>