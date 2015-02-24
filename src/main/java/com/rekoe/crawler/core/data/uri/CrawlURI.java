package com.rekoe.crawler.core.data.uri;
/**
 * 爬虫URI对象接口
 * @author javacoo
 * @since 2012-05-14
 */
public interface CrawlURI {
	/**取得父URI对象*/
	CrawlURI getParentURI();
	/**取得当前URL*/
	String getUrl();
	/**取得主机名*/
	String getHost();
	/**取得端口*/
	int getPort();
	/**取得路径类型*/
	String getPathType();
	/**取得RawPath*/
	String getRawPath();
	/**取得名称*/
	String getName();
	/**取得描述*/
	String getDesc();
}
