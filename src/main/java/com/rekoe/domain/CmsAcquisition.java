package com.rekoe.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("acquisition")
public class CmsAcquisition {

	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String pageEncoding;
	@Column
	@ColDefine(type=ColType.TEXT)
	private String planUrl;
	@Column
	private String linksetStart;
	@Column
	private String linksetEnd;
	@Column
	private String titleStart;
	@Column
	private String titleEnd;
	@Column
	private String contentStart;
	@Column
	private String contentEnd;

	@Column("article_category_id")
	private String articleCategoryId;

	public CmsAcquisition() {
	}

	public CmsAcquisition(int id) {
		this.setId(id);
	}

	public CmsAcquisition(String name, String pageEncoding) {
		this.setName(name);
		this.setPageEncoding(pageEncoding);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageEncoding() {
		return pageEncoding;
	}

	public void setPageEncoding(String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	public String getPlanUrl() {
		return planUrl;
	}

	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}

	public String getLinksetStart() {
		return linksetStart;
	}

	public void setLinksetStart(String linksetStart) {
		this.linksetStart = linksetStart;
	}

	public String getLinksetEnd() {
		return linksetEnd;
	}

	public void setLinksetEnd(String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}

	public String getTitleStart() {
		return titleStart;
	}

	public void setTitleStart(String titleStart) {
		this.titleStart = titleStart;
	}

	public String getTitleEnd() {
		return titleEnd;
	}

	public void setTitleEnd(String titleEnd) {
		this.titleEnd = titleEnd;
	}

	public String getContentStart() {
		return contentStart;
	}

	public void setContentStart(String contentStart) {
		this.contentStart = contentStart;
	}

	public String getContentEnd() {
		return contentEnd;
	}

	public void setContentEnd(String contentEnd) {
		this.contentEnd = contentEnd;
	}

	public String getArticleCategoryId() {
		return articleCategoryId;
	}

	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}

}