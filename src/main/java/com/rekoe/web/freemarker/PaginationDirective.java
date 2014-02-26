package com.rekoe.web.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PaginationDirective implements TemplateDirectiveModel {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		int pageNumber = DirectiveUtils.getInt("pageNumber", params);
		int totalPages = DirectiveUtils.getInt("totalPages", params);
		int segmentCount = DirectiveUtils.getInt("segmentCount", params);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		totalPages = totalPages < 1 ? 1 : totalPages;
		segmentCount = segmentCount < 1 ? 5 : segmentCount;
		boolean hasPrevious = pageNumber > 1;
		boolean hasNext = pageNumber < totalPages;
		boolean isFirst = pageNumber == 1;
		boolean isLast = (pageNumber == totalPages);
		int previousPageNumber = pageNumber - 1;
		int nextPageNumber = pageNumber + 1;
		int firstPageNumber = 1;
		int lastPageNumber = totalPages;
		int minPage = pageNumber - (int) Math.floor((segmentCount - 1) / 2.0D);
		int maxPage = pageNumber + (int) Math.ceil((segmentCount - 1) / 2.0D);
		minPage = minPage < 1 ? 1 : minPage;
		maxPage = maxPage > totalPages ? totalPages : maxPage;
		List<Integer> localArrayList = new ArrayList<Integer>();
		for (int i = minPage; i <= maxPage; i++) {
			localArrayList.add(i);
		}
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		localHashMap.put("pageNumber", pageNumber);
		localHashMap.put("totalPages", totalPages);
		localHashMap.put("segmentCount", segmentCount);
		localHashMap.put("hasPrevious", hasPrevious);
		localHashMap.put("hasNext", hasNext);
		localHashMap.put("isFirst", isFirst);
		localHashMap.put("isLast", isLast);
		localHashMap.put("previousPageNumber", previousPageNumber);
		localHashMap.put("nextPageNumber", nextPageNumber);
		localHashMap.put("firstPageNumber", firstPageNumber);
		localHashMap.put("lastPageNumber", lastPageNumber);
		localHashMap.put("segment", localArrayList);
		DirectiveUtils.setVariables(localHashMap, env, body);
	}
}
