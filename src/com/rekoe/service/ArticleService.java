package com.rekoe.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;

@IocBean(fields = { "dao" })
public class ArticleService extends BaseService<Article> {
	public ArticleService() {
		super();
	}

	public ArticleService(Dao dao) {
		super(dao);
	}

	public void delete(String[] ids) {
		dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
	}

	public List<Article> list() {
		return query(null, null);
	}

	public void insert(Article art) {
		dao().insert(art);
	}

	public void update(Article art) {
		dao().update(art);
	}

	public Article fetchByID(String id) {
		Article art = fetch(Cnd.where("id", "=", id));
		dao().fetchLinks(art, "articleCategory");
		return art;
	}
	
	public Pagination getArticleListByPager(Integer pageNumber, int pageSize,String articleCategoryId) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, pageSize);
		Cnd cnd = Cnd.where("articleCategoryId", "=",articleCategoryId);
		List<Article> list = dao().query(Article.class, StringUtils.isBlank(articleCategoryId)?null:cnd, pager);
		for (Article atricle : list) {
			atricle = dao().fetchLinks(atricle, "articleCategory");
		}
		pager.setRecordCount(dao().count(Article.class, StringUtils.isBlank(articleCategoryId)?null:cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
