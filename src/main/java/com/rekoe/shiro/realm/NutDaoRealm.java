package com.rekoe.shiro.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.util.ByteSource;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import com.rekoe.domain.User;
import com.rekoe.exception.CreateUserSaltException;
import com.rekoe.exception.IncorrectCaptchaException;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class NutDaoRealm extends AbstractNutAuthoRealm {

	public NutDaoRealm() {
		setAuthenticationTokenClass(CaptchaUsernamePasswordToken.class);
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws DisabledAccountException {
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
		String userSalt = user.getSalt();
		if (Strings.isBlank(userSalt)) {
			throw Lang.makeThrow(CreateUserSaltException.class, "Account [ %s ] is not set PassWord", authcToken.getUsername());
		}
		ByteSource salt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		info.setCredentialsSalt(salt);
		return info;
	}
}
