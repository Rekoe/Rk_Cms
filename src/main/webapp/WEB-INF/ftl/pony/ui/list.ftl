<#macro list action="" method="post" target="" id="" name="" showDel=true showSearch=true>
<form<#rt/>
 method="${method}"<#rt/>
 action="${action}"<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if target!=""> target="${target}"</#if>><#rt/>
<div class="bar">
	<a href="add.rk" class="iconButton">
		<span class="addIcon">&nbsp;</span><@s.m "admin.common.add" />
	</a>
	<#if showDel>
	<div class="buttonWrap">
		<a href="javascript:;" id="deleteButton" class="iconButton disabled">
			<span class="deleteIcon">&nbsp;</span><@s.m "admin.common.delete" />
		</a>
		<a href="javascript:;" id="refreshButton" class="iconButton">
			<span class="refreshIcon">&nbsp;</span><@s.m "admin.common.refresh" />
		</a>
	</div>
	</#if>
	<#if showSearch>
	<div class="menuWrap">
		<div class="search">
			<span id="searchPropertySelect" class="arrow">&nbsp;</span>
			<input type="text" id="searchValue" name="searchValue" value=" " maxlength="200" />
			<button type="submit">&nbsp;</button>
		</div>
		<div class="popupMenu">
			<ul id="searchPropertyOption">
				<li>
					<a href="javascript:;"class="current" val="title"><@s.m "Article.title" /></a>
				</li>
			</ul>
		</div>
	</div>
	</#if>
</div><#rt/>
<#nested/><#rt/>
</form>
</#macro>