package org.javacoo.crawler.core.cache;

import org.apache.commons.lang3.StringUtils;

import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * HttpHost对象缓存接口实现类
 * 
 * @author javacoo
 * @since 2012-05-14
 */
public class DefaultHostCache implements HostCache {

	@Override
	public String getHttpHostUrl(CrawlURI crawlURI) {
		String hostName = crawlURI.getHost();
		int port = crawlURI.getPort();
		if (StringUtils.isBlank(hostName)) {
			hostName = crawlURI.getParentURI().getHost();
			port = crawlURI.getParentURI().getPort();
		}
		return crawlURI.getUrl();
	}

}
