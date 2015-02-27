package com.rekoe.crawler.core;

import java.util.List;

import org.nutz.lang.Lang;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.cache.DefaultHostCache;
import com.rekoe.crawler.core.cache.HostCache;
import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.filter.factory.DefaultFilterFactory;
import com.rekoe.crawler.core.filter.factory.FilterFactory;
import com.rekoe.crawler.core.frontier.DefaultFrontier;
import com.rekoe.crawler.core.frontier.Frontier;
import com.rekoe.crawler.core.processor.ProcessorChain;
import com.rekoe.crawler.core.processor.ProcessorChainList;
import com.rekoe.crawler.core.thread.ProcessorManager;
import com.rekoe.crawler.core.util.CharsetHandler;
import com.rekoe.crawler.core.util.DefaultURIHelper;
import com.rekoe.crawler.core.util.URIHelper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapperImpl;
import com.rekoe.service.CrawlerRuleService;

/**
 * 爬虫控制器 <li>CrawlController 类是整个爬虫的总控制者 , 控制整个抓取工作的起点 ， 决定整个抓取任务的开始和结束。</li>
 * <li>
 * 爬虫工作流程如下：<br>
 * 一：根据initialize传递进来的CrawlScope对象初始化爬虫各个模块，分别是爬虫的配置参数，字符集帮助类，初始化HttpCilent对象，
 * HTML解析器帮助类，边界控制器，线程控制器，处理器链，主机缓存<br>
 * 1：爬虫配置参数（CrawlScope）：存储当前爬虫的配置信息，如采集页面编码，采集过滤器列表，采集种子列表，爬虫持久对象实现类<br>
 * 2：字符集帮助类（CharsetHandler）：字符集帮助类<br>
 * 3：初始化HttpCilent对象：初始化HttpCilent对象<br>
 * 4：HTML解析器包装类（HtmlParserWrapper）：根据爬虫配置参数（CrawlScope）中采集过滤器列表初始化HTML解析器<br>
 * 5：边界控制器（Frontier）：主要是加载爬行种子链接并根据加载的种子链接初始化任务队列，以备线程控制器（ProcessorManager）
 * 开启的任务执行线程（ProcessorThread）使用<br>
 * 6：线程控制器（ProcessorManager）：主要是控制任务执行线程数量，开启指定数目的任务执行线程执行任务<br>
 * 7：处理器链（ProcessorChainList）：默认构建了5中处理链，依次是，预取链，提取链，抽取链，写链，提交链，在任务处理线程中将使用<br>
 * 8：过滤器工厂（FilterFactory）：主要是提供过滤器的注册和查询 9：主机缓存（FilterFactory）：缓存主机
 * 二：调用爬虫控制器start方法启动爬虫。</li>
 */
public class CrawlerController {
	private static final org.nutz.log.Log log = Logs.get();
	/** 字符集帮助类 */
	private transient CharsetHandler handler;
	/** HTML解析器包装类类 */
	private transient HtmlParserWrapper htmlParserWrapper;
	/** 爬虫边界控制器 */
	private transient Frontier frontier;
	/** 爬虫线程控制器 */
	private transient ProcessorManager processorManager;
	/** 爬虫配置参数 */
	private transient CrawlScope crawlScope;
	/** 处理器链 */
	private transient ProcessorChainList processorChainList;
	/** 爬虫状态：初始状态,准备就绪,运行中,暂停 */
	private transient String state = Constants.CRAWL_STATE_ORIGINAL;
	/** 过滤器工厂 */
	@SuppressWarnings("rawtypes")
	private transient FilterFactory filterFactory;
	/** 主机缓存 */
	private transient HostCache hostCache;
	/** URIHelper */
	private transient URIHelper uriHelper;

	private transient CrawlerRuleService crawlerRuleService;

	/**
	 * 初始化
	 */
	public void initialize(CrawlScope crawlScope, CrawlerRuleService crawlerRuleService) {
		this.crawlerRuleService = crawlerRuleService;
		setupCrawlModules(crawlScope);
	}

	public void initialize(CrawlScope crawlScope) {
		setupCrawlModules(crawlScope);
	}

