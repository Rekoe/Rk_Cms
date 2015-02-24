package com.rekoe.cms.authorize.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.nutz.http.Http;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.bean.CrawlerRuleBean;
import com.rekoe.crawler.bean.ExtendFieldsBean;
import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.filter.BriefAreaFilter;
import com.rekoe.crawler.core.filter.ContentAreaFilter;
import com.rekoe.crawler.core.filter.FieldFilter;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.filter.LinkAreaFilter;
import com.rekoe.crawler.core.filter.factory.DefaultFilterFactory;
import com.rekoe.crawler.core.filter.factory.FilterFactory;
import com.rekoe.crawler.core.util.DefaultURIHelper;
import com.rekoe.crawler.core.util.URIHelper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapper;
import com.rekoe.crawler.core.util.parser.HtmlParserWrapperImpl;

public class RunTestRule implements Runnable {
	private final static Log log = Logs.get();
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

	public RunTestRule(CrawlerRuleBean crawlerRuleBean) {
		this.crawlerRuleBean = crawlerRuleBean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		List<Filter> filters = new ArrayList<Filter>();
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

		List<Filter> midFilters = new ArrayList<Filter>();
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

	@SuppressWarnings("rawtypes")
	private void addFilter(List<ExtendFieldsBean> extendFields, List<Filter> filters) {
		for (ExtendFieldsBean extendFieldsBean : extendFields) {
			filters.add(new FieldFilter(extendFieldsBean.getFields(), extendFieldsBean.getFilterStart(), extendFieldsBean.getFilterEnd()));
		}
	}

	@Override
	public void run() {
		init();
		test();
	}

	@SuppressWarnings("unchecked")
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
			writeLog(url.getUrl() + "|"+url.getTitle());
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
