/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.rekoe.crawler.bean;

/**
 * 扩展字段bean
 *@author DuanYong
 *@since 2013-3-18上午9:38:29
 *@version 1.0
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
