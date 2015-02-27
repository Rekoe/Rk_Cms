package com.rekoe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Dao;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.common.page.Pagination;
import com.rekoe.crawler.core.CrawlerController;
import com.rekoe.crawler.core.constants.CrawlerConfig;
import com.rekoe.crawler.core.data.ContentBean;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.filter.BriefAreaFilter;
import com.rekoe.crawler.core.filter.CommentAreaFilter;
import com.rekoe.crawler.core.filter.CommentFilter;
import com.rekoe.crawler.core.filter.CommentIndexFilter;
import com.rekoe.crawler.core.filter.CommentLinkFilter;
import com.rekoe.crawler.core.filter.ContentAreaFilter;
import com.rekoe.crawler.core.filter.FieldFilter;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.filter.LinkAreaFilter;
import com.rekoe.crawler.core.filter.PaginationAreaFilter;
import com.rekoe.domain.Article;
import com.rekoe.domain.CrawlerRule;
import com.rekoe.utils.CommonUtils;

@IocBean(create = "init", fields = { "dao", "articleService", "acquisitionTempService" })
public class CrawlerRuleService extends BaseService<CrawlerRule> {

	private final static Log log = Logs.get();

	private ArticleService articleService;
	private AcquisitionTempService acquisitionTempService;

	public CrawlerRuleService() {
		super();
	}

	public Pagination getListByPager(Integer pageNumber, int pageSize) {
		return getObjListByPager(pageNumber, pageSize, null);
	}

	public void insert(CrawlerRule acqu) {
		dao().insert(acqu);
	}

	public void update(CrawlerRule acqu) {
		dao().update(acqu);
	}

	public void delete(CrawlerRule acqu) {
		dao().delete(acqu);
	}

	public CrawlerRuleService(Dao dao, ArticleService articleService, AcquisitionTempService acquisitionTempService) {
		super(dao);
		this.articleService = articleService;
		this.acquisitionTempService = acquisitionTempService;
	}

	@Inject
	private PropertiesProxy gatheConf;

	public void init() {
		CrawlerConfig.threadNum = gatheConf.getInt("threadNum", CrawlerConfig.threadNum);
		CrawlerConfig.taskTimeOut = gatheConf.getInt("taskTimeOut", CrawlerConfig.taskTimeOut);
		CrawlerConfig.resSaveRootPath = gatheConf.get("resSaveRootPath", CrawlerConfig.resSaveRootPath);
		CrawlerConfig.resSavePath = gatheConf.get("resSavePath", CrawlerConfig.resSavePath);
		CrawlerConfig.extractResType = gatheConf.get("extractResType", CrawlerConfig.extractResType);
		CrawlerConfig.extractMediaResType = gatheConf.get("extractMediaResType", CrawlerConfig.extractMediaResType);
		CrawlerConfig.replaceResName = gatheConf.get("replaceName", CrawlerConfig.replaceResName);
		CrawlerConfig.proxyServerList = populateProxyServer(gatheConf.get("proxyServerList"));
		CrawlerConfig.systemRootPath = gatheConf.get("systemRootPath", CrawlerConfig.systemRootPath);
		CrawlerConfig.httpClientMaxConn = gatheConf.getInt("httpClientMaxConn", CrawlerConfig.httpClientMaxConn);
		CrawlerConfig.httpClientMaxRoute = gatheConf.getInt("httpClientMaxRoute", CrawlerConfig.httpClientMaxRoute);
		CrawlerConfig.httpConnTimeout = gatheConf.getInt("httpConnTimeout", CrawlerConfig.httpConnTimeout);
		CrawlerConfig.httpSocketTimeout = gatheConf.getInt("httpSocketTimeout", CrawlerConfig.httpSocketTimeout);
		CrawlerConfig.defaultWords = gatheConf.get("defaultWords", CrawlerConfig.defaultWords);
		CrawlerConfig.defaultCommonReplaceWords = CommonUtils.populateWordsMap(gatheConf.get("defaultCommonReplaceWords"));
		log.info("爬虫配置文件加载完成");

	}

	/**
	 * 组装代理服务器列表
	 */
	private List<Map<String, Integer>> populateProxyServer(String proxyServerStr) {
		List<Map<String, Integer>> resultList = new ArrayList<Map<String, Integer>>();
		String[] proxyArr = proxyServerStr.split(",");
		String[] tempArr = null;
		Map<String, Integer> proxyMap = null;
		for (String proxy : proxyArr) {
			tempArr = proxy.split(":");
			if (tempArr.length == 2 && StringUtils.isNotBlank(tempArr[0]) && StringUtils.isNotBlank(tempArr[1])) {
				proxyMap = new HashMap<String, Integer>();
				proxyMap.put(StringUtils.trim(tempArr[0]), Integer.parseInt(StringUtils.trim(tempArr[1])));
				resultList.add(proxyMap);
			}
		}
		return resultList;
	}

