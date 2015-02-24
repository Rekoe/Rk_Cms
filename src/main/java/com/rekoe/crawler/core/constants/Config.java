package com.rekoe.crawler.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置参数
 * @author javacoo
 * @since 2012-02-29
 */
public abstract class Config {
	
	/**
	 * 配置参数MAP
	 */
	public static Map<String, Map<String, String>> CRAWLER_CONFIG_MAP = new HashMap<String, Map<String,String>>();
	/**
	 * 配置参数文件路径MAP
	 */
	public static Map<String, String> CRAWLER_CONFIG_RESOURCES_PATH_MAP = new HashMap<String, String>();
	//----------------------------窗体国际化参数-----------------------------------------
	
	/**
	 * 窗体国际化参数key
	 */
	public static String CRAWLER_CONFIG_KEY_LOCAL = "crawlerAppLocal";
	/**
	 * 主窗体-标题
	 */
	public static String CRAWLER_CONFIG_KEY_LOCAL_MAIN_FRAME_TITLE = "CrawlerMainFrame.title";
	//----------------------------窗体资源配置-----------------------------------------
	
	/**
	 * 窗体资源配置key
	 */
	public static String CRAWLER_CONFIG_KEY_RES = "crawlerAppLocalRes";
	//---------------------------数据库配置-----------------------------------------
	/**
	 * 数据库配置key
	 */
	public static String CRAWLER_CONFIG_KEY_DATABASE = "crawlerAppDatabase";
	
	public static String CRAWLER_CONFIG_KEY_DATABASE_DEFAULT_DATABASE_LIST = "dataBaseNameList";
	public static String CRAWLER_CONFIG_KEY_DATABASE_DEFAULT_DB_NAME = "defaultDbName";
	public static String CRAWLER_CONFIG_KEY_DATABASE_DEFAULT_WAIT_TIME = "defaultWaitTime";
	public static String CRAWLER_CONFIG_KEY_DATABASE_DRIVERS = ".drivers";
	public static String CRAWLER_CONFIG_KEY_DATABASE_URL = ".url";
	public static String CRAWLER_CONFIG_KEY_DATABASE_USER = ".user";
	public static String CRAWLER_CONFIG_KEY_DATABASE_PASSWORD = ".password";
	public static String CRAWLER_CONFIG_KEY_DATABASE_MAXCONN = ".maxconn";
	
	//---------------------------日志配置-----------------------------------------
	/**
	 * 日志配置key
	 */
	public static String CRAWLER_CONFIG_KEY_LOG = "crawlerAppLog";
	
	public static String CRAWLER_CONFIG_KEY_LOG_FILE_PAHT = "logFilePath";
	
	public static String CRAWLER_CONFIG_KEY_LOG_DEBUG = "debug";
	//---------------------------核心配置-----------------------------------------
	/**
	 * 核心配置key
	 */
	public static String CRAWLER_CONFIG_KEY_CORE = "crawlerAppCore";
	/**开启线程数*/
	public static String CRAWLER_CONFIG_KEY_CORE_THREAD_NUM  = "threadNum";
	/**一个任务的超时时间，单位为毫秒*/
	public static String CRAWLER_CONFIG_KEY_CORE_TIME_OUT = "taskTimeOut";
	/**资源保存根路径*/
	public static String CRAWLER_CONFIG_KEY_CORE_RES_SAVE_ROOT_PATH = "resSaveRootPath";
	/**资源保存相对路径,一般留空,默认按照年月生成文件夹，然后再按照当天时间生成子文件夹如：201202/29*/
	public static String CRAWLER_CONFIG_KEY_CORE_RES_SAVE_PATH = "resSavePath";
	/**允许采集进来的外部资源类型*/
	public static String CRAWLER_CONFIG_KEY_CORE_EXTRACT_RES_TYPE = "extractResType";
	/**资源是否重命名,false:不重名,true:重命名,默认false*/
	public static String CRAWLER_CONFIG_KEY_CORE_REPLACE_RES_NAME = "replaceResName";
	/**允许采集进来的外部媒体资源类型*/
	public static String CRAWLER_CONFIG_KEY_CORE_EXTRACT_MEDIA_RES_TYPE = "extractMediaResType";
	/**代理服务器MAP列表,key为服务器地址,value为端口，配置格式为:address:port,address:port,.....*/
	public static String CRAWLER_CONFIG_KEY_CORE_PROXY_SERVER_LIST = "proxyServerList";
	/**默认替换后字符*/
	public static String CRAWLER_CONFIG_KEY_CORE_DEFAULT_WORDS = "defaultWords";
	/**默认全局替换字符,格式:待替换字符1=替换后字符1,待替换字符2=替换后字符2,待替换字符3,待替换字符4...*/
	public static String CRAWLER_CONFIG_KEY_CORE_DEFAULT_COMMON_REPLACE_WORDS = "defaultCommonReplaceWords";
	/**系统classLoaderPath*/
	public static String CRAWLER_CONFIG_KEY_CORE_CLASS_LOADER_PATH = "classLoaderPath";
	/**accept*/
	public static String CRAWLER_CONFIG_KEY_CORE_ACCEPT = "accept";
	/**acceptLanguage*/
	public static String CRAWLER_CONFIG_KEY_CORE_ACCEPT_LANGUAGE = "acceptLanguage";
	/**userAgent*/
	public static String CRAWLER_CONFIG_KEY_CORE_USER_AGENT = "userAgent";
	/**acceptCharset*/
	public static String CRAWLER_CONFIG_KEY_CORE_ACCEPT_CHARSET = "acceptCharset";
	/**keepAlive*/
	public static String CRAWLER_CONFIG_KEY_CORE_KEEP_ALIVE = "keepAlive";
	/**connection*/
	public static String CRAWLER_CONFIG_KEY_CORE_CONNECTION = "connection";
	/**cacheControl*/
	public static String CRAWLER_CONFIG_KEY_CORE_CACHE_CONTROL = "cacheControl";
	
	//---------------------------界面基本配置-----------------------------------------
	/**
	 * 界面基本配置KEY
	 */
	public static String CRAWLER_CONFIG_KEY_INIT = "crawlerAppInitConfig";
	/**主窗体宽*/
	public static String CRAWLER_CONFIG_KEY_INIT_FRAME_WIDTH = "frameWidth";
	/**主窗体高*/
	public static String CRAWLER_CONFIG_KEY_INIT_FRAME_HEIGHT= "frameHeight";
	/**显示广告*/
	public static String CRAWLER_CONFIG_KEY_INIT_SHOW_AD = "showAdvertisement";
	/**显示广告路径*/
	public static String CRAWLER_CONFIG_KEY_INIT_SHOW_AD_PATH = "showAdvertisementPath";
	/**播放音乐*/
	public static String CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC = "showMusic";
	/**进入主界面欢迎音乐名称*/
	public static String CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_WELCOME = "welocmeMusicName";
	/**消息提醒音乐名称*/
	public static String CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_MSG = "msgMusicName";
	/**丑牛主版本信息*/
	public static String CRAWLER_CONFIG_KEY_INIT_VERSION = "version";
	
	
}
