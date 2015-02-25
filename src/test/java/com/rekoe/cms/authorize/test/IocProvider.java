package com.rekoe.cms.authorize.test;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.constants.PropertiesCrawlerConfig;
import com.rekoe.domain.CrawlerRule;

/**
 * TODO 实际项目中获取ioc的方式可灵活决定
 * 
 */
public class IocProvider {
	private static Log logger = Logs.get();

	private static Ioc ioc;

	public static void init() {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader", "ioc/", "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.rekoe"));
			//new PropertiesCrawlerConfig().loadCrawlerConfig("gathe_core.properties");
		} catch (ClassNotFoundException e) {
			logger.error("Ioc create error", e);
		} catch (Exception e) {
			logger.error("Ioc create error", e);
		}
	}

	public static Ioc ioc() {
		if (ioc == null) {
			init();
		}
		return ioc;
	}

	public static void main(String[] args) {
		Dao dao = ioc().get(Dao.class);
		List<CrawlerRule> list = dao.query(CrawlerRule.class, null);
		System.out.println(list);
	}
}
