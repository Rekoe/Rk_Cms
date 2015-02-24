package com.rekoe.crawler.core.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * URI帮助类接口实现类
 * 
 * @author javacoo
 * @since 2012-05-15
 */
public class DefaultURIHelper implements URIHelper {
	/** 爬虫参数 */
	private CrawlScope crawlScope;

	public DefaultURIHelper(CrawlScope crawlScope) {
		super();
		this.crawlScope = crawlScope;
	}

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
	public CrawlLinkURI populateCrawlURI(CrawlURI parentCrawlURI, String urlStr, String title) {
		CrawlLinkURI crawlURI = new CrawlLinkURI();
		if (StringUtils.isNotBlank(urlStr)) {
			try {
				String pathType = Constants.PATH_TYPE_0;
				// 相对当前路径
				if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://") && !urlStr.startsWith("/")) {
					urlStr = "/" + urlStr;
					pathType = Constants.PATH_TYPE_2;
				} else if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://") && urlStr.startsWith("/")) {
					pathType = Constants.PATH_TYPE_1;
				}
				urlStr = StringUtils.trim(urlStr);
				URI uri = new URI(urlStr);
				crawlURI.setParentURI(parentCrawlURI);
				crawlURI.setUrl(urlStr);
				crawlURI.setTitle(StringUtils.trim(title));
				crawlURI.setPathType(pathType);
				crawlURI.setHost(uri.getHost());
				crawlURI.setPort(uri.getPort());
				crawlURI.setRawPath(uri.getRawPath());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return crawlURI;
	}

	/**
	 * 组装 ResURI对象
	 * 
	 * @param parentCrawlURI
	 *            父URI对象
	 * @param originUrl
	 *            源URL
	 * @param newUrl
	 *            新URL
	 * @param resType
	 *            资源类型
	 * @return ResURI
	 */
	public CrawlResURI populateResURI(CrawlURI parentCrawlURI, String originUrl, String newUrl, String resType) {
		CrawlResURI resURI = new CrawlResURI();
		if (StringUtils.isNotBlank(originUrl)) {
			try {
				String pathType = Constants.PATH_TYPE_0;
				String currUrl = originUrl;
				// 相对当前路径
				if (!originUrl.startsWith("http://") && !originUrl.startsWith("https://") && !originUrl.startsWith("/")) {
					originUrl = "/" + originUrl;
					if (null != parentCrawlURI && StringUtils.isNotBlank(parentCrawlURI.getUrl())) {
						String parentUrl = parentCrawlURI.getUrl();
						currUrl = parentUrl.substring(0, parentUrl.lastIndexOf("/")) + originUrl;
					}
					pathType = Constants.PATH_TYPE_2;
				} else if (!originUrl.startsWith("http://") && !originUrl.startsWith("https://") && originUrl.startsWith("/")) {
					pathType = Constants.PATH_TYPE_1;
					if (null != parentCrawlURI && StringUtils.isNotBlank(parentCrawlURI.getUrl())) {
						StringBuilder surUrl = new StringBuilder();
						currUrl = surUrl.append(Constants.HTTP_FILL_KEY).append(parentCrawlURI.getHost()).append(":").append(parentCrawlURI.getPort()).append(originUrl).toString();
					}
				}

				if (crawlScope.isExtractContentRes()) {
					currUrl = newUrl;
				}
				originUrl = StringUtils.trim(originUrl);
				URI uri = new URI(originUrl);
				resURI.setParentURI(parentCrawlURI);
				resURI.setOriginResUrl(originUrl);
				resURI.setNewResUrl(newUrl);
				resURI.setUrl(currUrl);
				resURI.setPathType(pathType);
				resURI.setHost(uri.getHost());
				resURI.setPort(uri.getPort());
				resURI.setRawPath(uri.getRawPath());
				resURI.setResType(resType);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return resURI;
	}

}
