package com.rekoe.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Article;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
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

	public List<Article> getIndexNewList() {
		return dao().query(getEntityClass(), Cnd.NEW().limit(10).desc("id"));
	}

	public void insert(Article art) {
		dao().insert(art);
	}

	public void update(final Article art) {
		Daos.ext(dao(), FieldFilter.create(Article.class, null, "^(createDate|hits)$", true)).update(art);
		// FieldFilter.create(Article.class, null, "^(createDate|hits)$",
		// true).run(new Atom() {
		// @Override
		// public void run() {
		// dao().update(art);
		// }
		// });
	}

	public Article fetchByID(String id) {
		Article art = fetch(Cnd.where("id", "=", id));
		dao().fetchLinks(art, "articleCategory");
		return art;
	}

	public Pagination getArticleListByPager(Integer pageNumber, int pageSize, String articleCategoryId) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, pageSize);
		Cnd cnd = Cnd.where("articleCategoryId", "=", articleCategoryId);
		List<Article> list = dao().query(Article.class, StringUtils.isBlank(articleCategoryId) ? Cnd.orderBy().desc("createDate") : cnd.desc("createDate"), pager);
		for (Article atricle : list) {
			atricle = dao().fetchLinks(atricle, "articleCategory");
		}
		pager.setRecordCount(dao().count(Article.class, StringUtils.isBlank(articleCategoryId) ? null : cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
	
	public Pagination getObjListByPager(Integer pageNumber, String keyWorld) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("title", "like", "%" + keyWorld + "%");
		List<Article> list = dao().query(getEntityClass(), cnd, pager);
		pager.setRecordCount(dao().count(getEntityClass(), cnd));
		Pagination pagination = new Pagination(pageNumber, 20, pager.getRecordCount(), list);
		return pagination;
	}
}
