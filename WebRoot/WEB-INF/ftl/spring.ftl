<#ftl strip_whitespace=true>
<#--
 * message as m
 *
 * Macro to translate a message code into a message.
 -->
<#macro m code>${mvcs[(code)]}</#macro>
<#--
 * messageArgs as ma
 *
 * Macro to translate a message code with arguments into a message.
 -->
<#macro ma code, text>${mvcs[(code)]?replace('{0}', text )}  </#macro>