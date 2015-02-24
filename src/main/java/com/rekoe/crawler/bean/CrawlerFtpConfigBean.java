/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.rekoe.crawler.bean;

/**
 * FTP配置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午3:48:33
 * @version 1.0
 */
public class CrawlerFtpConfigBean {
	/**FTP服务器名称*/
	private String ftpName;
	/**FTP服务器hostname*/
	private String ftpUrl;
	/**FTP服务器端口*/
	private String ftpPort;
	/**FTP登录账号*/
	private String ftpUserName;
	/**FTP登录密码*/
	private String ftpPassword;
	/**是否使用FTP*/
	private String useFtpFlag;
	/**FTP目录*/
	private String ftpDirPath;
	
	/**
	 * @return the ftpName
	 */
	public String getFtpName() {
		return ftpName;
	}
	/**
	 * @param ftpName the ftpName to set
	 */
	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}
	/**
	 * @return the ftpUrl
	 */
	public String getFtpUrl() {
		return ftpUrl;
	}
	/**
	 * @param ftpUrl the ftpUrl to set
	 */
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	/**
	 * @return the ftpPort
	 */
	public String getFtpPort() {
		return ftpPort;
	}
	/**
	 * @param ftpPort the ftpPort to set
	 */
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	/**
	 * @return the ftpUserName
	 */
	public String getFtpUserName() {
		return ftpUserName;
	}
	/**
	 * @param ftpUserName the ftpUserName to set
	 */
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	/**
	 * @return the ftpPassword
	 */
	public String getFtpPassword() {
		return ftpPassword;
	}
	/**
	 * @param ftpPassword the ftpPassword to set
	 */
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
	/**
	 * @return the useFtpFlag
	 */
	public String getUseFtpFlag() {
		return useFtpFlag;
	}
	/**
	 * @param useFtpFlag the useFtpFlag to set
	 */
	public void setUseFtpFlag(String useFtpFlag) {
		this.useFtpFlag = useFtpFlag;
	}
	
	/**
	 * @return the ftpDirPath
	 */
	public String getFtpDirPath() {
		return ftpDirPath;
	}
	/**
	 * @param ftpDirPath the ftpDirPath to set
	 */
	public void setFtpDirPath(String ftpDirPath) {
		this.ftpDirPath = ftpDirPath;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ftpPort == null) ? 0 : ftpPort.hashCode());
		result = prime * result + ((ftpUrl == null) ? 0 : ftpUrl.hashCode());
		result = prime * result
				+ ((ftpUserName == null) ? 0 : ftpUserName.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrawlerFtpConfigBean other = (CrawlerFtpConfigBean) obj;
		if (ftpPort == null) {
			if (other.ftpPort != null)
				return false;
		} else if (!ftpPort.equals(other.ftpPort))
			return false;
		if (ftpUrl == null) {
			if (other.ftpUrl != null)
				return false;
		} else if (!ftpUrl.equals(other.ftpUrl))
			return false;
		if (ftpUserName == null) {
			if (other.ftpUserName != null)
				return false;
		} else if (!ftpUserName.equals(other.ftpUserName))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ftpName;
	}
	
	
	

}
