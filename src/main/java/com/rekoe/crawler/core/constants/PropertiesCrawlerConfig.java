package com.rekoe.crawler.core.constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Streams;
import org.nutz.log.Logs;

import com.rekoe.utils.CommonUtils;

/**
 * 爬虫参数配置-从配置文件中加载
 * 
 * @author javacoo
 * @since 2012-02-29
 */
public class PropertiesCrawlerConfig {
	private final static org.nutz.log.Log log = Logs.get();
	/** 配置文件路径 */
	private String configFilePath;

	public void loadCrawlerConfig(String path) throws IOException {
		this.configFilePath = path;
		log.info("开始加载爬虫配置文件:" + this.configFilePath);
		Properties properties = new Properties();
		properties.load(Streams.fileIn(this.configFilePath));
		if (!isBlank(properties.getProperty("threadNum"))) {
			CrawlerConfig.threadNum = Integer.parseInt(properties.getProperty("threadNum"));
			log.info("已加载配置：开启线程数为:" + CrawlerConfig.threadNum);
		} else {
			log.info("未配置：开启线程数，采用系统默认:" + CrawlerConfig.threadNum);
		}
		if (!isBlank(properties.getProperty("taskTimeOut"))) {
			CrawlerConfig.taskTimeOut = Integer.parseInt(properties.getProperty("taskTimeOut"));
			log.info("已加载配置：任务的超时时间:" + CrawlerConfig.taskTimeOut);
		} else {
			log.info("未配置：任务的超时时间，采用系统默认:" + CrawlerConfig.taskTimeOut);
		}
		if (!isBlank(properties.getProperty("resSaveRootPath"))) {
			CrawlerConfig.resSaveRootPath = properties.getProperty("resSaveRootPath");
			log.info("已加载配置：资源保存根路径:" + CrawlerConfig.resSaveRootPath);
		} else {
			log.info("未配置：资源保存根路径，采用系统默认:" + CrawlerConfig.resSaveRootPath);
		}
		if (!isBlank(properties.getProperty("resSavePath"))) {
			CrawlerConfig.resSavePath = properties.getProperty("resSavePath");
			log.info("已加载配置：资源保存相对路径:" + CrawlerConfig.resSavePath);
		} else {
			log.info("未配置：资源保存相对路径，采用系统默认:" + CrawlerConfig.resSavePath);
		}

		if (!isBlank(properties.getProperty("extractResType"))) {
			CrawlerConfig.extractResType = properties.getProperty("extractResType");
			log.info("已加载配置：允许采集进来的外部资源类型:" + CrawlerConfig.extractResType);
		} else {
			log.info("未配置：允许采集进来的外部资源类型，采用系统默认:" + CrawlerConfig.extractResType);
		}

		if (!isBlank(properties.getProperty("extractMediaResType"))) {
			CrawlerConfig.extractMediaResType = properties.getProperty("extractMediaResType");
			log.info("已加载配置：允许采集进来的外部媒体资源类型:" + CrawlerConfig.extractMediaResType);
		} else {
			log.info("未配置：允许采集进来的外部媒体资源类型，采用系统默认:" + CrawlerConfig.extractMediaResType);
		}

		if (!isBlank(properties.getProperty("replaceName"))) {
			CrawlerConfig.replaceResName = properties.getProperty("replaceName");
			log.info("已加载配置：是否重命名:" + CrawlerConfig.replaceResName);
		} else {
			log.info("未配置：是否重命名，采用系统默认:" + CrawlerConfig.replaceResName);
		}

		if (!isBlank(properties.getProperty("proxyServerList"))) {
			CrawlerConfig.proxyServerList = populateProxyServer(properties.getProperty("proxyServerList"));
			log.info("已加载配置：代理服务器:" + CrawlerConfig.proxyServerList.size() + "个");
		} else {
			log.info("未配置：代理服务器列表");
		}
		if (!isBlank(properties.getProperty("systemRootPath"))) {
			CrawlerConfig.systemRootPath = properties.getProperty("systemRootPath");
			log.info("已加载配置：系统绝对路径:" + CrawlerConfig.systemRootPath);
		} else {
			CrawlerConfig.systemRootPath = configFilePath;
			log.info("未配置：系统绝对路径，采用系统默认:" + CrawlerConfig.systemRootPath);
		}
		if (!isBlank(properties.getProperty("httpClientMaxConn"))) {
			CrawlerConfig.httpClientMaxConn = Integer.parseInt(properties.getProperty("httpClientMaxConn"));
			log.info("已加载配置：httpclient 最大连接数:" + CrawlerConfig.httpClientMaxConn);
		} else {
			log.info("未配置：httpclient 最大连接数，采用系统默认:" + CrawlerConfig.httpClientMaxConn);
		}
		if (!isBlank(properties.getProperty("httpClientMaxRoute"))) {
			CrawlerConfig.httpClientMaxRoute = Integer.parseInt(properties.getProperty("httpClientMaxRoute"));
			log.info("已加载配置：httpclient route:" + CrawlerConfig.httpClientMaxRoute);
		} else {
			log.info("未配置：httpclient route，采用系统默认:" + CrawlerConfig.httpClientMaxRoute);
		}
		if (!isBlank(properties.getProperty("httpConnTimeout"))) {
			CrawlerConfig.httpConnTimeout = Integer.parseInt(properties.getProperty("httpConnTimeout"));
			log.info("已加载配置：httpclient 连接超时:" + CrawlerConfig.httpConnTimeout);
		} else {
			log.info("未配置：httpclient 连接超时，采用系统默认:" + CrawlerConfig.httpConnTimeout);
		}
		if (!isBlank(properties.getProperty("httpSocketTimeout"))) {
			CrawlerConfig.httpSocketTimeout = Integer.parseInt(properties.getProperty("httpSocketTimeout"));
			log.info("已加载配置：httpclient 请求超时:" + CrawlerConfig.httpSocketTimeout);
		} else {
			log.info("未配置：httpclient 请求超时，采用系统默认:" + CrawlerConfig.httpSocketTimeout);
		}
		if (!isBlank(properties.getProperty("defaultWords"))) {
			CrawlerConfig.defaultWords = properties.getProperty("defaultWords");
			log.info("已加载配置：默认替换后字符:" + CrawlerConfig.defaultWords);
		} else {
			log.info("未配置：默认替换后字符，采用系统默认:" + CrawlerConfig.defaultWords);
		}
		if (!isBlank(properties.getProperty("defaultCommonReplaceWords"))) {
			CrawlerConfig.defaultCommonReplaceWords = CommonUtils.populateWordsMap(properties.getProperty("defaultCommonReplaceWords"));
			log.info("已加载配置：默认全局替换字符:" + CrawlerConfig.defaultCommonReplaceWords);
		} else {
			log.info("未配置：默认全局替换字符");
		}
		log.info("爬虫配置文件加载完成");
	}

	/**
	 * 判断配置参数值是否为空
	 */
	private boolean isBlank(String value) {
		return StringUtils.isBlank(StringUtils.trim(value));
	}

	/**
	 * 组装代理服务器列表
	 */
	private List<Map<String, Integer>> populateProxyServer(String proxyServerStr) {
		List<Map<String, Integer>> resultList = new ArrayList<Map<String, Integer>>();
		String[] proxyArr = proxyServerStr.split(",");
		String[] tempArr = null;
		Map<String, Integer> proxyMap = null;
		for (String proxy : proxyArr) {
			tempArr = proxy.split(":");
			if (tempArr.length == 2 && !isBlank(tempArr[0]) && !isBlank(tempArr[1])) {
				proxyMap = new HashMap<String, Integer>();
				proxyMap.put(StringUtils.trim(tempArr[0]), Integer.parseInt(StringUtils.trim(tempArr[1])));
				resultList.add(proxyMap);
			}
		}
		return resultList;
	}
}