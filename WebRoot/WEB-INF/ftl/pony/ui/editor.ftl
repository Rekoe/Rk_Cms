<#--
<textarea name="textarea"></textarea>
-->
<#macro editor
	name value="" height="230"
	fullPage="false" toolbarSet="My"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	maxlength="65535"
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<script type="text/javascript">
<#local editorBasePath="${base}/thirdparty/fckeditor/" filemanager="${editorBasePath}editor/filemanager/browser/default/browser.html"/>
var ${name} = new FCKeditor("${name}");
${name}.BasePath = "${editorBasePath}";
<#--
${name}.Config["CustomConfigurationsPath"]="${base}/thirdparty/fckeditor/myconfig.js?d="+new Date()*1;
-->
${name}.Config["CustomConfigurationsPath"]="${base}/thirdparty/fckeditor/myconfig.js";

${name}.Config["LinkBrowser"] = false ;
${name}.Config["ImageBrowser"] = false ;
${name}.Config["FlashBrowser"] = false ;
${name}.Config["MediaBrowser"] = false ;
<#if fullPage=="true">
${name}.Config["FullPage"]=true;
</#if>

${name}.Config["LinkBrowserURL"] = "${filemanager}?Connector=${base+appBase}/fck/connector" ;
${name}.Config["ImageBrowserURL"] = "${filemanager}?Type=Image&Connector=${base+appBase}/fck/connector" ;
${name}.Config["FlashBrowserURL"] = "${filemanager}?Type=Flash&Connector=${base+appBase}/fck/connector" ;
${name}.Config["MediaBrowserURL"] = "${filemanager}?Type=Media&Connector=${base+appBase}/fck/connector" ;

${name}.Config["LinkUpload"] = true ;
${name}.Config["ImageUpload"] = true ;
${name}.Config["FlashUpload"] = true ;
${name}.Config["MediaUpload"] = true ;

${name}.Config["LinkUploadURL"] = "${base+appBase}/fck/upload" ;
${name}.Config["ImageUploadURL"] = "${base+appBase}/fck/upload?Type=Image" ;
${name}.Config["FlashUploadURL"] = "${base+appBase}/fck/upload?Type=Flash" ;
${name}.Config["MediaUploadURL"] = "${base+appBase}/fck/upload?Type=Media" ;

${name}.ToolbarSet="${toolbarSet}";
${name}.Height=${height};
${name}.Value="${value!?js_string}";
${name}.Create();
</script>
<#include "control-close.ftl"/><#rt/>
</#macro>