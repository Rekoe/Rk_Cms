package org.javacoo.crawler.core.cache;

import org.apache.http.HttpHost;
import com.rekoe.crawler.core.data.uri.CrawlURI;
/**
 * HttpHost对象缓存接口
 * @author javacoo
 * @since 2012-05-14
 */
public interface HostCache {
	 /**
	 * 根据主机名取得缓存中的HttpHost对象
	 * @param crawlURI 爬虫URI对象接口
	 * @return HttpHost对象
	 */
	HttpHost getHttpHost(CrawlURI crawlURI);
	/**
	 * 清理缓存
	 */
	void clear();

}
