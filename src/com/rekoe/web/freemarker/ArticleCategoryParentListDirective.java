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

/**
 * @author 科技㊣²º¹³ 2014年2月5日 下午6:16:58 http://www.rekoe.com QQ:5382211
 */
public class ArticleCategoryParentListDirective implements TemplateDirectiveModel {

	private ArticleCategoryService articleCategoryService;

	public ArticleCategoryParentListDirective(ArticleCategoryService articleCategoryService) {
		this.articleCategoryService = articleCategoryService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		String id = DirectiveUtils.getString("articleCategoryId", params);
		localHashMap.put("articleCategories", articleCategoryService.findParents(articleCategoryService.fetch(id)));
		DirectiveUtils.setVariables(localHashMap, env, body);
	}
}
