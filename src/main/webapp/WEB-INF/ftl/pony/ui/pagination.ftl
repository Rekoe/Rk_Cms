<#macro page value listAction="list">
<input type="hidden" id="pageSize" name="pageSize" value="${value.pageSize}" />
<#assign firstPageNumber=1>
<#assign lastPageNumber=value.totalPage>
<#if (value.totalCount > 1)>
	<div class="pagination">
		<#if value.firstPage>
			<span class="firstPage">&nbsp;</span>
		<#else>
			<a class="firstPage" href="javascript: $.pageSkip(1);">&nbsp;</a>
		</#if>
		<#if (value.totalPage > 1)>
			<a class="previousPage" href="javascript: $.pageSkip(${value.prePage});">&nbsp;</a>
		<#else>
			<span class="previousPage">&nbsp;</span>
		</#if>
		<#list value.segment as segmentPageNumber>
			<#if segmentPageNumber_index == 0 && (segmentPageNumber > firstPageNumber + 1)>
				<span class="pageBreak">...</span>
			</#if>
			<#if segmentPageNumber != value.pageNo>
				<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
			<#else>
				<span class="currentPage">${value.pageNo}</span>
			</#if>
			<#if !segmentPageNumber_has_next && (segmentPageNumber < lastPageNumber - 1)>
				<span class="pageBreak">...</span>
			</#if>
		</#list>
		<#if (value.totalPage > value.pageNo)>
			<a class="nextPage" href="javascript: $.pageSkip(${value.nextPage});">&nbsp;</a>
		<#else>
			<span class="nextPage">&nbsp;</span>
		</#if>
		<#if value.lastPage>
			<span class="lastPage">&nbsp;</span>
		<#else>
			<a class="lastPage" href="javascript: $.pageSkip(${value.totalPage});">&nbsp;</a>
		</#if>
		<span class="pageSkip">
			<@s.ma "admin.page.totalPages" "${value.totalPage}"/>  <@s.ma "admin.page.pageNumber" "<input id='pageNumber' name='pageNumber' value='${value.pageNo}' maxlength='9' onpaste='return false;' />"/>  &nbsp;<button type="submit">&nbsp;</button>
		</span>
	</div>
</#if>
	</#macro>