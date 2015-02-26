package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.service.ArticleCategoryService;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
@IocBean
@At("/admin/article_category")
@RequiresAuthentication
public class ArticleCategoryAct {

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
	public ArticleCategory edit(@Param("id") String id, HttpServletRequest req) {
		ArticleCategory articleCategory = articleCategoryService.fetch(id);
		req.setAttribute("articleCategoryTree", list());
		req.setAttribute("children", articleCategoryService.findChildren(articleCategory));
		return articleCategory;
	}

	@At
	@Ok(">>:/admin/article_category/list.rk")
	public void save(@Param("name") String name, @Param("order") int order, final @Param("::ac.") ArticleCategory ac) {
		ParentCallBack callBack = new ParentCallBack() {
			int i = 0;

			@Override
			public void invoke() {
				i++;
			}

			@Override
			public int getCount() {
				return i;
			}
		};
		checkCategoryGread(ac.getParentId(), callBack);
		ac.setGrade(callBack.getCount());
		ac.setCreateDate(Times.now());
		ac.setModifyDate(Times.now());
		ac.setName(name);
		ac.setOrder(order);
		articleCategoryService.insert(ac);
	}

	private String checkCategoryGread(String parentid, ParentCallBack callBack) {
		boolean haveParent = StringUtils.isNotBlank(parentid);
		if (haveParent) {
			callBack.invoke();
			ArticleCategory articleCategory = articleCategoryService.fetch(parentid);
			if (!Lang.isEmpty(articleCategory)) {
				String tempParentid = checkCategoryGread(articleCategory.getParentId(), callBack);
				if (StringUtils.isBlank(tempParentid)) {
					return "";
				} else {
					checkCategoryGread(tempParentid, callBack);
				}
			}
		}
		return "";
	}

	public abstract interface ParentCallBack {
		public void invoke();

		public int getCount();
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
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	public List<ArticleCategory> by_channel(@Param("channelId") String id) {
		return articleCategoryService.getChildrenList(id);
	}
}
