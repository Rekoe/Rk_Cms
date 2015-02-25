package com.rekoe.crawler.bean;

/**
 * 采集规则基本参数值对象
 */
public class RuleBaseBean {
	/**采集名称*/
	private String ruleName;
	/**暂停时间*/
	private java.lang.Integer pauseTime = 500;
	/**页面编码*/
	private java.lang.String pageEncoding;
	/**页面链接补全URL编码*/
	private java.lang.String urlRepairUrl;
	/**资源补全URL编码*/
	private java.lang.String resourceRepairUrl;
	/**内容分页补全URL编码*/
	private java.lang.String paginationRepairUrl;
	/**是否去除连接*/
	private java.lang.String replaceLinkFlag;
	/**是否去重标准*/
	private java.lang.String repeatCheckFlag;
	/**是否采集资源*/
	private java.lang.String saveResourceFlag;
	/**是否去使用代理*/
	private java.lang.String useProxyFlag;
	/**是否随机日期*/
	private java.lang.String randomDateFlag;
	/**采集顺序*/
	private java.lang.String gatherOrderFlag;
	/**代理地址*/
	private java.lang.String proxyAddress;
	/**代理端口*/
	private java.lang.String proxyPort;
	/**替换字符*/
	private java.lang.String replaceWords;
	/**随机日期格式*/
	private java.lang.String dateFormat;
	/**随机日期开始*/
	private java.lang.String startRandomDate;
	/**随机日期结束*/
	private java.lang.String endRandomDate;
	/**采集数量*/
	private java.lang.String gatherNum;
	
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public java.lang.Integer getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(java.lang.Integer pauseTime) {
		this.pauseTime = pauseTime;
	}
	public java.lang.String getPageEncoding() {
		return pageEncoding;
	}
	public void setPageEncoding(java.lang.String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}
	public java.lang.String getUrlRepairUrl() {
		return urlRepairUrl;
	}
	public void setUrlRepairUrl(java.lang.String urlRepairUrl) {
		this.urlRepairUrl = urlRepairUrl;
	}
	public java.lang.String getResourceRepairUrl() {
		return resourceRepairUrl;
	}
	public void setResourceRepairUrl(java.lang.String resourceRepairUrl) {
		this.resourceRepairUrl = resourceRepairUrl;
	}
	public java.lang.String getPaginationRepairUrl() {
		return paginationRepairUrl;
	}
	public void setPaginationRepairUrl(java.lang.String paginationRepairUrl) {
		this.paginationRepairUrl = paginationRepairUrl;
	}
	public java.lang.String getReplaceLinkFlag() {
		return replaceLinkFlag;
	}
	public void setReplaceLinkFlag(java.lang.String replaceLinkFlag) {
		this.replaceLinkFlag = replaceLinkFlag;
	}
	public java.lang.String getRepeatCheckFlag() {
		return repeatCheckFlag;
	}
	public void setRepeatCheckFlag(java.lang.String repeatCheckFlag) {
		this.repeatCheckFlag = repeatCheckFlag;
	}
	public java.lang.String getSaveResourceFlag() {
		return saveResourceFlag;
	}
	public void setSaveResourceFlag(java.lang.String saveResourceFlag) {
		this.saveResourceFlag = saveResourceFlag;
	}
	public java.lang.String getUseProxyFlag() {
		return useProxyFlag;
	}
	public void setUseProxyFlag(java.lang.String useProxyFlag) {
		this.useProxyFlag = useProxyFlag;
	}
	public java.lang.String getProxyAddress() {
		return proxyAddress;
	}
	public void setProxyAddress(java.lang.String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}
	public java.lang.String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(java.lang.String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public java.lang.String getReplaceWords() {
		return replaceWords;
	}
	public void setReplaceWords(java.lang.String replaceWords) {
		this.replaceWords = replaceWords;
	}
	public java.lang.String getRandomDateFlag() {
		return randomDateFlag;
	}
	public void setRandomDateFlag(java.lang.String randomDateFlag) {
		this.randomDateFlag = randomDateFlag;
	}
	public java.lang.String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(java.lang.String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public java.lang.String getStartRandomDate() {
		return startRandomDate;
	}
	public void setStartRandomDate(java.lang.String startRandomDate) {
		this.startRandomDate = startRandomDate;
	}
	public java.lang.String getEndRandomDate() {
		return endRandomDate;
	}
	public void setEndRandomDate(java.lang.String endRandomDate) {
		this.endRandomDate = endRandomDate;
	}
	public java.lang.String getGatherOrderFlag() {
		return gatherOrderFlag;
	}
	public void setGatherOrderFlag(java.lang.String gatherOrderFlag) {
		this.gatherOrderFlag = gatherOrderFlag;
	}
	public java.lang.String getGatherNum() {
		return gatherNum;
	}
	public void setGatherNum(java.lang.String gatherNum) {
		this.gatherNum = gatherNum;
	}
	
}
