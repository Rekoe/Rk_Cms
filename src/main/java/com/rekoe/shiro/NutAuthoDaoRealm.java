package com.rekoe.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.brickred.socialauth.Profile;
import org.nutz.lang.Lang;

import com.rekoe.domain.User;
import com.rekoe.exception.IncorrectCaptchaException;

/**
 * 用NutDao来实现Shiro的Realm
 * <p/>
 * 可以通过配置文件注入数据源
 * <p/>
 * 在Web环境中也可以通过自动搜索来获取NutDao
 * 
 * @author wendal
 * 
 */
public class NutAuthoDaoRealm extends AbstractNutAuthoRealm {

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws DisabledAccountException {
		if (token instanceof OAuthToken) {
			OAuthToken oauthToken = (OAuthToken) token;
			Profile credential = oauthToken.getCredentials();
			String openid = credential.getValidatedId();
			User user = getUserService().fetchByOpenID(openid);
			if (Lang.isEmpty(user)) {
				String nickName = credential.getDisplayName();
				String providerid = credential.getProviderId();
				user = getUserService().initUser(nickName, openid, providerid, oauthToken.getAddr());
				throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", openid);
			}
			if (user.isLocked()) {
				throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", openid);
			}
			oauthToken.setUserId(openid);
			SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user, credential, getName());// (principalCollection,
			Subject obj = SecurityUtils.getSubject();
			obj.getSession().setAttribute(org.nutz.web.Webs.ME, user);
			return account;
		} else if (token instanceof CaptchaUsernamePasswordToken) {
			CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
			String accountName = authcToken.getUsername();
			if (StringUtils.isBlank(accountName)) {
				throw Lang.makeThrow(AuthenticationException.class, "Account is empty");
			}
			boolean isCaptchaBlank = StringUtils.isBlank(authcToken.getCaptcha());
			if (isCaptchaBlank) {
				throw Lang.makeThrow(IncorrectCaptchaException.class, "验证码不可以为空!");
			}
			String sessionID = SecurityUtils.getSubject().getSession(true).getId().toString();
			boolean isRight = getImageCaptchaService().validateResponseForID(sessionID, StringUtils.upperCase(authcToken.getCaptcha()));
			if (!isRight) {
				throw Lang.makeThrow(IncorrectCaptchaException.class, "验证码错误!");
			}
			User user = getUserService().fetchByName(authcToken.getUsername());
			if (Lang.isEmpty(user)) {
				throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", authcToken.getUsername());
			}
			if (user.isLocked()) {
				throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", authcToken.getUsername());
			}
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			ByteSource salt = ByteSource.Util.bytes(user.getSalt());
			info.setCredentialsSalt(salt);
			return info;
		} else {
			UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
			String accountName = authcToken.getUsername();
			if (StringUtils.isBlank(accountName)) {
				throw Lang.makeThrow(AuthenticationException.class, "Account is empty");
			}
			User user = getUserService().fetchByName(authcToken.getUsername());
			if (Lang.isEmpty(user)) {
				throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", authcToken.getUsername());
			}
			if (user.isLocked()) {
				throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", authcToken.getUsername());
			}
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			ByteSource salt = ByteSource.Util.bytes(user.getSalt());
			info.setCredentialsSalt(salt);
			return info;
		}
	}
}
