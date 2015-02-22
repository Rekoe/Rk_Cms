package com.rekoe.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rekoe.service.ArticleCategoryService;
import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ArticleCategoryRootListDirective implements TemplateDirectiveModel {

	private ArticleCategoryService articleCategoryService;

	public ArticleCategoryRootListDirective(ArticleCategoryService articleCategoryService) {
		this.articleCategoryService = articleCategoryService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		int count = DirectiveUtils.getInt("count", params);
		localHashMap.put("articleCategories", articleCategoryService.findRoots(count));
		DirectiveUtils.setVariables(localHashMap, env, body);
	}
}
