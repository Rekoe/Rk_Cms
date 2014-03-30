package com.rekoe.domain;

import com.restfb.types.User;

/**
 * @author 科技㊣²º¹³
 * 2014年3月30日 下午6:41:38
 * http://www.rekoe.com
 * QQ:5382211
 */
public class FacebookAccount {

	private String code;
	private String[] requestIds;
	private User user;
	private Account account;

	public FacebookAccount(String code, String[] requestIds, User user, Account account) {
		this.code = code;
		this.requestIds = requestIds;
		this.user = user;
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getRequestIds() {
		return requestIds;
	}

	public void setRequestIds(String[] requestIds) {
		this.requestIds = requestIds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
