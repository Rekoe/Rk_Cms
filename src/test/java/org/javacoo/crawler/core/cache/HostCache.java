package org.javacoo.crawler.core.cache;

import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * HttpHost对象缓存接口
 */
public interface HostCache {

	String getHttpHostUrl(CrawlURI crawlURI);
}
