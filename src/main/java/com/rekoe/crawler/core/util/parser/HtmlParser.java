package com.rekoe.crawler.core.util.parser;

import java.util.List;
import java.util.Map;

import org.htmlparser.util.ParserException;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * HTML解析工具类接口
 * @author javacoo
 * @since 2011-10-31
 */
public interface HtmlParser {
	/**
	 * 取得指定区域的连接集合
	 * @param orginHtml 原始HTML
	 * @param fetchAreaTagMap 待提取区域标签属性Map
	 * @param deleteAreaTagMap 待提取区域中要删除的标签属性Map
	 * @param parentCrawlURI 父crawlURI
	 * @return 连接集合
	 */
	List<CrawlLinkURI> getUrlList(String orginHtml,Map<String,String> fetchAreaTagMap,Map<String,String> deleteAreaTagMap,CrawlURI parentCrawlURI);
    /**
     * 取得指定区域的HTML内容
     * @param fetchAreaTagMap 待提取区域标签属性Map
	 * @param deleteAreaTagMap 待提取区域中要删除的标签属性Map
     * @return 指定区域的HTML内容
     */
	String getHtml(String orginHtml,Map<String,String> fetchAreaTagMap,Map<String,String> deleteAreaTagMap);
	/**
     * 取得指定区域中指定属性标签内的HTML内容
     * @param fetchAreaTagMap 待提取区域标签属性Map
     * @return 指定区域的HTML内容
     */
	String fetchAreaHtml(String orginHtml,Map<String,String> fetchAreaTagMap);
	/**
     * 过滤指定属性标签内的HTML内容
	 * @param deleteAreaTagMap 待提取区域中要删除的标签属性Map
     * @return 过滤后的HTML内容
     */
	String deleteAreaHtml(String orginHtml,Map<String,String> deleteAreaTagMap);
	/**
	 * 取得无格式的HTML内容
	 * @param orginHtml 原始HTML内容
	 * @return 无格式的HTML内容
	 */
	String getPlainText(String orginHtml);
	/**
	 * 取得HTML内容中的资源
	 * @param orginHtml 原始HTML内容
	 * @param resCrawlURIList 资源URI对象集合
	 * @param parentCrawlURI 父crawlURI
	 * @return String
	 */
	String getHtmlResource(String orginHtml,List<CrawlResURI> resCrawlURIList,CrawlURI parentCrawlURI);
	/**
	 * 取得CrawlURI对象集合，如果有标题图片则保存
	 * @param orginHtml 原始HTML
	 * @param savePath 标题图片保存路径
     * @param fetchAreaTagMap 待提取区域标签属性Map
     * @param deleteAreaTagMap 待提取区域中要删除的标签属性Map
	 * @param parentCrawlURI 父crawlURI
	 * @return CrawlURI集合
	 */
    List<CrawlLinkURI> getCrawlURIList(String orginHtml,String savePath,Map<String,String> fetchAreaTagMap,Map<String,String> deleteAreaTagMap,CrawlURI parentCrawlURI);
	/**
	 * 替换HTML内容中资源链接地址为指定地址，并返回包含了替换后HTML，原始资源链接与替换后资源链接对照的MAP对象
	 * @param orginHtml 原始HTML内容
	 * @param savePath 资源保存路径
	 * @param resCrawlURIList 资源URI对象集合
	 * @param parentCrawlURI 父crawlURI
	 * @return String
	 */
    String replaceHtmlResource(String orginHtml,String savePath,List<CrawlResURI> resCrawlURIList,CrawlURI parentCrawlURI);
	/**
	 * 替换内容中的超链接
	 * @param orginHtml 原始HTML内容
	 * @return 替换掉超链接后的HTML
	 */
	String replaceHtmlLink(String orginHtml);
	/**
	 * 取得指定属性/标签无格式内容集合
	 * @param orginHtml 原始HTML
	 * @param tagMap 待提取区域标签属性Map
	 * @return 无格式内容集合
	 * @throws ParserException
	 */
	List<String> getPlainTextList(String orginHtml,Map<String, String> fetchAreaTagMap);
	/**
	 * 取得指定区域内的内容并放如集合中
	 * @param orginHtml 原始HTML内容
     * @param fetchAreaTagMap 待提取区域标签属性Map
	 * @return 指定区域内的内容集合
	 */
	List<String> getHtmlList(String orginHtml,Map<String,String> fetchAreaTagMap);
}
