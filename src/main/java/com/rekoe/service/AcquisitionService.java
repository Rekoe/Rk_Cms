package com.rekoe.service;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.http.Http;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.AcquisitionTemp;
import com.rekoe.domain.Article;
import com.rekoe.domain.CmsAcquisition;
import com.rekoe.utils.HtmlParserImpl;
import com.rekoe.utils.ParseHtmlTool;

@IocBean(fields = { "dao", "articleService", "acquisitionTempService" })
public class AcquisitionService extends BaseService<CmsAcquisition> {

	private final static Log log = Logs.get();

	private ArticleService articleService;

	private AcquisitionTempService acquisitionTempService;

	public AcquisitionService() {
		super();
	}

	public AcquisitionService(Dao dao, ArticleService articleService, AcquisitionTempService acquisitionTempService) {
		super(dao);
		this.articleService = articleService;
		this.acquisitionTempService = acquisitionTempService;
	}

	public Pagination getListByPager(Integer pageNumber, int pageSize) {
		return getObjListByPager(dao(), pageNumber, pageSize, null, CmsAcquisition.class);
	}

	public void insert(CmsAcquisition acqu) {
		dao().insert(acqu);
	}

	public void update(CmsAcquisition acqu) {
		dao().update(acqu);
	}

	public void delete(CmsAcquisition acqu) {
		dao().delete(acqu);
	}

	public boolean start(int id) {
		CmsAcquisition acqu = dao().fetch(CmsAcquisition.class, Cnd.where("id", "=", id));
		Thread thread = new AcquisitionThread(acqu);
		thread.start();
		return true;
	}

	private class AcquisitionThread extends Thread {
		private CmsAcquisition acqu;
		/** HTML解析工具类 */
		private ParseHtmlTool parseHtmlTool;
		private List<String> urlList;
		private List<String> titleList;

		public AcquisitionThread(CmsAcquisition acqu) {
			super(acqu.getClass().getName() + "#" + acqu.getId());
			this.acqu = acqu;
			parseHtmlTool = new HtmlParserImpl(acqu);
			String url = acqu.getPlanUrl();
			String html = Http.get(url.trim()).getContent(acqu.getPageEncoding());
			this.urlList = parseHtmlTool.getUrlList(html);
			this.titleList = parseHtmlTool.getTitleList(html);
			acquisitionTempService.save(titleList,urlList);
		}

		@Override
		public void run() {
			if (acqu == null) {
				return;
			}
			long tStart = System.currentTimeMillis();
			log.info(Thread.currentThread().getName() + "开始...");
			try {
				while (true) {
					AcquisitionTemp temp = acquisitionTempService.getAcquisitionTemp();
					if (Lang.isEmpty(temp)) {
						break;
					}
					saveContent(temp.getContentUrl(), temp.getTitle(), acqu.getArticleCategoryId());
					acquisitionTempService.delete(temp.getId());
					Lang.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(null, e);
			}
			log.info(Thread.currentThread().getName() + "结束.");
			long tEnd = System.currentTimeMillis();
			log.info("总共用时:" + (tEnd - tStart) + "millions");
			log.infof("Acquisition#{%s} complete", acqu.getId());
		}

		private void saveContent(String url, String title, String articleCategoryId) {
			try {
				Date now = Times.now();
				String html = Http.get(url.trim()).getContent(acqu.getPageEncoding());
				String txt = parseHtmlTool.getHtml(html);
				Article art = new Article();
				art.setArticleCategoryId(articleCategoryId);
				art.setTitle(title);
				art.setContent(txt);
				art.setCreateDate(now);
				art.setModifyDate(now);
				art.setAuthor("admin");
				articleService.insert(art);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
}
