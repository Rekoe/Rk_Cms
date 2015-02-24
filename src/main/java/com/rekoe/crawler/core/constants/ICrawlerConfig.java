package com.rekoe.crawler.core.constants;

import java.io.IOException;

/**
 * 爬虫参数配置加载接口
 * @author javacoo
 * @since 2012-02-29
 */
public interface ICrawlerConfig {
	/**
	 * 加载配置信息
	 */
	void loadCrawlerConfig(String path) throws IOException;
}
