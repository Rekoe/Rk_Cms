package com.rekoe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.nutz.dao.Dao;
import org.nutz.http.Http;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.bean.CrawlerRuleBean;
import com.rekoe.crawler.bean.ExtendFieldsBean;
import com.rekoe.crawler.core.CrawlerController;
import com.rekoe.crawler.core.constants.Constants;
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
import com.rekoe.crawler.core.filter.factory.DefaultFilterFactory;
import com.rekoe.crawler.core.filter.factory.FilterFactory;
import com.rekoe.crawler.core.util.DefaultURIHelper;
import com.rekoe.crawler.core.util.URIHelper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapperImpl;
import com.rekoe.domain.Article;
import com.rekoe.domain.CrawlerRule;
import com.rekoe.utils.CommonUtils;

@IocBean(create = "init", fields = { "dao", "articleService" })
public class CrawlerRuleService extends BaseService<CrawlerRule> {

	private final static Log log = Logs.get();

	private ArticleService articleService;

	public CrawlerRuleService() {
		super();
	}

	public CrawlerRuleService(Dao dao, ArticleService articleService) {
		super(dao);
		this.articleService = articleService;
	}

	public void start(int id) {
		CrawlerRule crawlerRule = fetch(id);
		List<Integer> ruleIdList = new ArrayList<Integer>();
		ruleIdList.add(crawlerRule.getId());
		CrawlerRuleBean crawlerRuleBean = new CrawlerRuleBean();
		crawlerRuleBean.setRuleId(crawlerRule.getId());
		crawlerRuleBean.setStartTime(crawlerRule.getStartTime());
		crawlerRuleBean.setEndTime(crawlerRule.getEndTime());
		crawlerRuleBean.setRuleIdList(ruleIdList);
		//crawlerRuleBean.setRuleBaseBean(Json.fromJson(RuleBaseBean.class, crawlerRule.getRuleBaseConfig()));
		//crawlerRuleBean.setRuleContentBean(Json.fromJson(RuleContentBean.class, crawlerRule.getRuleContentConfig()));
		RunRule runTestRule = new RunRule(crawlerRuleBean);
		Thread currThread = new Thread(runTestRule);
		currThread.start();
	}

	class RunRule implements Runnable {
		private CrawlerRuleBean crawlerRuleBean;
		/** 过滤器工厂 */
		@SuppressWarnings("rawtypes")
		private transient FilterFactory filterFactory;
		/** 爬虫配置参数 */
		private transient CrawlScope crawlScope;
		/** HTML解析器包装类类 */
		private transient HtmlParserWrapper htmlParserWrapper;
		/** URIHelper */
		private transient URIHelper uriHelper;

		public RunRule(CrawlerRuleBean crawlerRuleBean) {
			this.crawlerRuleBean = crawlerRuleBean;
		}

		@SuppressWarnings({ "unchecked" })
		private void init() {
			List<Filter<String, ?>> filters = new ArrayList<Filter<String, ?>>();
			filters.add(new LinkAreaFilter(crawlerRuleBean.getRuleContentBean().getLinksetStart(), crawlerRuleBean.getRuleContentBean().getLinksetEnd()));
			filters.add(new ContentAreaFilter(crawlerRuleBean.getRuleContentBean().getContentStart(), crawlerRuleBean.getRuleContentBean().getContentEnd()));
			filters.add(new BriefAreaFilter(crawlerRuleBean.getRuleContentBean().getDescriptionStart(), crawlerRuleBean.getRuleContentBean().getDescriptionEnd()));
			// filters.add(new
			// PaginationAreaFilter(crawlerRuleBean.getRuleContentPageBean().getPaginationStart(),crawlerRuleBean.getRuleContentPageBean().getPaginationEnd()));
			// filters.add(new
			// CommentIndexFilter(crawlerRuleBean.getRuleCommentBean().getCommentIndexStart(),crawlerRuleBean.getRuleCommentBean().getCommentIndexEnd()));
			// filters.add(new
			// CommentAreaFilter(crawlerRuleBean.getRuleCommentBean().getCommentAreaStart(),crawlerRuleBean.getRuleCommentBean().getCommentAreaEnd()));
			// filters.add(new
			// CommentFilter(crawlerRuleBean.getRuleCommentBean().getCommentStart(),crawlerRuleBean.getRuleCommentBean().getCommentEnd()));
			// filters.add(new
			// CommentLinkFilter(crawlerRuleBean.getRuleCommentBean().getCommentLinkStart(),crawlerRuleBean.getRuleCommentBean().getCommentLinkEnd()));

			crawlScope = new CrawlScope();
			crawlScope.setEncoding(crawlerRuleBean.getRuleBaseBean().getPageEncoding());
			crawlScope.setFilterList(filters);
			crawlScope.addSeeds(crawlerRuleBean.getRuleContentBean().getAllPlans());
			crawlScope.setRepairPageUrl(crawlerRuleBean.getRuleBaseBean().getUrlRepairUrl());

			List<Filter<String, Map<String, String>>> midFilters = new ArrayList<Filter<String, Map<String, String>>>();
			// 添加过度连接过滤器
			if (null != crawlerRuleBean.getRuleContentBean() && !Lang.isEmpty(crawlerRuleBean.getRuleContentBean().getMidExtendFields())) {
				addFilter(crawlerRuleBean.getRuleContentBean().getMidExtendFields(), midFilters);
			}
			crawlScope.setMidFilterList(midFilters);

			filterFactory = new DefaultFilterFactory();
			filterFactory.register(crawlScope.getFilterList());
			uriHelper = new DefaultURIHelper(crawlScope);
			this.htmlParserWrapper = new HtmlParserWrapperImpl(filterFactory, uriHelper);
			writeLog("初始化采集配置完成");
		}

