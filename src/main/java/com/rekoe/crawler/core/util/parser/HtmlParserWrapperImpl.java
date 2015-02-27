package com.rekoe.crawler.core.util.parser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.filter.factory.FilterFactory;
import com.rekoe.crawler.core.util.URIHelper;

/**
 * HTML解析包装类接口实现类
 */
@SuppressWarnings("unchecked")
public class HtmlParserWrapperImpl implements HtmlParserWrapper {
	private final static Log log = Logs.get();
	/** 过滤器工厂 */
	@SuppressWarnings("rawtypes")
	private FilterFactory filterFactory;
	/** HTML解析工具类 */
	private HtmlParser htmlParser;

	/**
	 * HTML解析包装类构造方法
	 * 
	 * @param filterFactory
	 *            过滤器工
	 */
	@SuppressWarnings("rawtypes")
	public HtmlParserWrapperImpl(FilterFactory filterFactory, URIHelper uriHelper) {
		this.filterFactory = filterFactory;
		this.htmlParser = new HtmlParserImpl(uriHelper);
	}

	/**
	 * 取得文章列表区域链接集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 标题集合
	 */
	public List<CrawlLinkURI> getLinkAreaUrlList(String orginHtml, CrawlURI parentCrawlURI) {
		Filter<String, String> linkAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_LINK_AREA);
		List<CrawlLinkURI> linkAreaList = new ArrayList<CrawlLinkURI>();
		if (!Lang.isEmpty(linkAreafilter.getFetchAreaTagMap())) {
			linkAreaList = htmlParser.getUrlList(orginHtml, linkAreafilter.getFetchAreaTagMap(), linkAreafilter.getDeleteAreaTagMap(), parentCrawlURI);
		}
		return linkAreaList;
	}

	/**
	 * 取得原始HTML中指定条件的值并放在对应字段
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 字段 值对照MAP
	 */
	public Map<String, String> getFieldValues(String orginHtml) {
		Map<String, String> resultMap = new HashMap<String, String>();
		List<Filter<String, Map<String, String>>> fieldfilterList = filterFactory.getFilterListByName(Constants.FILTER_NAME_FIELD);
		if (!Lang.isEmpty(fieldfilterList)) {
			for (Filter<String, Map<String, String>> fieldfilter : fieldfilterList) {
				String field = null;
				String tempHtml = null;
				StringBuilder tempValue = null;
				List<String> tempList = null;
				for (Iterator<String> it = fieldfilter.getFetchAreaTagMap().keySet().iterator(); it.hasNext();) {
					field = it.next();
					// 取得、过滤指定属性/标签内容
					tempHtml = htmlParser.getHtml(orginHtml, fieldfilter.getFetchAreaTagMap().get(field), fieldfilter.getDeleteAreaTagMap().get(field));
					tempList = htmlParser.getPlainTextList(tempHtml, fieldfilter.getFetchAreaTagMap().get(field));
					if (!CollectionUtils.isEmpty(tempList)) {
						tempValue = new StringBuilder();
						for (String str : tempList) {
							tempValue.append(str).append(",");
						}
						resultMap.put(field, tempValue.substring(0, tempValue.length() - 1).toString());
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 指定区域的HTML内容
	 * @throws ParserException
	 */
	public String getContentHtml(String orginHtml) {
		Filter<String, String> contentAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_CONTENT_AREA);
		return htmlParser.getHtml(orginHtml, contentAreafilter.getFetchAreaTagMap(), contentAreafilter.getDeleteAreaTagMap());
	}

	/**
	 * 取得指定区域的文章HTML内容,未过滤
	 * 
	 * @return 指定区域的HTML内容
	 */
	public String getTargetContentHtml(String orginHtml) {
		Filter<String, String> contentAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_CONTENT_AREA);
		return htmlParser.fetchAreaHtml(orginHtml, contentAreafilter.getFetchAreaTagMap());
	}

	/**
	 * 过滤指定区域的文章HTML内容
	 * 
	 * @return 过滤后的HTML内容
	 */
	public String filterTargetContentHtml(String orginHtml) {
		Filter<String, String> contentAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_CONTENT_AREA);
		return htmlParser.deleteAreaHtml(orginHtml, contentAreafilter.getDeleteAreaTagMap());
	}

	/**
	 * 取得无格式的HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @return 无格式的HTML内容
	 */
	public String getPlainText(String orginHtml) {
		return htmlParser.getPlainText(orginHtml);
	}

	/**
	 * 取得HTML内容中的资源
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return String
	 */
	public String getHtmlResource(String orginHtml, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) {
		return htmlParser.getHtmlResource(orginHtml, resCrawlURIList, parentCrawlURI);

	}

	/**
	 * 取得指定HTML内容中的摘要信息
	 * 
	 * @param orginHtml
	 *            原始HTML信息
	 * @return 指定区域的HTML内容
	 */
	public String getContentBrief(String orginHtml) {
		log.info("========取得指定HTML内容中的摘要信息=========");
		String returnHtml = "";
		if (StringUtils.isNotBlank(orginHtml)) {
			Filter<String, String> briefAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_BRIEF_AREA);
			if (Lang.isEmpty(briefAreafilter.getFetchAreaTagMap())) {// 如果摘要采集参数为空,则提取内容中指定字数字符作为摘要信息
				returnHtml = getTargetContentHtml(orginHtml);
				returnHtml = filterTargetContentHtml(returnHtml);
				returnHtml = getPlainText(returnHtml);
			} else {
				returnHtml = htmlParser.getHtml(orginHtml, briefAreafilter.getFetchAreaTagMap(), briefAreafilter.getDeleteAreaTagMap());
				returnHtml = getPlainText(returnHtml);
			}
			returnHtml = StringUtils.left(returnHtml, Constants.BRIEF_NUM);
		}

		return htmlInit(returnHtml);
	}

	private String htmlInit(String htmlStr) {
		NodeFilter scriptFilter = new NodeClassFilter(ScriptTag.class);
		NodeFilter styleFilter = new NodeClassFilter(StyleTag.class);
		NodeFilter spanFilter = new NodeClassFilter(Span.class);
		NodeFilter[] filter = { scriptFilter, styleFilter, spanFilter };
		OrFilter orFilter = new OrFilter(filter);
		try {
			htmlStr = ParserUtils.trimTags(htmlStr, orFilter, true, true);
		} catch (ParserException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return htmlStr;
	}

	/**
	 * 取得指定HTML内容中的分页链接列表信息
	 * 
	 * @param orginHtml
	 *            原始HTML信息
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 分页链接列表
	 */
	public List<CrawlLinkURI> getPaginationList(String orginHtml, CrawlURI parentCrawlURI) {
		log.info("========取得指定HTML内容中的分页链接列表信息=========");
		Filter<String, String> paginationAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_PAGINATION_AREA);
		List<CrawlLinkURI> paginationList = new ArrayList<CrawlLinkURI>();
		if (!Lang.isEmpty(paginationAreafilter.getFetchAreaTagMap())) {
			paginationList = htmlParser.getUrlList(orginHtml, paginationAreafilter.getFetchAreaTagMap(), paginationAreafilter.getDeleteAreaTagMap(), parentCrawlURI);
		}
		return paginationList;
	}

	/**
	 * 取得指定HTML内容中的评论列表入口URL
	 * 
	 * @param orginHtml
	 *            原始HTML信息
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return Url
	 */
	public CrawlLinkURI getCommentIndexUrl(String orginHtml, CrawlURI parentCrawlURI) {
		Filter<String, String> commentIndexAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_COMMENT_INDEX_AREA);
		if (!Lang.isEmpty(commentIndexAreafilter.getFetchAreaTagMap())) {
			List<CrawlLinkURI> urlList = htmlParser.getUrlList(orginHtml, commentIndexAreafilter.getFetchAreaTagMap(), commentIndexAreafilter.getDeleteAreaTagMap(), parentCrawlURI);
			if (!CollectionUtils.isEmpty(urlList)) {
				return urlList.get(0);
			}
		}
		return null;
	}

	/**
	 * 取得指定HTML内容中的评论内容列表区域HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML信息
	 * @return String 评论内容列表区域HTML内容
	 */
	public String getCommentListArea(String orginHtml) {
		Filter<String, String> commentListAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_COMMENT_LIST_AREA);
		return htmlParser.getHtml(orginHtml, commentListAreafilter.getFetchAreaTagMap(), commentListAreafilter.getDeleteAreaTagMap());
	}

	/**
	 * 取得指定HTML内容中的评论链接列表信息
	 * 
	 * @param orginHtml
	 *            原始HTML信息
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 评论链接列表
	 */
	public List<CrawlLinkURI> getCommentUrlList(String orginHtml, CrawlURI parentCrawlURI) {
		log.info("========取得指定HTML内容中的评论链接列表信息=========");
		Filter<String, String> commentLinkAreafilter = filterFactory.getFilterByName(Constants.FILTER_NAME_COMMENT_LINK_AREA);
		List<CrawlLinkURI> commentLinkList = new ArrayList<CrawlLinkURI>();
		if (!Lang.isEmpty(commentLinkAreafilter.getFetchAreaTagMap())) {
			commentLinkList = htmlParser.getUrlList(orginHtml, commentLinkAreafilter.getFetchAreaTagMap(), commentLinkAreafilter.getDeleteAreaTagMap(), parentCrawlURI);
		}
		return commentLinkList;
	}

	/**
	 * 取得评论内容集合
	 * 
	 * @param orginHtml
	 * @return
	 */
	public List<String> getCommentList(String orginHtml) {
		Filter<String, String> commentfilter = filterFactory.getFilterByName(Constants.FILTER_NAME_COMMENT_AREA);
		return htmlParser.getHtmlList(orginHtml, commentfilter.getFetchAreaTagMap());
	}

	/**
	 * 过滤评论内容区域的HTML内容
	 * 
	 * @return 过滤的HTML内容
	 */
	public String filterCommentHtml(String orginHtml) {
		log.info("========过滤评论内容区域的HTML内容=========");
		Filter<String, String> commentfilter = filterFactory.getFilterByName(Constants.FILTER_NAME_COMMENT_AREA);
		return htmlParser.deleteAreaHtml(orginHtml, commentfilter.getDeleteAreaTagMap());

	}

	/**
	 * 替换HTML内容中资源链接地址为指定地址，并返回包含了替换后HTML，原始资源链接与替换后资源链接对照的MAP对象
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @param savePath
	 *            资源保存路径
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return string
	 */
	public String replaceHtmlResource(String orginHtml, String savePath, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) {
		return htmlParser.replaceHtmlResource(orginHtml, savePath, resCrawlURIList, parentCrawlURI);
	}

	/**
	 * 替换内容中的超链接
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @return 去掉超链接后的HTML
	 */
	public String replaceHtmlLink(String orginHtml) {
		return htmlParser.replaceHtmlLink(orginHtml);
	}

	/**
	 * 取得连CrawlURI集合，如果有标题图片则放到资源MAP中
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            标题图片保存路径
	 * @return CrawlURI集合
	 */
	public List<CrawlLinkURI> getCrawlURIList(String orginHtml, String savePath, CrawlURI parentCrawlURI) {
		Filter<String, String> linkfilter = filterFactory.getFilterByName(Constants.FILTER_NAME_LINK_AREA);
		return htmlParser.getCrawlURIList(orginHtml, savePath, linkfilter.getFetchAreaTagMap(), linkfilter.getDeleteAreaTagMap(), parentCrawlURI);
	}

	/**
	 * 取得指定区域,过滤标签的HTML内容
	 * 
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 指定区域的连接内容
	 */
	public List<CrawlLinkURI> getCrawlLinkURIByFilterMap(String orginHtml, Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap, CrawlURI parentCrawlURI) {
		List<CrawlLinkURI> commentLinkList = new ArrayList<CrawlLinkURI>();
		if (!Lang.isEmpty(fetchAreaTagMap)) {
			commentLinkList = htmlParser.getUrlList(orginHtml, fetchAreaTagMap, deleteAreaTagMap, parentCrawlURI);
		}
		return commentLinkList;
	}
}
