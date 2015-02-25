package com.rekoe.crawler.bean;

import org.apache.commons.lang3.StringUtils;

import com.rekoe.crawler.core.constants.GatherConstant;

/**
 * 列类型信息bean
 */
public class ColumnBean {
	/**字段名称*/
	private java.lang.String columnName;
	/**字段类型*/
	private java.lang.String columnType;
	/**字段值*/
	private java.lang.String columnValue;
	/**字段值显示*/
	private java.lang.String columnValueView;
	/**字段值类型*/
	private java.lang.String columnValueType;
	/**是否非空：0表示非空，1表示可以为空*/
	private int isNullable;
	public ColumnBean() {
		super();
	}
	public ColumnBean(String columnName, String columnType,int isNullable){
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.isNullable = isNullable;
	}
	/**
	 * @param columnName
	 * @param columnType
	 * @param columnValue
	 */
	public ColumnBean(String columnName, String columnType, String columnValue,String columnValueView,String columnValueType,int isNullable) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnValue = columnValue;
		this.columnValueView = columnValueView;
		this.columnValueType = columnValueType;
		this.isNullable = isNullable;
	}
	public java.lang.String getColumnName() {
		return columnName;
	}
	public void setColumnName(java.lang.String columnName) {
		this.columnName = columnName;
	}
	public java.lang.String getColumnType() {
		return columnType;
	}
	public void setColumnType(java.lang.String columnType) {
		this.columnType = columnType;
	}
	public java.lang.String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(java.lang.String columnValue) {
		this.columnValue = columnValue;
	}
	
	public java.lang.String getColumnValueType() {
		return columnValueType;
	}
	public void setColumnValueType(java.lang.String columnValueType) {
		this.columnValueType = columnValueType;
	}
	
	public java.lang.String getColumnValueView() {
		return columnValueView;
	}
	public void setColumnValueView(java.lang.String columnValueView) {
		this.columnValueView = columnValueView;
	}
	
	/**
	 * @return the isNullable
	 */
	public int getIsNullable() {
		return isNullable;
	}
	/**
	 * @param isNullable the isNullable to set
	 */
	public void setIsNullable(int isNullable) {
		this.isNullable = isNullable;
	}
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(columnValueView)){
			if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_KEY.equals(this.columnValueType)){
				return columnName + "=" +columnValueView+"(KV数据->取键)";
			}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE.equals(this.columnValueType)){
				return columnName + "=" +columnValueView+"(KV数据->取值)";
			}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY.equals(this.columnValueType)){
				return columnName + "=" +columnValueView+"(KV数据->取键)";
			}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE.equals(this.columnValueType)){
				return columnName + "=" +columnValueView+"(KV数据->取值)";
			}else{
				return columnName + "=" +columnValueView;	
			}
		}
		return (this.isNullable == 0 ? "*":"")+columnName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
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
		ColumnBean other = (ColumnBean) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}
	
	

}
