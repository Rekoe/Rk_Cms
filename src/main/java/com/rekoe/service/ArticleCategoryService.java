package com.rekoe.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import com.rekoe.domain.ArticleCategory;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
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

	public StringBuffer loadSelect(String actid) {
		List<ArticleCategory> list = dao().query(ArticleCategory.class, Cnd.where("grade", "=", 0));
		;
		for (ArticleCategory actCategory : list) {
			loadChildren(actCategory);
		}
		StringBuffer sb = new StringBuffer();
		for (ArticleCategory articleCategory : list) {
			sb.append("<option value=").append('"').append(articleCategory.getId()).append('"');
			if (StringUtils.equals(articleCategory.getId(), actid)) {
				sb.append(" selected=\"selected\"");
			}
			sb.append(">");
			sb.append(articleCategory.getName()).append("</option>").append("\n");
			loadChildren(articleCategory, sb, actid);
		}
		return sb;
	}

	private ArticleCategory loadChildren(ArticleCategory articleCategory, StringBuffer sb, String actid) {
		Set<ArticleCategory> childrens = articleCategory.getChildren();
		if (Lang.isEmpty(childrens)) {
			return null;
		}
		for (ArticleCategory children : childrens) {
			sb.append("<option value=").append('"').append(children.getId()).append('"');
			if (StringUtils.equals(children.getId(), actid)) {
				sb.append(" selected=\"selected\"");
			}
			sb.append(">");
			int deep = children.getGrade();
			for (int i = 1; i <= deep; i++) {
				sb.append("&nbsp;&nbsp;");
				if (i == deep) {
					sb.append("|-");
				}
			}
			sb.append(children.getName()).append("</option>").append("\n");
			loadChildren(children, sb, actid);
		}
		return articleCategory;
	}

	/**
	 * 加载子分类
	 */
	private ArticleCategory loadChildren(ArticleCategory actCategory) {
		actCategory = dao().fetchLinks(actCategory, "children");
		Set<ArticleCategory> childrens = actCategory.getChildren();
		if (Lang.isEmpty(childrens)) {
			return null;
		}
		for (ArticleCategory ac : childrens) {
			loadChildren(ac);
		}
		return actCategory;
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

	public List<ArticleCategory> findRoots(int count) {
		// "select * from article_category as articleCategory where articleCategory.parent is null order by articleCategory.order asc";
		List<ArticleCategory> list = dao().query(getEntityClass(), Cnd.where("grade", "=", 0).limit(1, count).desc("order"));
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
