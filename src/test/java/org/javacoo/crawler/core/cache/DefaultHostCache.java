package org.javacoo.crawler.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.uri.CrawlURI;
/**
 * HttpHost对象缓存接口实现类
 * @author javacoo
 * @since 2012-05-14
 */
public class DefaultHostCache implements HostCache{
	private static Log log =  LogFactory.getLog(DefaultHostCache.class);
	/**主机缓存*/
	private Map<String,HttpHost> hostCache = new ConcurrentHashMap<String, HttpHost>();
	/**
	 * 根据主机名取得缓存中的HttpHost对象,如果不存在则将此主机缓存并返回
	 * @param crawlURI 爬虫URI对象
	 * @return HttpHost对象
	 */
	public HttpHost getHttpHost(CrawlURI crawlURI){
		String hostName = crawlURI.getHost();
		int port = crawlURI.getPort();
		if(StringUtils.isBlank(hostName)){
			hostName = crawlURI.getParentURI().getHost();
			port = crawlURI.getParentURI().getPort();
		}
		cacheHttpHost(hostName,port);
		log.info("=====================取得缓存中的HttpHost对象========="+hostName+port);
		return this.hostCache.get(StringUtils.trim(hostName+port));
	} 
	/**
	 * 清理缓存
	 */
	public void clear(){
		hostCache.clear();
	}
	/**
	 * 缓存主机
	 * @param hostName 主机名
	 * @param port 端口
	 */
	private void cacheHttpHost(String hostName,int port){
		String key = hostName + port;
		if(!httpHostHasCache(key)){
			log.info("=====================缓存主机========="+Thread.currentThread().getName()+key);
			int clientPort = Constants.HTTP_CLIENT_HTTP_PORT;
			if(port != -1){
				clientPort = Integer.valueOf(port);
			}
			this.hostCache.put(StringUtils.trim(key), new HttpHost(StringUtils.trim(hostName), clientPort, Constants.HTTP_CLIENT_REG_HTTP_KEY));
		}
	}
	/**
	 * 检查指定主机名是否已缓存
	 * @param hostName
	 * @return
	 */
	private boolean httpHostHasCache(String hostName){
		if(StringUtils.isNotBlank(hostName) && this.hostCache.containsKey(StringUtils.trim(hostName))){
			return true;
		}
		return false;
	}

}
