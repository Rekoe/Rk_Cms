package com.rekoe.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
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
			String nickName = credential.getDisplayName();
			String providerid = credential.getProviderId();
			user = getUserService().initUser(nickName, openid, providerid, oauthToken.getAddr());
			throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", user.getName());
		}
		if (user.isLocked()) {
			throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", user.getName());
		}
		oauthToken.setUserId(openid);
		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user, credential, getName());// (principalCollection,
		Subject obj = SecurityUtils.getSubject();
		obj.getSession().setAttribute(org.nutz.web.Webs.ME, user);
		return account;
	}
}