		private void addFilter(List<ExtendFieldsBean> extendFields, List<Filter<String, Map<String, String>>> filters) {
			for (ExtendFieldsBean extendFieldsBean : extendFields) {
				filters.add(new FieldFilter(extendFieldsBean.getFields(), extendFieldsBean.getFilterStart(), extendFieldsBean.getFilterEnd()));
			}
		}

		@Override
		public void run() {
			init();
			test();
		}

		private void test() {
			long startTime = System.currentTimeMillis();
			writeLog("开始提取原始URL连接");
			writeLog("提取到" + crawlScope.getSeeds().size() + "个原始连接");
			for (String url : crawlScope.getSeeds()) {
				writeLog(url + "");
			}
			writeLog("取第一个URL:" + crawlScope.getSeeds().get(0) + ",作为采集目标连接测试");
			writeLog("开始采集连接：" + crawlScope.getSeeds().get(0) + "的原始HTML内容");
			String orginHtml = getTargetUrlHtml(crawlScope.getSeeds().get(0));
			if (StringUtils.isBlank(orginHtml)) {
				writeLog("没有采集到该连接的原始HTML内容,请检查配置参数");
				return;
			}
			// writeLog("采集到该连接的原始HTML内容如下");
			// writeLog(orginHtml+"");
			if (StringUtils.isBlank(crawlerRuleBean.getRuleContentBean().getLinksetStart())) {
				writeLog("没有配置采集连接区域标签参数,测试结束");
				return;
			}
			writeLog("开始提取该HTML内容中的目标连接");
			List<CrawlLinkURI> areaLinkList = htmlParserWrapper.getLinkAreaUrlList(orginHtml, null);
			if (CollectionUtils.isEmpty(areaLinkList)) {
				writeLog("没有采集到该参数区域的目标连接,请检查连接区域配置参数");
				return;
			}
			writeLog("提取到" + areaLinkList.size() + "个目标连接");
			for (CrawlLinkURI url : areaLinkList) {
				writeLog(url.getUrl() + "");
			}
			String contentUrl = areaLinkList.get(0).getUrl();
			writeLog("取第一个URL:" + contentUrl + ",作为采集内容测试");
			contentUrl = repair(contentUrl);
			String contentHtml = "";
			// 如果有中间连接
			if (!Lang.isEmpty(crawlScope.getMidFilterList())) {
				CrawlLinkURI crawlLinkURI = new CrawlLinkURI(contentUrl, "", "0", "");
				writeLog("=========取得中间连接---进入地址：=========" + crawlLinkURI);
				for (Filter<String, Map<String, String>> fieldfilter : crawlScope.getMidFilterList()) {
					String field = null;
					List<CrawlLinkURI> tempLinkList = null;
					contentHtml = getTargetUrlHtml(contentUrl);
					for (Iterator<String> it = fieldfilter.getFetchAreaTagMap().keySet().iterator(); it.hasNext();) {
						field = it.next();
						writeLog("=========取得中间连接=========" + field);
						// 取得、过滤指定属性/标签内容
						tempLinkList = htmlParserWrapper.getCrawlLinkURIByFilterMap(contentHtml, fieldfilter.getFetchAreaTagMap().get(field), fieldfilter.getDeleteAreaTagMap().get(field), crawlLinkURI);
						if (!Lang.isEmpty(tempLinkList)) {
							crawlLinkURI = tempLinkList.get(0);
							crawlLinkURI.setUrl(repair(crawlLinkURI.getUrl()));
							contentUrl = crawlLinkURI.getUrl();
							writeLog("=========取得中间连接地址：=========" + crawlLinkURI);
						}
					}
				}
				writeLog("=========取得中间连接---结果地址：=========" + crawlLinkURI);
			}
			contentHtml = getTargetUrlHtml(contentUrl);
			if (StringUtils.isBlank(contentHtml)) {
				writeLog("没有采集到目标连接原始HTML,请检查配置参数");
				return;
			}

			// writeLog("采集到该连接的原始HTML内容如下");
			// writeLog(contentHtml+"");
			if (StringUtils.isNotBlank(crawlerRuleBean.getRuleContentBean().getDescriptionStart())) {
				writeLog("开始提取内容中的描述内容");
				String brief = htmlParserWrapper.getContentBrief(contentHtml);
				if (StringUtils.isBlank(contentHtml)) {
					writeLog("没有提取到描述信息,请检查描述区域配置参数");
				} else {
					writeLog("提取到描述信息,如下");
					writeLog(brief);
				}
			}
			if (StringUtils.isNotBlank(crawlerRuleBean.getRuleContentBean().getContentStart())) {
				writeLog("开始提取目标内容");
				String content = htmlParserWrapper.getTargetContentHtml(contentHtml);
				if (StringUtils.isBlank(contentHtml)) {
					writeLog("没有提取到目标内容信息,请检查内容区域配置参数");
				} else {
					writeLog("提取到目标内容信息,如下");
					writeLog(content);
				}
			}
			long endTime = System.currentTimeMillis();
			writeLog("本次测试结束,耗时：" + (endTime - startTime) + "毫秒");
		}

