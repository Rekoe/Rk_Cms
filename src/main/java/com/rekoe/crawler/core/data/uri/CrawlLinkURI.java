package com.rekoe.crawler.core.data.uri;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 爬虫URI对象
 * @author javacoo
 * @since 2012-04-21
 */
public class CrawlLinkURI implements CrawlURI{
	/**来源URI*/
	private CrawlURI parentURI;
	/**URL全路径*/
	private String url;
	/**主机*/
	private String host;
	/**端口*/
	private int port;
	/**路径：除去主机和端口剩余部分*/
	private String rawPath;
	/**是否是种子*/
	private boolean isSeed = false;
	/**标题*/
	private String title;
	/**类型：连接和资源*/
	private String type;
	/**源资对象*/
	private CrawlResURI resURI = new CrawlResURI();
	/**路径类型，绝对:0,相对根路径:1,相对当前路径:2*/
	private String pathType;
	/**资源描述*/
	private String desc;
	public CrawlLinkURI() {
		super();
	}
	public CrawlLinkURI(String url,String title, String type,String pathType) {
		super();
		this.url = url;
		this.title = title;
		this.type = type;
		this.pathType = pathType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPathType() {
		return pathType;
	}
	public void setPathType(String pathType) {
		this.pathType = pathType;
	}
	
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
		if(StringUtils.isBlank(host) && null != this.parentURI){
			host = this.parentURI.getHost();
		}
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
	public boolean isSeed() {
		return isSeed;
	}
	public void setSeed(boolean isSeed) {
		this.isSeed = isSeed;
	}
	
	
	public CrawlResURI getResURI() {
		return resURI;
	}
	public void setResURI(CrawlResURI resURI) {
		this.resURI = resURI;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		if(StringUtils.isNotBlank(url)){
			return url.substring(url.lastIndexOf(File.separator), url.length());
		}
		return "";
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
	/**
	 * 清理工作
	 */
	public void clear(){
		setParentURI(null);
		setTitle(null);
		setHost(null);
		setType(null);
		setPathType(null);
		setRawPath(null);
		setResURI(null);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrawlLinkURI other = (CrawlLinkURI) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!FilenameUtils.getName(url).equals(FilenameUtils.getName(other.url)))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Url [title=" + title + ", type=" + type + ", url=" + url + "]";
	}
	
	

}
