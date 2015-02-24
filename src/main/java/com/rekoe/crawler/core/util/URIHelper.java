package com.rekoe.crawler.core.util;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * URI帮助类接口
 * 
 * @author javacoo
 * @since 2012-05-15
 */
public interface URIHelper {
	/**
	 * 组装 CrawlURI对象
	 * 
	 * @param crawlURI
	 *            父URI对象
	 * @param urlStr
	 *            url
	 * @param title
	 *            标题
	 * @param type
	 *            类型
	 * @return CrawlURI
	 */
	CrawlLinkURI populateCrawlURI(CrawlURI crawlURI, String urlStr, String title);

	/**
	 * 组装 ResURI对象
	 * 
	 * @param crawlURI
	 *            父URI对象
	 * @param originUrl
	 *            源URL
	 * @param newUrl
	 *            新URL
	 * @param resType
	 *            资源类型
	 * @return ResURI
	 */
	CrawlResURI populateResURI(CrawlURI crawlURI, String originUrl, String newUrl, String resType);

}
