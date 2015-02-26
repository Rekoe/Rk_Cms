<#--
<form></form>
-->
<#macro crawler_form
	action="" method="post" target="" enctype="" acceptCharset=""
	theme="jeesys" width="100%" tableClass="pn-ftable" labelWidth="20" required="false" colspan="1"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onsubmit=""
	>
<form<#rt/>
 method="${method}"<#rt/>
 action="${action}"<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if target!=""> target="${target}"</#if><#rt/>
<#if enctype!=""> enctype="${enctype}"</#if><#rt/>
<#if onsubmit!=""> onsubmit="${onsubmit}"</#if><#rt/>
<#if acceptCharset!=""> accept-charset="${acceptCharset}"</#if><#rt/>
<#include "common-attributes.ftl"/><#rt/>
>
<#assign labelWidth=labelWidth/>
<#nested/><#rt/>
</form>
</#macro>
