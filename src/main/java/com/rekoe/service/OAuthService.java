package com.rekoe.service;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class OAuthService extends BaseService<String> {

	private Map<String, String> cache = new HashMap<>();

	@Inject
	private ClientService clientService;

	public void addAuthCode(String authCode, String username) {
		cache.put(authCode, username);
	}

	public void addAccessToken(String accessToken, String username) {
		cache.put(accessToken, username);
	}

	public String getUsernameByAuthCode(String authCode) {
		return cache.get(authCode);
	}

	public String getUsernameByAccessToken(String accessToken) {
		return cache.get(accessToken);
	}

	public boolean checkAuthCode(String authCode) {
		return cache.get(authCode) != null;
	}

	public boolean checkAccessToken(String accessToken) {
		return cache.get(accessToken) != null;
	}

	public boolean checkClientId(String clientId) {
		return clientService.findByClientId(clientId) != null;
	}

	public boolean checkClientSecret(String clientSecret) {
		return clientService.findByClientSecret(clientSecret) != null;
	}

	public long getExpireIn() {
		return 3600L;
	}
}
