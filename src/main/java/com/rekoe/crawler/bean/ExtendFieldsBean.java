package com.rekoe.crawler.bean;

/**
 * 扩展字段bean
 */
public class ExtendFieldsBean {
	/**扩展字段*/
	private String fields;
	/**扩展字段标签*/
	private String filterStart;
	/**扩展字段过滤标签*/
	private String filterEnd;
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getFilterStart() {
		return filterStart;
	}
	public void setFilterStart(String filterStart) {
		this.filterStart = filterStart;
	}
	public String getFilterEnd() {
		return filterEnd;
	}
	public void setFilterEnd(String filterEnd) {
		this.filterEnd = filterEnd;
	}
	@Override
	public String toString() {
		return "ExtendFieldsBean [fields=" + fields + ", filterStart="
				+ filterStart + ", filterEnd=" + filterEnd + "]";
	}
	
	
	
	

}
