package com.rekoe.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.domain.AcquisitionTemp;

@IocBean(fields = { "dao" })
public class AcquisitionTempService extends BaseService<AcquisitionTemp> {

	private final static Log log = Logs.get();

	public AcquisitionTempService() {
		super();
	}

	public AcquisitionTempService(Dao dao) {
		super(dao);
	}

	private AcquisitionTemp newTemp(String title, String contentUrl, int curr, int total) {
		AcquisitionTemp temp = new AcquisitionTemp();
		temp.setContentUrl(contentUrl);
		temp.setTitle(title);
		NumberFormat nf = NumberFormat.getPercentInstance();
		String percent = nf.format((curr * 1F) / (total * 1F));
		temp.setPercent(Integer.parseInt(percent.substring(0, percent.length() - 1)));
		return temp;
	}

	/**
	 * 保存内容
	 */
	public synchronized void save(List<String> urlList, List<String> contentList) {
		Dao dao = dao();
		dao.execute(Sqls.create("truncate acquisition_temp"));
		int total = urlList.size();
		List<AcquisitionTemp> newList = new ArrayList<>();
		for (int i = 1; i <= total; i++) {
			newList.add(newTemp(urlList.get(i - 1), contentList.get(i - 1), i, total));
		}
		dao().fastInsert(newList);
	}

	/**
	 * 保存内容
	 */
	public synchronized void save(List<CrawlLinkURI> urlList) {
		Dao dao = dao();
		dao.execute(Sqls.create("truncate acquisition_temp"));
		int total = urlList.size();
		List<AcquisitionTemp> newList = new ArrayList<>();
		for (int i = 1; i <= total; i++) {
			CrawlLinkURI link = urlList.get(i - 1);
			newList.add(newTemp(link.getUrl(), link.getTitle(), i, total));
		}
		dao().fastInsert(newList);
	}

	public List<AcquisitionTemp> getList() {
		return dao().query(AcquisitionTemp.class, Cnd.orderBy().desc("id"));
	}

	public int getPercent() {
		Sql sql = Sqls.create("select max(percent) from acquisition_temp");
		sql.setCallback(Sqls.callback.integer());
		dao().execute(sql);
		log.info(sql.getInt());
		return sql.getResult() == null ? 0 : sql.getInt();
	}

	public AcquisitionTemp getAcquisitionTemp() {
		return dao().fetch(AcquisitionTemp.class, Cnd.orderBy().desc("id"));
	}

	public void delete() {
		Sql sql = Sqls.create("delete from acquisition_temp order by percent desc limit 1 ");
		dao().execute(sql);
	}
}
