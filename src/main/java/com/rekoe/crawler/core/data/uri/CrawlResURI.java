package com.rekoe.crawler.core.data.uri;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

/**
 * 资源URI对象
 * @author javacoo
 * @since 2012-05-14
 */
public class CrawlResURI implements CrawlURI{
	/**父资源对象*/
	private CrawlURI parentURI;
	/**主机*/
	private String host;
	/**端口*/
	private int port;
	/**路径：除去主机和端口剩余部分*/
	private String rawPath;
	/**当前资源路径：如果是将资源采集回来，则此路径是新资源地址，否则是源资源地址*/
	private String url;
	/**源资源地址*/
	private String originResUrl;
	/**新资源地址*/
	private String newResUrl;
	/**路径类型，绝对:0,相对根路径:1,相对当前路径:2*/
	private String pathType;
	/**资源类型：图片资源，媒体资源，附件资源*/
	private String resType;
	/**资源名称*/
	private String name;
	/**资源描述*/
	private String desc;
	
	
	public CrawlURI getParentURI() {
		return parentURI;
	}
	public void setParentURI(CrawlURI parentURI) {
		this.parentURI = parentURI;
	}
	public String getHost() {
		if(StringUtils.isBlank(host) && null != this.parentURI){
			host = this.parentURI.getHost();
		}
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		if(0 == port && null != this.parentURI){
			port = this.parentURI.getPort();
		}
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRawPath() {
		return rawPath;
	}
	public void setRawPath(String rawPath) {
		this.rawPath = rawPath;
	}
	public String getOriginResUrl() {
		return originResUrl;
	}
	public void setOriginResUrl(String originResUrl) {
		this.originResUrl = originResUrl;
	}
	public String getNewResUrl() {
		return newResUrl;
	}
	public void setNewResUrl(String newResUrl) {
		this.newResUrl = newResUrl;
	}
	public String getPathType() {
		return pathType;
	}
	public void setPathType(String pathType) {
		this.pathType = pathType;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		if(StringUtils.isNotBlank(name)){
			return name;
		}
		if(StringUtils.isNotBlank(originResUrl)){
			name = originResUrl.substring(originResUrl.lastIndexOf(File.separator), originResUrl.length());
		}
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	

}
