package com.rekoe.shiro;

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

import com.rekoe.cms.socialauth.OAuthToken;
import com.rekoe.domain.User;

/**
 * 用NutDao来实现Shiro的Realm
 * <p/> 可以通过配置文件注入数据源
 * <p/> 在Web环境中也可以通过自动搜索来获取NutDao
 * @author wendal
 *
 */
public class NutAuthoDaoRealm extends AbstractNutAuthoRealm {

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws DisabledAccountException {
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
        SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user, credential,  getName());//(principalCollection, credential);		
        Subject obj = SecurityUtils.getSubject();
        obj.getSession().setAttribute(org.nutz.web.Webs.ME, user);
        return account;
	}
}
