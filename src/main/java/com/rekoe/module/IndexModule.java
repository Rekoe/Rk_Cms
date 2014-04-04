package com.rekoe.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.domain.Article;
import com.rekoe.service.ArticleService;
import com.rekoe.service.SettingService;

/**
 * @author 科技㊣²º¹³
 * 2014年2月6日 下午8:58:00
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
public class IndexModule {

	private final static Log log = Logs.get();
	@Inject
	private SettingService settingService;
	@Inject
	private ArticleService articleService;

	@At
	@Ok("fm:template.front.index")
	public List<Article> index(HttpServletRequest req,@Param("signed_request") String code) {
		log.info(code);
		req.setAttribute("setting", settingService.getSetting());
		return articleService.getIndexNewList();
	}
}
