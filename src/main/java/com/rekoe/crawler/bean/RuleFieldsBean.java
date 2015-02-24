package com.rekoe.crawler.bean;

import java.util.List;

/**
 * 扩展字段值对象
 *@author DuanYong
 *@since 2012-11-15上午9:36:08
 *@version 1.0
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
