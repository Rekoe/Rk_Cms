package com.rekoe.crawler.core.cache;

import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * HttpHost对象缓存接口实现类
 */
public class DefaultHostCache implements HostCache {

	@Override
	public String getHttpHostUrl(CrawlURI crawlURI) {
		//String hostName = crawlURI.getHost();
		//int port = crawlURI.getPort();
		//if (StringUtils.isBlank(hostName)) {
			//hostName = crawlURI.getParentURI().getHost();
			//port = crawlURI.getParentURI().getPort();
		//}
		return crawlURI.getUrl();
	}

}
