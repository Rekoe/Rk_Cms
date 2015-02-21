package com.rekoe.module.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.domain.Article;
import com.rekoe.service.ArticleService;

@IocBean
@At("/admin/collection")
public class CollectionAct {

	@Inject
	private ArticleService articleService;

	@At
	@Ok("void")
	public void qq(@Param("name") String name) throws IOException {
		String url = "http://bj.jjj.qq.com/" + name + "/";
		Document doc = Jsoup.connect(url).post();
		List<Element> list = doc.getElementsByClass("newBox");
		List<String> linkList = new ArrayList<String>();
		for (Element e : list) {
			List<Element> photoElement = e.getElementsByClass("newimg");
			for (Element pe : photoElement) {
				Elements herfElements = pe.getElementsByAttribute("href");
				String link = herfElements.attr("href");
				linkList.add("http://bj.jjj.qq.com" + link);
			}
		}
		for (String link : linkList) {
			doc = Jsoup.connect(link).get();
			Element e = doc.select("div.hd>h1").first();
			System.out.println(e.ownText());
			String context = doc.getElementById("Cnt-Main-Article-QQ").html();
			System.out.println(context);
			Article art = new Article();
			art.setAuthor("admin");
			art.setTitle(e.ownText());
			art.setContent(context);
			art.setCreateDate(Times.now());
			art.setModifyDate(Times.now());
			art.setArticleCategoryId("6d38b6b1afb4458fb6a2eb5a5ff9641e");
			articleService.insert(art);
		}
	}
}
