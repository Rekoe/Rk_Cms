package com.rekoe.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author 科技㊣²º¹³
 * 2014年2月4日 下午2:08:59
 * http://www.rekoe.com
 * QQ:5382211
 */
@Table("system_setting")
public class Setting {
	@Id
	private int id;
	@Column("site_name")
	private String siteName;
	@Column("certtext")
	@ColDefine(type=ColType.VARCHAR,width=255)
	private String certtext;
	@Column("site_enabled")
	private boolean siteEnabled;
	@Column("site_close_message")
	@ColDefine(type=ColType.TEXT)
	private String siteCloseMessage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getCerttext() {
		return certtext;
	}
	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}
	public boolean isSiteEnabled() {
		return siteEnabled;
	}
	public void setSiteEnabled(boolean siteEnabled) {
		this.siteEnabled = siteEnabled;
	}
	public String getSiteCloseMessage() {
		return siteCloseMessage;
	}
	public void setSiteCloseMessage(String siteCloseMessage) {
		this.siteCloseMessage = siteCloseMessage;
	}
}
