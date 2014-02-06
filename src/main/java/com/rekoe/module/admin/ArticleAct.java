package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
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
		return articleService.getArticleListByPager(pageNumber, 20,null);
	}

	@At
	@Ok("fm:template.admin.article.add")
	public List<ArticleCategory> add() {
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		return list;
	}

	@At
	@Ok(">>:/admin/article/list.rk")
	@RequiresAuthentication
	public boolean save(@Param("articleCategoryId") String articleCategoryId,
			@Param("title") String title, @Param("content") String content,
			@Param("::art.") Article article) {
		article.setCreateDate(Times.now());
		article.setModifyDate(Times.now());
		article.setContent(content);
		article.setArticleCategoryId(articleCategoryId);
		article.setTitle(title);
		articleService.insert(article);
		return true;
	}

	@At
	@Ok("fm:template.admin.article.edit.rk")
	public List<ArticleCategory> edit(String id, HttpServletRequest req) {
		Article art = articleService.fetchByID(id);
		art = articleService.dao().fetchLinks(art, "articleCategory");
		List<ArticleCategory> list = articleCategoryService.query(null, null);
		req.setAttribute("article", art);
		return list;
	}

	@At
	@Ok(">>:/admin/article/list.rk")
	public boolean update(@Param("content")String content,@Param("::article.") Article article,@Param("title")String title,@Param("articleCategoryId")String articleCategoryId) {
		articleService.update(Chain.make("title", title).add("articleCategoryId", articleCategoryId).add("content", content).add("modifyDate", Times.now()), Cnd.where("id", "=", article.getId()));
		return true;
	}

	@At
	@Ok("json")
	public Message delete(@Param("ids") String[] ids, HttpServletRequest req) {
		articleService.delete(ids);
		return Message.success("admin.message.success", req);
	}
}
