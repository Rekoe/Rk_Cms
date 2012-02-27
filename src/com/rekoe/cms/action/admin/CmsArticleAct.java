package com.rekoe.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.cms.common.page.Pagination;
import com.rekoe.cms.dao.MessageTypeDao;
import com.rekoe.cms.model.Article;
import com.rekoe.cms.model.NavModel;
import com.rekoe.cms.model.Templete;
import com.rekoe.cms.model.selector.TemplateType;
import com.rekoe.cms.utils.SystemContext;
import com.rk.cms.controller.BaseAction;

@IocBean
@InjectName
public class CmsArticleAct extends BaseAction{

	@Inject
	private MessageTypeDao messageTypeDao;
	/**
	 * 分页查询页面数据
	 * 
	 * @param currentPage
	 * @param ioc
	 */
	@At("/admin/article/list")
	@Ok("fm:cms_sys.article.list")
	public void list(@Param("pageNo") Integer pageNo,HttpServletRequest request) {
		Pagination pagination = basicDao.getPage(Article.class, getPage(pageNo), SystemContext.PAGE_SIZE, "createDate");
		request.setAttribute("pagination", pagination);
	}
	
	/**
	 * 根据id查找数据
	 * 
	 * @param id
	 * @param ioc
	 * @return
	 */
	@At("/admin/article/edit")
	@Ok("fm:cms_sys.article.edit")
	public void find(@Param("id") int id,HttpServletRequest request) {
		Article a = basicDao.find(id, Article.class);
		a = basicDao.findLink(a, "nav");
		request.setAttribute("art", a);
		TemplateType t = null;
		switch (a.getStyleid()) {
		case 0:
			t = TemplateType.ABOUT;
			break;
		case 1:
			t = TemplateType.CACAT;
			break;
		case 2:
			t = TemplateType.DETAIL;
			break;
		case 3:
			t = TemplateType.LIST;
			break;
		case 4:
			t = TemplateType.MESSAGE;
			break;
		case 5:
			t = TemplateType.DOWNLOAD;
			break;
		case 6:
			t = TemplateType.GROUP;
			break;
		default:
			break;
		}
		List<Templete> ts = null;
		if (t != null) {
			ts = basicDao.search(Templete.class, Cnd.where("type", "=", t).desc("id"));
		} else {
			ts = basicDao.search(Templete.class, "id");
		}
		request.setAttribute("template", ts);
		SqlExpression e = Cnd.exp("pid", "=", 0);
		List<NavModel> models = messageTypeDao.search(NavModel.class, Cnd.where(e).desc("sortNumber"));
		for (NavModel model : models) {
			model = messageTypeDao.findLink(model, "children");
		}
		models = getListForSelect(models);
		for (NavModel model : models) {
			model = messageTypeDao.findLink(model, "parent");
		}
		request.setAttribute("models", models);
	}
}
