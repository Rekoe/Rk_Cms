package com.rekoe.crawler.core.constants;

import java.util.List;
import java.util.Map;

/**
 * 爬虫参数配置
 * 
 * @author javacoo
 * @since 2012-02-29
 */
public abstract class CrawlerConfig {
	/** 开启线程数 */
	public static int threadNum = Runtime.getRuntime().availableProcessors();
	/** 一个任务的超时时间，单位为毫秒 */
	public static int taskTimeOut = 5000;
	/** 访问失败时，允许重试访问的次数，默认0次 */
	public static int tryMaxNum = 0;
	/** httpclient 最大连接数,默认100 */
	public static int httpClientMaxConn = 100;
	/** httpclient route 可以理解为 运行环境机器 到 目标机器的一条线路，默认20 */
	public static int httpClientMaxRoute = 20;
	/** httpclient 连接超时，默认10秒 */
	public static int httpConnTimeout = 10000;
	/** httpclient 请求超时，默认10秒 */
	public static int httpSocketTimeout = 10000;
	/** 资源保存根路径 */
	public static String resSaveRootPath = "/";
	/** 资源保存相对路径,一般留空,默认按照年月生成文件夹，然后再按照当天时间生成子文件夹如：201202/29 */
	public static String resSavePath = "";
	/** 允许采集进来的外部资源类型 */
	public static String extractResType = "doc,docx,excel,txt,pdf,rar";
	/** 允许采集进来的外部媒体资源类型 */
	public static String extractMediaResType = "swf,flv";
	/** 资源是否重命名,false:不重名,true:重命名,默认false */
	public static String replaceResName = "false";
	/** 代理服务器MAP列表,key为服务器地址,value为端口，配置格式为:address:port,address:port,..... */
	public static List<Map<String, Integer>> proxyServerList = null;
	/** 系统绝对路径 */
	public static String systemRootPath = System.getProperties().getProperty("user.dir") + System.getProperties().getProperty("file.separator");
	/** 默认替换后字符 */
	public static String defaultWords = "";
	/** 默认全局替换字符,格式:待替换字符1=替换后字符1,待替换字符2=替换后字符2,待替换字符3,待替换字符4... */
	public static Map<String, String> defaultCommonReplaceWords = null;

}
