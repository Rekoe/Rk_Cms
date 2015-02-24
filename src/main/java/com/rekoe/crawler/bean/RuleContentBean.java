package com.rekoe.crawler.bean;


import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 采集规则内容参数值对象
 *@author DuanYong
 *@since 2012-11-15上午9:33:22
 *@version 1.0
 */
public class RuleContentBean {
	/**采集地址*/
	private java.lang.String planList;
	/**动态地址*/
	private java.lang.String dynamicAddr;
	/**动态地址开始页码*/
	private java.lang.Integer dynamicStart = 2;
	/**动态地址开结束页码*/
	private java.lang.Integer dynamicEnd = 10;
	/**连接区域开始标签*/
	private java.lang.String linksetStart;
	/**连接区域结束标签*/
	private java.lang.String linksetEnd;
	/**描述开始标签*/
	private java.lang.String descriptionStart;
	/**描述结束标签*/
	private java.lang.String descriptionEnd;
	/**内容开始标签*/
	private java.lang.String contentStart;
	/**内容结束标签*/
	private java.lang.String contentEnd;
	/**过度连接字段集合*/
	private List<ExtendFieldsBean> midExtendFields;
	/**
	 * 动态页翻页页号
	 */
	public static final String PAGE = "[page]";
	public java.lang.String getPlanList() {
		return planList;
	}
	public void setPlanList(java.lang.String planList) {
		this.planList = planList;
	}
	public java.lang.String getDynamicAddr() {
		return dynamicAddr;
	}
	public void setDynamicAddr(java.lang.String dynamicAddr) {
		this.dynamicAddr = dynamicAddr;
	}
	public java.lang.Integer getDynamicStart() {
		return dynamicStart;
	}
	public void setDynamicStart(java.lang.Integer dynamicStart) {
		this.dynamicStart = dynamicStart;
	}
	public java.lang.Integer getDynamicEnd() {
		return dynamicEnd;
	}
	public void setDynamicEnd(java.lang.Integer dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}
	public java.lang.String getLinksetStart() {
		return linksetStart;
	}
	public void setLinksetStart(java.lang.String linksetStart) {
		this.linksetStart = linksetStart;
	}
	public java.lang.String getLinksetEnd() {
		return linksetEnd;
	}
	public void setLinksetEnd(java.lang.String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}
	
	public java.lang.String getDescriptionStart() {
		return descriptionStart;
	}
	public void setDescriptionStart(java.lang.String descriptionStart) {
		this.descriptionStart = descriptionStart;
	}
	public java.lang.String getDescriptionEnd() {
		return descriptionEnd;
	}
	public void setDescriptionEnd(java.lang.String descriptionEnd) {
		this.descriptionEnd = descriptionEnd;
	}
	public java.lang.String getContentStart() {
		return contentStart;
	}
	public void setContentStart(java.lang.String contentStart) {
		this.contentStart = contentStart;
	}
	public java.lang.String getContentEnd() {
		return contentEnd;
	}
	public void setContentEnd(java.lang.String contentEnd) {
		this.contentEnd = contentEnd;
	}
	
	/**
	 * @return the midExtendFields
	 */
	public List<ExtendFieldsBean> getMidExtendFields() {
		return midExtendFields;
	}
	/**
	 * @param midExtendFields the midExtendFields to set
	 */
	public void setMidExtendFields(List<ExtendFieldsBean> midExtendFields) {
		this.midExtendFields = midExtendFields;
	}
	public String[] getPlans() {
		String plan = getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}
	public String[] getAllPlans() {
		String[] plans = getPlans();
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null && start >= 0 && end >= start) {
			int plansLen = plans.length;
			String[] allPlans = new String[plansLen + end - start + 1];
			for (int i = 0; i < plansLen; i++) {
				allPlans[i] = plans[i];
			}
			for (int i = 0, len = end - start + 1; i < len; i++) {
				allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
			}
			return allPlans;
		} else {
			return plans;
		}
	}
	private String getDynamicAddrByNum(int num) {
		return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
	}
	
	
	

}
