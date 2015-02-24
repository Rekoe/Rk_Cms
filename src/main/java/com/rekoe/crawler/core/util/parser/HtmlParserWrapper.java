package com.rekoe.crawler.core.util.parser;

import java.util.List;
import java.util.Map;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;


/**
 * HTML解析包装类接口
 * @author javacoo
 * @since 2012-05-12
 */
public interface HtmlParserWrapper {
	/**
	 * 取得文章列表区域链接集合
	 * @param orginHtml 原始HTML
	 * @param parentCrawlURI 父crawlURI
	 * @return 连接集合
	 */
	List<CrawlLinkURI> getLinkAreaUrlList(String orginHtml,CrawlURI parentCrawlURI);
    /**
     * 取得指定区域的HTML内容
     * @return 指定区域的HTML内容
     */
	String getContentHtml(String orginHtml);
	/**
     * 取得指定区域的文章HTML内容,未过滤
     * @return 指定区域的HTML内容
     */
	String getTargetContentHtml(String orginHtml);
	/**
     * 过滤指定区域的HTML内容
     * @return 过滤后的文章HTML内容
     */
	String filterTargetContentHtml(String orginHtml);
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
	 * 取得CrawlURI集合，如果有标题图片则保存
	 * @param orginHtml 原始HTML
	 * @param savePath 标题图片保存路径
	 * @param parentCrawlURI 父crawlURI
	 * @return CrawlURI集合
	 */
    List<CrawlLinkURI> getCrawlURIList(String orginHtml,String savePath,CrawlURI parentCrawlURI);
	/**
	 * 替换HTML内容中资源链接地址为指定地址，并返回包含了替换后HTML，原始资源链接与替换后资源链接对照的MAP对象
	 * @param orginHtml 原始HTML内容
	 * @param savePath 资源保存路径
	 * @param resCrawlURIList 资源URI对象集合
	 * @param parentCrawlURI 父crawlURI
	 * @return MAP
	 */
    String replaceHtmlResource(String orginHtml,String savePath,List<CrawlResURI> resCrawlURIList,CrawlURI parentCrawlURI);
	/**
	 * 替换内容中的超链接
	 * @param orginHtml 原始HTML内容
	 * @return 替换掉超链接后的HTML
	 */
	String replaceHtmlLink(String orginHtml);
	/**
	 * 取得原始HTML中指定条件的值并放在对应字段
	 * @param orginHtml 原始HTML
	 * @return 字段 值对照MAP
	 */
	Map<String,String> getFieldValues(String orginHtml);
	/**
     * 取得指定HTML内容中的摘要信息
     * @param orginHtml 摘要信息
     * @return 指定区域的HTML内容
     */
	String getContentBrief(String orginHtml);
	/**
     * 取得指定HTML内容中的分页链接列表信息
     * @param orginHtml 原始HTML信息
	 * @param parentCrawlURI 父crawlURI
     * @return 内容分页链接列表
     */
	List<CrawlLinkURI> getPaginationList(String orginHtml,CrawlURI parentCrawlURI);
	/**
     * 取得指定HTML内容中的评论列表入口URL
     * @param orginHtml 原始HTML信息
	 * @param parentCrawlURI 父crawlURI
     * @return Url
     */
	CrawlLinkURI getCommentIndexUrl(String orginHtml,CrawlURI parentCrawlURI);
	/**
     * 取得指定HTML内容中的评论内容列表区域HTML内容
     * @param orginHtml 原始HTML信息
     * @return String 评论内容列表区域HTML内容
     */
	String getCommentListArea(String orginHtml);
	
	/**
     * 取得指定HTML内容中的评论链接列表信息
     * @param orginHtml 原始HTML信息
	 * @param parentCrawlURI 父crawlURI
     * @return 评论链接列表
     */
	List<CrawlLinkURI> getCommentUrlList(String orginHtml,CrawlURI parentCrawlURI);
	/**
	 * 取得评论内容集合
	 * @param orginHtml
	 * @return
	 */
	List<String> getCommentList(String orginHtml);
	/**
     * 过滤评论内容区域的HTML内容
     * @return 过滤的HTML内容
     */
	String filterCommentHtml(String orginHtml);
	/**
     * 取得指定区域,过滤标签的HTML内容
     * @param fetchAreaTagMap 待提取区域标签属性Map
	 * @param deleteAreaTagMap 待提取区域中要删除的标签属性Map
     * @return 指定区域的连接内容
     */
	List<CrawlLinkURI> getCrawlLinkURIByFilterMap(String orginHtml,Map<String,String> fetchAreaTagMap,Map<String,String> deleteAreaTagMap,CrawlURI parentCrawlURI);
}
