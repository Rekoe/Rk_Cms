package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.AcquisitionTemp;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.domain.CrawlerRule;
import com.rekoe.service.AcquisitionTempService;
import com.rekoe.service.ArticleCategoryService;
import com.rekoe.service.CrawlerRuleService;

@IocBean
@At("/admin/crawler")
@RequiresAuthentication
public class CrawlerAct {

	@Inject
	private CrawlerRuleService crawlerRuleService;

	@Inject
	private ArticleCategoryService articleCategoryService;

	@Inject
	private AcquisitionTempService acquisitionTempService;

	@At
	@Ok("fm:template.admin.crawler.add")
	public List<ArticleCategory> add() {
		return articleCategoryService.getTopList();
	}

	@At
	@Ok("fm:template.admin.crawler.list")
	public Object list(@Param("pageNumber") Integer pageNumber) {
		return crawlerRuleService.getListByPager(pageNumber, 20);
	}

	@At
	@Ok("fm:template.admin.crawler.edit")
	public CrawlerRule edit(@Param("id") long id) {
		CrawlerRule crawlerRule = crawlerRuleService.fetch(id);
		return crawlerRule;
	}

	@At
	@Ok("json")
	public Message save(@Param("::crawler.") CrawlerRule crawler, HttpServletRequest req) {
		crawlerRuleService.insert(crawler);
		return Message.success("admin.message.success", req);
	}

	public static void main(String[] args) {
		System.out.println(new Boolean("fales").booleanValue());
	}
	@At
	@Ok("json")
	public Message update(@Param("::crawler.") CrawlerRule crawler, HttpServletRequest req) {
		crawlerRuleService.update(crawler);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	public Message delete(@Param("ids") String[] ids, HttpServletRequest req) {
		crawlerRuleService.delete(ids);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("json")
	public Message start(@Param("id") long id, HttpServletRequest req) {
		crawlerRuleService.start(id);
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.progress_data")
	public int v_progress_data(HttpServletRequest req) {
		List<AcquisitionTemp> list = acquisitionTempService.getList();
		req.setAttribute("list", list);
		return acquisitionTempService.getPercent();
	}

	@At
	@Ok("json")
	public Message v_check_complete(HttpServletRequest req) {
		List<AcquisitionTemp> list = acquisitionTempService.getList();
		if (Lang.isEmpty(list)) {
			return Message.success("admin.message.success", req);
		}
		return Message.error("admin.message.success", req);
	}

	@At
	@Ok("fm:template.admin.article_acquisition.progress")
	public void progress() {

	}
}
