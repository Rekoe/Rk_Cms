package com.rekoe.web.freemarker;

import java.io.IOException;
import java.util.Map;

import com.rekoe.service.ArticleCategoryService;
import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ArticleCategoryTreeDirective implements TemplateDirectiveModel {

	private ArticleCategoryService articleCategoryService;

	public ArticleCategoryTreeDirective(ArticleCategoryService articleCategoryService) {
		this.articleCategoryService = articleCategoryService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String categoryid = DirectiveUtils.getString("categoryid", params);
		StringBuffer sb = articleCategoryService.loadSelect(categoryid);
		env.getOut().append(sb);
	}
}
