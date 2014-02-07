<#list obj?keys as key>
	<set name="<@timeFormat time=key format="yyyy-MM" />" value="${obj[key]}" />
</#list>