	public void start(long id) {
		CrawlerRule rule = fetch(id);
		CrawlerController crawlController = new CrawlerController();
		List<Filter<String, ?>> filters = new ArrayList<Filter<String, ?>>();
		filters.add(new LinkAreaFilter(rule.getLinksetStart(), rule.getLinksetEnd()));
		filters.add(new ContentAreaFilter(rule.getContentStart(), rule.getContentEnd()));
		filters.add(new BriefAreaFilter(rule.getDescriptionStart(), rule.getDescriptionEnd()));
		filters.add(new PaginationAreaFilter(rule.getPaginationStart(), rule.getPaginationEnd()));
		filters.add(new CommentIndexFilter(rule.getCommentIndexStart(), rule.getCommentIndexEnd()));
		filters.add(new CommentAreaFilter(rule.getCommentAreaStart(), rule.getCommentAreaEnd()));
		filters.add(new CommentFilter(rule.getCommentStart(), rule.getCommentEnd()));
		filters.add(new CommentLinkFilter(rule.getCommentLinkStart(), rule.getCommentLinkEnd()));

		// 添加扩展字段过滤器
		if (StringUtils.isNotEmpty(rule.getKeywordsStart())) {
			addFilter(rule.getKeywordsStart(), filters);
		}

		CrawlScope crawlScope = new CrawlScope();
		crawlScope.setArticleCategoryId(rule.getArticleCategoryId());
		// crawlScope.setCrawlerPersistent(crawlerPersistent);
		crawlScope.setEncoding(rule.getPageEncoding());
		crawlScope.setId(rule.getId());
		crawlScope.setFilterList(filters);
		// 评论内容列表是否与内容页分离，如果填写了,则为true
		if (StringUtils.isNotEmpty(rule.getCommentIndexStart())) {
			crawlScope.setCommentListIsAlone(true);
		}
		crawlScope.setRepairPageUrl(rule.getLinkStart());
		crawlScope.setRepairImageUrl(rule.getLinkEnd());
		// 设置休眠时间
		crawlScope.setSleepTime(rule.getPauseTime());
		crawlScope.setPaginationRepairUrl(rule.getPaginationRepairUrl());
		// 是否下载图片至本地
		crawlScope.setExtractContentRes(rule.isExtractContentRes());
		// 是否去掉内容中连接
		crawlScope.setReplaceHtmlLink(rule.isReplaceHtmlLink());
		crawlScope.setAllowRepeat(rule.isRepeatCheckType());
		crawlScope.setUseProxy(rule.isUseProxy());
		crawlScope.setProxyAddress(rule.getProxyAddress());
		crawlScope.setProxyPort(rule.getProxyPort());
		crawlScope.setReplaceWords(rule.getReplaceWords());
		crawlScope.addSeeds(rule.getAllPlans());
		crawlController.initialize(crawlScope, this);
		crawlController.start();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addFilter(String jsonStr, List<Filter<String, ?>> filters) {
		List<Map> arry = Json.fromJsonAsList(Map.class, jsonStr);
		String fields = "", filterStart = "", filterEnd = "";
		for (int i = 0; i < arry.size(); i++) {
			Map<String, String> map = arry.get(i);
			if (null != map.get("fields")) {
				fields = map.get("fields");
			}
			if (null != map.get("filterStart")) {
				filterStart = map.get("filterStart");
			}
			if (null != map.get("filterEnd")) {
				filterEnd = map.get("filterEnd");
			}
			filters.add(new FieldFilter(fields, filterStart, filterEnd));
		}
	}

	public void addArticle(Task task) {
		ContentBean contentBean = task.getContentBean();
		Date tim = Times.now();
		Article art = new Article();
		art.setAuthor("admin");
		art.setCreateDate(tim);
		art.setModifyDate(tim);
		art.setTitle(contentBean.getTitle());
		art.setContent(contentBean.getContent());
		art.setArticleCategoryId(task.getArticleCategoryId());
		articleService.insert(art);
	}

	public void addTempList(List<CrawlLinkURI> urlList) {
		acquisitionTempService.save(urlList);
	}

	public void deleteTemp() {
		acquisitionTempService.delete();
	}
}
