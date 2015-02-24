package com.rekoe.crawler.core.constants;

import java.util.List;
import java.util.Map;

/**
 * 常量定义
 * 
 * @author javacoo
 * @since 2011-11-10
 */
public abstract class Constants {
	/** 过滤器名称-连接区域过滤器 */
	public final static String FILTER_NAME_LINK_AREA = "link_area_filter";
	/** 过滤器名称-内容区域过滤器 */
	public final static String FILTER_NAME_CONTENT_AREA = "content_area_filter";
	/** 过滤器名称-摘要区域过滤器 */
	public final static String FILTER_NAME_BRIEF_AREA = "brief_area_filter";
	/** 过滤器名称-内容分页区域过滤器 */
	public final static String FILTER_NAME_PAGINATION_AREA = "pagination_area_filter";
	/** 过滤器名称-评论列表入口连接区域过滤器 */
	public final static String FILTER_NAME_COMMENT_INDEX_AREA = "comment_index_area_filter";
	/** 过滤器名称-评论内容列表区域过滤器 */
	public final static String FILTER_NAME_COMMENT_LIST_AREA = "comment_list_area_filter";
	/** 过滤器名称-评论内容区域过滤器 */
	public final static String FILTER_NAME_COMMENT_AREA = "comment_area_filter";
	/** 过滤器名称-评论链接区域过滤器 */
	public final static String FILTER_NAME_COMMENT_LINK_AREA = "comment_link_area_filter";
	/** 过滤器名称-空过滤器 */
	public final static String FILTER_NAME_EMPTY = "empty_filter";
	/** 过滤器名称-字段过滤器 */
	public final static String FILTER_NAME_FIELD = "field_filter";

	/** 采集区域KEY */
	public final static String FETCH_AREA_KEY = "fetch_area";
	/** 过滤区域KEY */
	public final static String FILTER_AREA_KEY = "filter_area";
	/** 标题图片 KEY */
	public final static String TITLE_IMAGE_KEY = "title_images_key";
	/** 标题连接 KEY */
	public final static String TITLE_LINK_KEY = "title_LINK_key";
	/** 单标签标志 */
	public final static String SINGLE_TAG = "singleTag";

	/** HTML内容 KEY */
	public final static String RES_HTML_KEY = "html_key";
	/** 图片资源map KEY */
	public final static String RES_IMAGES_MAP_KEY = "images_res_map_key";
	/** 媒体资源map KEY */
	public final static String RES_MEDIA_MAP_KEY = "media_res_map_key";
	/** 附件资源map KEY */
	public final static String RES_ATTAC_MAP_KEY = "attachment_res_map_key";
	/** . */
	public final static String IMAGE_SPLIT = ".";
	/** 默认按年月生成文件夹 */
	public final static String GENERATOR_SIMPLE_P_FORMAT = "yyyyMM";
	/** 默认按日生成文件夹 */
	public final static String GENERATOR_SIMPLE_C_FORMAT = "dd";
	/** 年月日时分秒 */
	public final static String GENERATOR_FORMAT = "yyyyMMddHHmmss";

	/** 连接集合标志 */
	public final static String LINK_KEY = "linkKey";
	/** 标题集合标志 */
	public final static String TITLE_KEY = "titleKey";
	/** 新标题图片MAP标志 */
	public final static String NEW_IMAGE_KEY = "newImageKey";
	/** 原始标题图片MAP标志 */
	public final static String ORIGN_IMAGE_KEY = "orignImageKey";
	/** 是否允许采集重复数据,默认允许 */
	public final static boolean ALLOW_REPEAT = false;
	/** 是否使用代理 */
	public final static boolean USE_PROXY = false;
	/** 采集顺序,false 表示是由下向上 */
	public final static boolean GATHER_ORDER = false;
	/** 每个线程休眠毫秒数 */
	public final static int SLEEP_TIME = 500;
	/** 采集多少条记录保存一次 */
	public final static int SAVE_NUM = 3;
	/** 摘要字数 */
	public final static int BRIEF_NUM = 200;
	/** URL类型 link类 */
	public final static String URL_TYPE_LINK = "link";
	/** URL类型 资源类 */
	public final static String URL_TYPE_RES = "res";
	/** http补全 */
	public final static String HTTP_FILL_KEY = "http://";

