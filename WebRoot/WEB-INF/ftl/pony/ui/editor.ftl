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
            var editor = null;
            KindEditor.ready(function(k){
                editor = k.create('#${name}',{
                    width: '100%',
                    height: 370,
                    resizeType: 0,
                    cssPath : ['${base}/kindeditor4/plugins/code/prettify.css'],
                    filterMode: true,
                    allowFileManager: true,
                    uploadJson: '${base}/nutz/ke4plugin/upload',
                    fileManagerJson : '${base}/nutz/ke4plugin/manager'
                });
                editor.html($("#${name}").html());
            });
        </script>
<textarea id="${name}" cols="0" rows="0" style="visibility:hidden">${value!?js_string}</textarea>
<#include "control-close.ftl"/><#rt/>
</#macro>