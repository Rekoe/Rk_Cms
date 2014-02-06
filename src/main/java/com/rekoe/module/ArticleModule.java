package com.rekoe.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.ArticleService;

@IocBean
@At("/article")
@Filters
public class ArticleModule {

	@Inject
	private ArticleService articleService;
	@Inject
	private ArticleCategoryService articleCategoryService;

	@At("/list/?")
	@Ok("fm:template.front.article.list")
	public Pagination list(String id, @Param("pageNumber") Integer pageNumber, HttpServletRequest req) {
		req.setAttribute("articleCategory", articleCategoryService.fetch(id));
		return articleService.getArticleListByPager(pageNumber, 20, id);
	}

	@At("/view/?")
	@Ok("fm:template.front.article.content")
	public Article view(String id) {
		return articleService.fetchByID(id);
	}

	@At("/hits/?")
	@Ok("raw")
	public int hits(String id) {
		articleService.update(Chain.makeSpecial("hits", "+1"), Cnd.where("id", "=", id));
		Article art = view(id);
		return art.getHits();
	}

	@At
	@Ok("fm:template.front.article.search")
	public Pagination search(HttpServletRequest req, @Param("keyword") String keyword, @Param("pageNumber") Integer pageNumber) {
		req.setAttribute("articleKeyword", keyword);
		return articleService.getObjListByPager(pageNumber, keyword);
	}

}
