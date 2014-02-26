package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;

@IocBean
@At("/admin/article_category")
@RequiresAuthentication
public class ArticleCategoryAct {

	private final static Log log = Logs.get();
	@Inject
	private ArticleCategoryService articleCategoryService;

	@At
	@Ok("fm:template.admin.article_category.list")
	public List<ArticleCategory> list() {
		return articleCategoryService.getList();
	}

	@At
	@Ok("fm:template.admin.article_category.add")
	public List<ArticleCategory> add() {
		return articleCategoryService.getList();
	}

	@At
	@Ok("fm:template.admin.article_category.edit")
	public ArticleCategory edit(@Param("id") String id,HttpServletRequest req) {
		ArticleCategory articleCategory = articleCategoryService.fetch(id);
		req.setAttribute("articleCategoryTree", list());
		req.setAttribute("children", articleCategoryService.findChildren(articleCategory));
		return articleCategory;
	}

	@At
	@Ok(">>:/admin/article_category/list.rk")
	public void save(@Param("name") String name, @Param("order") int order, @Param("::ac.") ArticleCategory ac) {
		ac.setCreateDate(Times.now());
		ac.setModifyDate(Times.now());
		ac.setName(name);
		ac.setOrder(order);
		log.info(Json.toJson(ac));
		articleCategoryService.insert(ac);
	}

	@At
	@Ok("json")
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		ArticleCategory ac = articleCategoryService.fetch(id);
		if (!Lang.isEmpty(ac.getArticleSet())) {
			return Message.error("admin.articleCategory.deleteExistArticleNotAllowed", req);
		}
		if (!Lang.isEmpty(ac.getChildren())) {
			return Message.error("admin.articleCategory.deleteExistChildrenNotAllowed", req);
		}
		articleCategoryService.delete(id);
		return Message.success("admin.common.success", req);
	}
}
