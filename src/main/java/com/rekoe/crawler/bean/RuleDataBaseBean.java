package com.rekoe.crawler.bean;

import java.util.Map;

/**
 * 采集规则远程数据库配置
 *@author DuanYong
 *@since 2013-3-2下午7:46:00
 *@version 1.0
 */
public class RuleDataBaseBean {
	/**是否保存至指定数据库*/
	private java.lang.String saveToDataBaseFlag;
	/**数据库ID*/
	private java.lang.String dataBaseId;
	/**主表名称*/
	private java.lang.String primaryTable;
	/**内容分页标签*/
	private java.lang.String contentPageTag;
	/**数据值信息map*/
    private Map<String,Map<String,ColumnBean>> hasSelectedValueMap;

	public java.lang.String getSaveToDataBaseFlag() {
		return saveToDataBaseFlag;
	}
	public void setSaveToDataBaseFlag(java.lang.String saveToDataBaseFlag) {
		this.saveToDataBaseFlag = saveToDataBaseFlag;
	}
	public java.lang.String getDataBaseId() {
		return dataBaseId;
	}
	public void setDataBaseId(java.lang.String dataBaseId) {
		this.dataBaseId = dataBaseId;
	}
	
	/**
	 * @return the primaryTable
	 */
	public java.lang.String getPrimaryTable() {
		return primaryTable;
	}
	/**
	 * @param primaryTable the primaryTable to set
	 */
	public void setPrimaryTable(java.lang.String primaryTable) {
		this.primaryTable = primaryTable;
	}
	public Map<String, Map<String, ColumnBean>> getHasSelectedValueMap() {
		return hasSelectedValueMap;
	}
	public void setHasSelectedValueMap(
			Map<String, Map<String, ColumnBean>> hasSelectedValueMap) {
		this.hasSelectedValueMap = hasSelectedValueMap;
	}
	/**
	 * @return the contentPageTag
	 */
	public java.lang.String getContentPageTag() {
		return contentPageTag;
	}
	/**
	 * @param contentPageTag the contentPageTag to set
	 */
	public void setContentPageTag(java.lang.String contentPageTag) {
		this.contentPageTag = contentPageTag;
	}
	
	
	
}
