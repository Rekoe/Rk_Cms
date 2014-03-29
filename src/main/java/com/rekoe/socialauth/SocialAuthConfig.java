package com.rekoe.socialauth;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.log.Logs;

@IocBean
public class SocialAuthConfig implements Serializable {
	private static final long serialVersionUID = 5445308327297705055L;
	private Map<String, String> domainMap = new HashMap<String, String>();
	private Map<String, OAuthConfig> providersConfig = new HashMap<String, OAuthConfig>();

	public void load() {
		Properties p = new Properties();
		try {
			File devConfig = Files.findFile("app_oauth_consumer.properties_dev"); // 开发期所使用的配置文件
			if (devConfig == null)
				devConfig = Files.findFile("app_oauth_consumer.properties"); // 真实环境所使用的配置文件
			if (devConfig == null)
				throw new NullPointerException(String.format("the file %s is not found", "app_oauth_consumer.properties"));
			else
				p.load(new FileInputStream(devConfig));
			registerProviders(p);
			loadProvidersConfig(p);
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private void registerProviders(Properties p) throws Exception {
		for (Object key : p.keySet()) {
			String str = key.toString();
			String val = StringUtils.substringBefore(str, ".");
			domainMap.put(val, val);
		}
	}

	private void loadProvidersConfig(Properties p) {
		for (Map.Entry<String, String> entry : domainMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String cKey = p.getProperty(value + ".consumer_key");
			String cSecret = p.getProperty(value + ".consumer_secret");
			String cNameSpace = p.getProperty(value + ".consumer_namespace");
			String cPaykey = p.getProperty(value + ".consumer_pay");
			String url = p.getProperty(value + ".consumer_url");
			String login_err = p.getProperty(value + ".consumer_login_err");
			if (cKey != null && cSecret != null) {
				Logs.get().debug("Loading configuration for provider : " + key);
				OAuthConfig conf = new OAuthConfig(cKey, cSecret, cNameSpace, cPaykey, url, login_err);
				conf.setId(key);
				// conf.setProviderImplClass(providersImplMap.get(key));
				providersConfig.put(key, conf);
			} else {
				Logs.get().debug("Configuration for provider " + key + " is not available");
			}
		}
	}

	public OAuthConfig getProviderConfig(final String id) throws Exception {
		OAuthConfig config = providersConfig.get(id);
		if (Lang.isEmpty(config)) {
			throw new Exception("Configuration of " + id + " provider is not found");
		}
		if (config.get_consumerSecret().length() <= 0) {
			throw new Exception(id + " consumer_secret value is null");
		}
		if (config.get_consumerKey().length() <= 0) {
			throw new Exception(id + " consumer_key value is null");
		}
		return config;
	}
}