	/** httpclient最大连接数 */
	public final static int HTTP_CLIENT_MAX_CONN = CrawlerConfig.httpClientMaxConn;
	/** httpclient route 可以理解为 运行环境机器 到 目标机器的一条线路 */
	public final static int HTTP_CLIENT_MAX_ROUTE = CrawlerConfig.httpClientMaxRoute;
	/** httpclient注册KEY */
	public final static String HTTP_CLIENT_REG_HTTP_KEY = "http";
	/** httpclient注册KEY */
	public final static String HTTP_CLIENT_REG_HTTPS_KEY = "https";
	/** httpclient http 端口 */
	public final static int HTTP_CLIENT_HTTP_PORT = 80;
	/** httpclient https 端口 */
	public final static int HTTP_CLIENT_HTTPS_PORT = 443;
	/** httpclient 连接超时 */
	public final static int HTTP_CONN_TIMEOUT = CrawlerConfig.httpConnTimeout;
	/** httpclient 请求超时 */
	public final static int HTTP_SOCKET_TIMEOUT = CrawlerConfig.httpSocketTimeout;

	/** 爬虫状态-初始状态 */
	public final static String CRAWL_STATE_ORIGINAL = "original";
	/** 爬虫状态-准备就绪 */
	public final static String CRAWL_STATE_READY = "ready";
	/** 爬虫状态-运行中 */
	public final static String CRAWL_STATE_RUNNING = "running";
	/** 爬虫状态-暂停 */
	public final static String CRAWL_STATE_PAUSE = "pause";

	/** 开启线程数,当前机器Cpu数 */
	public final static int THREAD_NUM = CrawlerConfig.threadNum;
	/** 一个任务的超时时间，单位为毫秒 */
	public final static int TASK_TIMEOUT = CrawlerConfig.taskTimeOut;
	/** 允许采集进来的外部资源类型 */
	public final static String EXTRACT_RES_TYPE = CrawlerConfig.extractResType;
	/** 允许采集进来的外部媒体资源类型 */
	public final static String EXTRACT_MEDIA_RES_TYPE =CrawlerConfig.extractMediaResType;
	/** 是否重命名,false:不重名,true:重命名,默认false */
	public final static String REPLACE_NAME = CrawlerConfig.replaceResName;
	/** 资源保存根路径 */
	// public final static String RES_SAVE_ROOT_PATH =
	// CrawlerConfig.resSaveRootPath;
	/** 资源保存根路径 */
	public final static String RES_SAVE_ROOT_PATH = CrawlerConfig.resSaveRootPath;
	/** 资源保存相对路径 */
	// public final static String RES_SAVE_PATH = CrawlerConfig.resSavePath;
	/** 资源保存相对路径 */
	public final static String RES_SAVE_PATH = CrawlerConfig.resSavePath;
	/** 系统绝对路径 */
	// public final static String SYSTEM_ROOT_PATH =
	// CrawlerConfig.systemRootPath;
	public final static String SYSTEM_ROOT_PATH = CrawlerConfig.systemRootPath;
	/** 代理服务器MAP列表,key为服务器地址,value为端口，配置格式为:address:port,address:port,..... */
	public final static List<Map<String, Integer>> PROXY_SERVER_LIST = null;//CommonUtils.populateProxyServer(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_PROXY_SERVER_LIST));
	/** 默认替换后字符 */
	public final static String DEFAULT_WORDS = "REKOE";//org.javacoo.crawler.core.constants.Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_WORDS);
	/** 默认全局替换字符,格式:待替换字符1=替换后字符1,待替换字符2=替换后字符2,待替换字符3,待替换字符4... */
	public final static Map<String, String> DEFAULT_COMMON_REPLACE_WORDS = null;//CommonUtils.populateWordsMap(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_COMMON_REPLACE_WORDS));

	/** 路径类型：绝对:0 */
	public final static String PATH_TYPE_0 = "0";
	/** 路径类型：相对根路径:1 */
	public final static String PATH_TYPE_1 = "1";
	/** 路径类型：相对当前路径:2 */
	public final static String PATH_TYPE_2 = "2";

}
