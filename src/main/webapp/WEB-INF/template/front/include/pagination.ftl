<#if (totalPages >1)>
	<div class="pagination">
		<#if isFirst>
			<span class="firstPage">&nbsp;</span>
		<#else>
			<a class="firstPage" href="<@pattern?replace("{pageNumber}", "${firstPageNumber}")?interpret />">&nbsp;</a>
		</#if>
		<#if hasPrevious>
			<a class="previousPage" href="<@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret />">&nbsp;</a>
		<#else>
			<span class="previousPage">&nbsp;</span>
		</#if>
		<#list segment as segmentPageNumber>
			<#if segmentPageNumber_index == 0 && (segmentPageNumber > firstPageNumber + 1)>
				<span class="pageBreak">...</span>
			</#if>
			<#if segmentPageNumber != pageNumber>
				<a href="<@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret />">${segmentPageNumber}</a>
			<#else>
				<span class="currentPage">${segmentPageNumber}</span>
			</#if>
			<#if !segmentPageNumber_has_next && (segmentPageNumber < lastPageNumber - 1)>
				<span class="pageBreak">...</span>
			</#if>
		</#list>
		<#if hasNext>
			<a class="nextPage" href="<@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret />">&nbsp;</a>
		<#else>
			<span class="nextPage">&nbsp;</span>
		</#if>
		<#if isLast>
			<span class="lastPage">&nbsp;</span>
		<#else>
			
			<a class="lastPage" href="<@pattern?replace("{pageNumber}", "${lastPageNumber}")?interpret />">&nbsp;</a>
		</#if>
	</div>
</#if>