		/**
		 * 补全URL
		 * <p>
		 * 方法说明:</>
		 */
		private String repair(String url) {
			if (!url.contains(Constants.HTTP_FILL_KEY)) {
				if (StringUtils.isBlank(crawlScope.getRepairPageUrl())) {
					writeLog("当前URL:" + url + "是相对地址，需要设置页面链接补全URL");
					return "";
				}
				url = crawlScope.getRepairPageUrl() + url;
				writeLog("补全后URL:" + url + "");
			}
			return url;
		}

		/**
		 * 打印日志
		 */
		private void writeLog(String logStr) {
			log.info(logStr);
		}

		/**
		 * 获取指定页面原始内容
		 */
		private String getTargetUrlHtml(String url) {
			if (!StringUtils.contains(url, "http://")) {
				url = "http://www.zhms.cn/" + url;
			}
			return Http.get(url).getContent(crawlerRuleBean.getRuleBaseBean().getPageEncoding());
		}

	}

	private final static String PROPERTIES_CONF = "gather_core.properties";
	private PropertiesProxy conf;

	public void init() {
		log.info("开始加载爬虫配置文件:" + PROPERTIES_CONF);
		conf.joinAndClose(Streams.fileInr(PROPERTIES_CONF));
		CrawlerConfig.threadNum = conf.getInt("threadNum", CrawlerConfig.threadNum);
		CrawlerConfig.taskTimeOut = conf.getInt("taskTimeOut", CrawlerConfig.taskTimeOut);
		CrawlerConfig.resSaveRootPath = conf.get("resSaveRootPath", CrawlerConfig.resSaveRootPath);
		CrawlerConfig.resSavePath = conf.get("resSavePath", CrawlerConfig.resSavePath);
		CrawlerConfig.extractResType = conf.get("extractResType", CrawlerConfig.extractResType);
		CrawlerConfig.extractMediaResType = conf.get("extractMediaResType", CrawlerConfig.extractMediaResType);
		CrawlerConfig.replaceResName = conf.get("replaceName", CrawlerConfig.replaceResName);
		CrawlerConfig.proxyServerList = populateProxyServer(conf.get("proxyServerList"));
		CrawlerConfig.systemRootPath = conf.get("systemRootPath", CrawlerConfig.systemRootPath);
		CrawlerConfig.httpClientMaxConn = conf.getInt("httpClientMaxConn", CrawlerConfig.httpClientMaxConn);
		CrawlerConfig.httpClientMaxRoute = conf.getInt("httpClientMaxRoute", CrawlerConfig.httpClientMaxRoute);
		CrawlerConfig.httpConnTimeout = conf.getInt("httpConnTimeout", CrawlerConfig.httpConnTimeout);
		CrawlerConfig.httpSocketTimeout = conf.getInt("httpSocketTimeout", CrawlerConfig.httpSocketTimeout);
		CrawlerConfig.defaultWords = conf.get("defaultWords", CrawlerConfig.defaultWords);
		CrawlerConfig.defaultCommonReplaceWords = CommonUtils.populateWordsMap(conf.get("defaultCommonReplaceWords"));
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

	public void s(CrawlerRule rule)
	{
		CrawlerController crawlController = new CrawlerController();
		List<Filter<String, ?>> filters = new ArrayList<Filter<String, ?>>();
		filters.add(new LinkAreaFilter(rule.getLinksetStart(),rule.getLinksetEnd()));
		filters.add(new ContentAreaFilter(rule.getContentStart(),rule.getContentEnd()));
		filters.add(new BriefAreaFilter(rule.getDescriptionStart(),rule.getDescriptionEnd()));
		filters.add(new PaginationAreaFilter(rule.getPaginationStart(),rule.getPaginationEnd()));
		filters.add(new CommentIndexFilter(rule.getCommentIndexStart(),rule.getCommentIndexEnd()));
		filters.add(new CommentAreaFilter(rule.getCommentAreaStart(),rule.getCommentAreaEnd()));
		filters.add(new CommentFilter(rule.getCommentStart(),rule.getCommentEnd()));
		filters.add(new CommentLinkFilter(rule.getCommentLinkStart(),rule.getCommentLinkEnd()));
		
		//添加扩展字段过滤器
		if(StringUtils.isNotEmpty(rule.getKeywordsStart())){
			addFilter(rule.getKeywordsStart(),filters);
		}
		
		CrawlScope crawlScope = new CrawlScope();
		//crawlScope.setCrawlerPersistent(crawlerPersistent);
		crawlScope.setEncoding(rule.getPageEncoding());
		crawlScope.setId(rule.getId());
		crawlScope.setFilterList(filters);
		//评论内容列表是否与内容页分离，如果填写了,则为true
		if(StringUtils.isNotEmpty(rule.getCommentIndexStart())){
			crawlScope.setCommentListIsAlone(true);
		}
		crawlScope.setRepairPageUrl(rule.getLinkStart());
		crawlScope.setRepairImageUrl(rule.getLinkEnd());
		//设置休眠时间
		crawlScope.setSleepTime(rule.getPauseTime());
		crawlScope.setPaginationRepairUrl(rule.getPaginationRepairUrl());
		//是否下载图片至本地
		crawlScope.setExtractContentRes(Boolean.valueOf(rule.getTitleStart()));
		//是否去掉内容中连接
		crawlScope.setReplaceHtmlLink(Boolean.valueOf(rule.getTitleEnd()));
		crawlScope.setAllowRepeat(rule.isRepeatCheckType());
		crawlScope.setUseProxy(rule.isUseProxy());
		crawlScope.setProxyAddress(rule.getProxyAddress());
		crawlScope.setProxyPort(rule.getProxyPort());
		crawlScope.setReplaceWords(rule.getReplaceWords());
		crawlScope.addSeeds(rule.getAllPlans());
		crawlController.initialize(crawlScope);
		crawlController.start();
	}
	public void startCrawker(CrawlerRuleBean rule) {
		CrawlerController crawlController = new CrawlerController();
		List<Filter<String, ?>> filters = new ArrayList<Filter<String, ?>>();
		filters.add(new LinkAreaFilter(rule.getRuleContentBean().getLinksetStart(), rule.getRuleContentBean().getLinksetEnd()));
		filters.add(new ContentAreaFilter(rule.getRuleContentBean().getContentStart(), rule.getRuleContentBean().getContentEnd()));
		filters.add(new BriefAreaFilter(rule.getRuleContentBean().getDescriptionStart(), rule.getRuleContentBean().getDescriptionEnd()));
		// filters.add(new
		// PaginationAreaFilter(rule.getRuleContentPageBean().getPaginationStart(),rule.getRuleContentPageBean().getPaginationEnd()));
		// filters.add(new
		// CommentIndexFilter(rule.getRuleCommentBean().getCommentIndexStart(),rule.getRuleCommentBean().getCommentIndexEnd()));
		// filters.add(new
		// CommentAreaFilter(rule.getRuleCommentBean().getCommentAreaStart(),rule.getRuleCommentBean().getCommentAreaEnd()));
		// filters.add(new
		// CommentFilter(rule.getRuleCommentBean().getCommentStart(),rule.getRuleCommentBean().getCommentEnd()));
		// filters.add(new
		// CommentLinkFilter(rule.getRuleCommentBean().getCommentLinkStart(),rule.getRuleCommentBean().getCommentLinkEnd()));

		List<Filter<String, Map<String, String>>> midFilters = new ArrayList<Filter<String, Map<String, String>>>();
		// 添加过度连接过滤器
		if (null != rule.getRuleContentBean() && !Lang.isEmpty(rule.getRuleContentBean().getMidExtendFields())) {
			addFilter(rule.getRuleContentBean().getMidExtendFields(), midFilters);
		}

		List<Filter<String, Map<String, String>>> multeityFilters = new ArrayList<Filter<String, Map<String, String>>>();
		// 添加扩展字段过滤器
		if (null != rule.getRuleFieldsBean() && !Lang.isEmpty(rule.getRuleFieldsBean().getExtendFields())) {
			addFilter(rule.getRuleFieldsBean().getExtendFields(), multeityFilters);
		}
		CrawlScope crawlScope = new CrawlScope();
		// crawlScope.setCrawlerPersistent(this.getCrawlerPersistent());
		crawlScope.setEncoding(rule.getRuleBaseBean().getPageEncoding());
		crawlScope.setId(rule.getRuleId());
		crawlScope.setFilterList(filters);
		crawlScope.setMidFilterList(midFilters);
		crawlScope.setMulteityFilterList(multeityFilters);
		// 评论内容列表是否与内容页分离，如果填写了,则为true
		if (null != rule.getRuleCommentBean() && StringUtils.isNotEmpty(rule.getRuleCommentBean().getCommentIndexStart())) {
			crawlScope.setCommentListIsAlone(true);
		}
		crawlScope.setRepairPageUrl(rule.getRuleBaseBean().getUrlRepairUrl());
		crawlScope.setRepairImageUrl(rule.getRuleBaseBean().getResourceRepairUrl());
		crawlScope.setPaginationRepairUrl(rule.getRuleBaseBean().getPaginationRepairUrl());
		// 设置休眠时间
		crawlScope.setSleepTime(rule.getRuleBaseBean().getPauseTime());
		// 是否下载图片至本地
		crawlScope.setExtractContentRes(Boolean.valueOf(rule.getRuleBaseBean().getSaveResourceFlag()));
		// 是否去掉内容中连接
		crawlScope.setReplaceHtmlLink(Boolean.valueOf(rule.getRuleBaseBean().getReplaceLinkFlag()));
		crawlScope.setAllowRepeat(Boolean.valueOf(rule.getRuleBaseBean().getRepeatCheckFlag()));
		crawlScope.setUseProxy(Boolean.valueOf(rule.getRuleBaseBean().getUseProxyFlag()));
		crawlScope.setGatherOrder(Boolean.valueOf(rule.getRuleBaseBean().getGatherOrderFlag()));

		crawlScope.setProxyAddress(rule.getRuleBaseBean().getProxyAddress());
		crawlScope.setProxyPort(rule.getRuleBaseBean().getProxyPort());
		crawlScope.setReplaceWords(rule.getRuleBaseBean().getReplaceWords());

		// 随机生成日期
		crawlScope.setDateFormat(rule.getRuleBaseBean().getDateFormat());
		crawlScope.setRandomDateFlag(Boolean.valueOf(rule.getRuleBaseBean().getRandomDateFlag()));
		crawlScope.setStartRandomDate(rule.getRuleBaseBean().getStartRandomDate());
		crawlScope.setEndRandomDate(rule.getRuleBaseBean().getEndRandomDate());
		crawlScope.setGatherNum(rule.getRuleBaseBean().getGatherNum());
		crawlScope.addSeeds(rule.getRuleContentBean().getAllPlans());
		crawlController.initialize(crawlScope, this);
		crawlController.start();
	}

	private void addFilter(String jsonStr,List<Filter<String, ?>> filters){
		List<String> arry = Json.fromJsonAsList(String.class, jsonStr);
		String fields= "",filterStart= "",filterEnd = "";
		for(int i = 0;i < arry.size();i++){
			Map<String,String> map = Json.fromJsonAsMap(String.class, arry.get(i));
			if(null != map.get("fields")){
				fields = map.get("fields");
			}
			if(null != map.get("filterStart")){
				filterStart =map.get("filterStart");
			}
            if(null != map.get("filterEnd")){
        	   filterEnd = map.get("filterEnd");
			}
			filters.add(new FieldFilter(fields,filterStart,filterEnd));
		}
	}
	
	private void addFilter(List<ExtendFieldsBean> extendFields, List<Filter<String, Map<String, String>>> filters) {
		for (ExtendFieldsBean extendFieldsBean : extendFields) {
			filters.add(new FieldFilter(extendFieldsBean.getFields(), extendFieldsBean.getFilterStart(), extendFieldsBean.getFilterEnd()));
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
		contentBean.getAcquId();
		articleService.insert(art);
	}
}
