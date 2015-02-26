package com.rekoe.module.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.domain.ArticleCategory;
import com.rekoe.domain.CrawlerRule;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.CrawlerRuleService;

@IocBean
@At("/admin/crawler")
@RequiresAuthentication
public class CrawlerAct {

	@Inject
	private CrawlerRuleService crawlerRuleService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@At
	@Ok("fm:template.admin.crawler.add")
	public List<ArticleCategory> add() {
		return articleCategoryService.getTopList();
	}

	@At
	@Ok("fm:template.admin.crawler.list")
	public Object list(@Param("pageNumber") Integer pageNumber) {
		return crawlerRuleService.getListByPager(pageNumber, 20);
	}

	@At
	@Ok("fm:template.admin.crawler.edit")
	public CrawlerRule edit(@Param("id") long id) {
		CrawlerRule crawlerRule = crawlerRuleService.fetch(id);
		return crawlerRule;
	}
}