	/**
	 * 初始化爬虫各个模块
	 * 
	 * @param crawlScope
	 *            配置参数
	 */
	@SuppressWarnings("unchecked")
	private void setupCrawlModules(CrawlScope crawlScope) {
		log.info("=========开始初始化爬虫各个模块=========");
		log.info("=====================加载爬虫配置参数=========");
		this.crawlScope = crawlScope;
		log.info("=====================初始化字符集帮助类=========");
		this.handler = new CharsetHandler(crawlScope.getEncoding());
		log.info("=====================初始化过滤器工厂,并注册过滤器=========");
		filterFactory = new DefaultFilterFactory();
		filterFactory.register(crawlScope.getFilterList());
		filterFactory.registerMulteity(crawlScope.getMulteityFilterList());
		log.info("=====================初始化主机缓存=========");
		hostCache = new DefaultHostCache();
		log.info("=====================初始化uriHelper=========");
		uriHelper = new DefaultURIHelper(crawlScope);
		log.info("=====================初始化HTML解析器帮助类=========");
		this.htmlParserWrapper = new HtmlParserWrapperImpl(filterFactory, uriHelper);
		log.info("=====================初始化爬虫边界控制器=========");
		if (null == frontier) {
			frontier = new DefaultFrontier();
			frontier.initialize(this);
		}
		log.info("=====================初始化爬虫线程控制器=========");
		this.processorManager = new ProcessorManager(this,crawlScope.getArticleCategoryId());
		log.info("=====================初始化任务处理器链=========");
		if (null == processorChainList) {
			processorChainList = new ProcessorChainList();
		}
		log.info("=====================初始化爬虫状态=========");
		this.state = Constants.CRAWL_STATE_READY;
	}

	/**
	 * 取得爬虫线程控制器
	 * 
	 * @return 爬虫线程控制器
	 */
	public ProcessorManager getProcessorManager() {
		return processorManager;
	}

	/**
	 * 取得爬虫边界控制器对象
	 * 
	 * @return 边界控制器对象
	 */
	public Frontier getFrontier() {
		return frontier;
	}

	/**
	 * 取得爬虫配置参数对象
	 * 
	 * @return 配置参数对象
	 */
	public CrawlScope getCrawlScope() {
		return crawlScope;
	}

	/**
	 * 取得爬虫字符集对象
	 * 
	 * @return 爬虫字符集对象
	 */
	public CharsetHandler getHandler() {
		return handler;
	}

	/**
	 * 取得爬虫HTML解析包装类
	 * 
	 * @return 爬虫HTML解析包装类
	 */
	public HtmlParserWrapper getHtmlParserWrapper() {
		return htmlParserWrapper;
	}

	/**
	 * 取得爬虫第一个处理链对象
	 * 
	 * @return ProcessorChain 处理链对象
	 */
	public ProcessorChain getFirstProcessorChain() {
		return processorChainList.getFirstChain();
	}

	/**
	 * 取得爬虫主机缓存
	 * 
	 * @return 主机缓存
	 */
	public HostCache getHostCache() {
		return hostCache;
	}

	/**
	 * 取得URIHelper对象
	 * 
	 * @return URIHelper对象
	 */
	public URIHelper getUriHelper() {
		return uriHelper;
	}

	/**
	 * 取得爬虫当前状态
	 * 
	 * @return String 爬虫当前状态
	 */
	public String getState() {
		return state;
	}

	/**
	 * 爬虫正常停止
	 */
	public void shutdown() {
		log.info("=====================爬虫正常停止=========");
		this.destory();
	}

	/**
	 * 爬虫非正常停止
	 */
	public void shutdownNow() {
		log.info("=====================爬虫非正常停止=========");
		this.processorManager.getThreadPoolService().shutdownNow();
		this.state = Constants.CRAWL_STATE_ORIGINAL;
	}

	/**
	 * 爬虫启动
	 */
	public void start() {
		log.info("=====================爬虫启动=========");
		new Thread(processorManager).start();
		this.state = Constants.CRAWL_STATE_RUNNING;
	}

	/**
	 * 爬虫暂停
	 */
	public void pause() {
		log.info("=====================爬虫暂停=========");
		this.state = Constants.CRAWL_STATE_PAUSE;
	}

	/**
	 * 爬虫继续
	 */
	public void resume() {
		log.info("=====================爬虫继续=========");
		this.state = Constants.CRAWL_STATE_RUNNING;
	}

	/**
	 * 爬虫
	 */
	private void destory() {
		this.crawlScope.finished();
		this.handler = null;
		this.crawlScope = null;
		this.frontier.destory();
		this.frontier = null;
		this.processorChainList = null;
		this.htmlParserWrapper = null;
		this.processorManager = null;
		this.filterFactory.clear();
		this.filterFactory = null;
		this.hostCache = null;
		this.state = Constants.CRAWL_STATE_ORIGINAL;
	}

	public void addArticle(Task task) {
		if (!Lang.isEmpty(this.crawlerRuleService)) {
			crawlerRuleService.addArticle(task);
		}
	}

	public void addLinkList(List<CrawlLinkURI> urlList) {
		crawlerRuleService.addTempList(urlList);
	}
	
	public void deleteTemp() {
		crawlerRuleService.deleteTemp();
	}
}
