<#macro fcolumn code="" title="">
<tr>
	<th><#if code!=""><@s.mt code=code text=code/><#else>${title}</#if></th><#rt/>
	<td>
		<#nested/><#rt/>
	</td><#rt/>
</tr><#rt/>
</#macro>