package com.rekoe.module.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.ArticleService;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
@IocBean
@At("/admin/article")
@RequiresAuthentication
public class ArticleAct {

	@Inject
	private ArticleService articleService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@At
	@Ok("fm:template.admin.article.list")
	public Pagination list(@Param("pageNumber") Integer pageNumber) {
		return articleService.getArticleListByPager(pageNumber, 20, null);
	}

	@At
	@Ok("fm:template.admin.article.add")
	public List<ArticleCategory> add() {
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		return list;
	}

	@At
	@Ok("json")
	@RequiresAuthentication
	public Message save(@Param("::art.") Article article, HttpServletRequest req) {
		Date now = Times.now();
		article.setCreateDate(now);
		article.setModifyDate(now);
		articleService.insert(article);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article.edit")
	public List<ArticleCategory> edit(String id, HttpServletRequest req) {
		Article art = articleService.fetchByID(id);
		art = articleService.dao().fetchLinks(art, "articleCategory");
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		req.setAttribute("article", art);
		return list;
	}

	@At
	@Ok("json")
	public Message update(@Param("::art.") Article article, HttpServletRequest req) {
		article.setModifyDate(Times.now());
		articleService.update(article);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	public Message delete(@Param("ids") String[] ids, HttpServletRequest req) {
		articleService.delete(ids);
		return Message.success("admin.message.success", req);
	}
}
