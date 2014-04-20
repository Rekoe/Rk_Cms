package com.rekoe.cms.socialauth.github;

import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.ServerDataException;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.oauthstrategy.OAuth2;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.nutz.json.Json;

import com.rekoe.cms.socialauth.AbstractOAuthProvider;

/**
 * 实现Github帐号登录,OAuth2
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class GithubOAuthProvider extends AbstractOAuthProvider {

	public GithubOAuthProvider(final OAuthConfig providerConfig) throws Exception {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL, "https://github.com/login/oauth/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL, "https://github.com/login/oauth/access_token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());
		PROFILE_URL = "https://api.github.com/user";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;

		try {
			Response response = authenticationStrategy.executeFeed(PROFILE_URL);
			presp = response.getResponseBodyAsString(Constants.ENCODING);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from " + PROFILE_URL, e);
		}
		try {
			Map<String, Object> data = Json.fromJson(Map.class, presp);
			if (!data.containsKey("id"))
				throw new SocialAuthException("Error: " + presp);
			if (userProfile == null)
				userProfile = new Profile();
			userProfile.setValidatedId(data.get("id").toString());
			userProfile.setProviderId(getProviderId());
			// System.out.println(Json.toJson(data));
			if (data.get("email") != null)
				userProfile.setValidatedId(data.get("email").toString());
			if (data.get("avatar_url") != null)
				userProfile.setProfileImageURL(data.get("avatar_url").toString());
			if (data.get("name") != null)
				userProfile.setDisplayName(data.get("name").toString());
			return userProfile;

		} catch (Exception ex) {
			throw new ServerDataException("Failed to parse the user profile json : " + presp, ex);
		}
	}

	@Override
	protected String verifyResponseMethod() {
		return "POST";
	}
}