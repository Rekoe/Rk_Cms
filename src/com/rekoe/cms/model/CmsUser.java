package com.rekoe.cms.model;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("rk_user")
public class CmsUser implements Serializable{

	private static final long serialVersionUID = 1323567417767391619L;

	@Id
	private int id;
	@Column
	private String openid;
	@Column("oauth_provider")
	private String oAuthProvider;
	@Column
	private String username;
	@Column()
	private Date lastLoginDate;
	
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getoAuthProvider() {
		return oAuthProvider;
	}
	public void setoAuthProvider(String oAuthProvider) {
		this.oAuthProvider = oAuthProvider;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
