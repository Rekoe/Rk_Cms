package com.rekoe.shiro.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.brickred.socialauth.Profile;
import org.nutz.lang.Lang;

import com.rekoe.domain.User;

public class NutAuthoDaoRealm extends AbstractNutAuthoRealm {

	public NutAuthoDaoRealm() {
		setAuthenticationTokenClass(OAuthToken.class);
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws DisabledAccountException {
		OAuthToken oauthToken = (OAuthToken) token;
		Profile credential = oauthToken.getCredentials();
		String openid = credential.getValidatedId();
		User user = getUserService().fetchByOpenID(openid);
		if (Lang.isEmpty(user)) {
			boolean isUpdated = StringUtils.isNotBlank(credential.getDisplayName());
			String nickName = StringUtils.defaultString(credential.getDisplayName(), openid);
			String providerid = credential.getProviderId();
			user = getUserService().initUser(nickName, openid, providerid, oauthToken.getAddr(), isUpdated);
		}
		if (user.isLocked()) {
			throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", user.getName());
		}
		oauthToken.setRname(!user.isUpdated());
		oauthToken.setUserId(openid);
		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user, credential, getName());
		oauthToken.getSession().setAttribute(org.nutz.web.Webs.ME, user);
		return account;
	}
}
