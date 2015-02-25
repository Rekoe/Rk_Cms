package com.rekoe.crawler.bean;

import java.util.List;

/**
 * 扩展字段值对象
 */
public class RuleFieldsBean {
	/**扩展字段集合*/
	private List<ExtendFieldsBean> extendFields;

	public List<ExtendFieldsBean> getExtendFields() {
		return extendFields;
	}

	public void setExtendFields(List<ExtendFieldsBean> extendFields) {
		this.extendFields = extendFields;
	}
}
