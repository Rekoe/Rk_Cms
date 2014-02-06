package com.rekoe.service;

import java.util.Collections;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.rekoe.domain.ArticleCategory;

@IocBean(fields = { "dao" })
public class ArticleCategoryService extends IdEntityService<ArticleCategory> {
	public ArticleCategoryService() {
		super();
	}

	public ArticleCategoryService(Dao dao) {
		super(dao);
	}

	public List<ArticleCategory> getList() {
		List<ArticleCategory> list = dao().query(ArticleCategory.class, null);
		return list;
	}

	public void insert(ArticleCategory ac) {
		dao().insert(ac);
	}

	public ArticleCategory fetch(String id) {
		ArticleCategory ac = fetch(Cnd.where("id", "=", id));
		dao().fetchLinks(ac, "articleSet");
		dao().fetchLinks(ac, "children");
		return ac;
	}

	public void delete(String id) {
		dao().clear(getEntityClass(), Cnd.where("id", "=", id));
	}

	public List<ArticleCategory> findRoots() {
		// "select * from article_category as articleCategory where articleCategory.parent is null order by articleCategory.order asc";
		List<ArticleCategory> list = dao().query(getEntityClass(), Cnd.where("parentId", "=", null).limit(10).desc("order"));
		for (ArticleCategory ac : list) {
			dao().fetchLinks(ac, "children");
		}
		return list;
	}

	public List<ArticleCategory> findParents(ArticleCategory articleCategory) {
		if ((articleCategory == null) || (articleCategory.getParent() == null))
			return Collections.emptyList();
		// "select articleCategory from ArticleCategory articleCategory where articleCategory.id in (:ids) order by articleCategory.grade asc";
		return dao().query(getEntityClass(), Cnd.where("id", "in", articleCategory.getTreePaths()).limit(10).asc("grade"));
	}

	public List<ArticleCategory> findChildren(ArticleCategory articleCategory) {
		if (articleCategory != null) {
			// "select articleCategory from ArticleCategory articleCategory where articleCategory.treePath like :treePath order by articleCategory.order asc";
			return dao().query(getEntityClass(), Cnd.where("treePath", "like", "%," + articleCategory.getId() + "," + "%").limit(10).asc("order"));
		} else {
			// "select articleCategory from ArticleCategory articleCategory order by articleCategory.order asc";
			return dao().query(getEntityClass(), Cnd.NEW().limit(10).asc("order"));
		}
	}
}
