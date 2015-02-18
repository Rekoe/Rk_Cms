<#ftl strip_whitespace=true>
<#macro m code>${mvcs[(code)]}</#macro>
<#macro ma code, text>${mvcs[(code)]?replace('{0}', text )}  </#macro>
<#macro mt code, text>${mvcs[(code)]}</#macro>