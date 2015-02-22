package com.rekoe.web.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Cnd;

import com.rekoe.service.ArticleService;
import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author 科技㊣²º¹³ 
 * 2014年2月5日 下午6:16:26 
 * http://www.rekoe.com 
 * QQ:5382211
 */
public class ArticleListDirective implements TemplateDirectiveModel {
	private ArticleService articleService;

	public ArticleListDirective(ArticleService articleService) {
		this.articleService = articleService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		String id = DirectiveUtils.getString("articleCategoryId", params);
		String desc = DirectiveUtils.getString("desc", params);
		int count = DirectiveUtils.getInt("count", params);
		localHashMap.put("articles", articleService.getListByCnd(Cnd.where("articleCategoryId", "=", id).limit(1, count).desc(desc)));
		DirectiveUtils.setVariables(localHashMap, env, body);
	}
}
