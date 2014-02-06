package com.rekoe.module;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.mvc.annotation.Attr;

import com.rekoe.exception.IncorrectCaptchaException;

public class LoginModule {

	/**
	 * 本地登录
	 * 
	 * @param token
	 */
	public void localLogin(@Attr("loginToken") AuthenticationToken token) {
		Subject currentUser = SecurityUtils.getSubject();
		ThreadContext.bind(currentUser);
		try {
			currentUser.login(token); // 这里的授权,请查看shiro.ini中的nutRealm
			// System.out.println(currentUser.isPermitted("gameuser:update:addkick"));
		} catch (IncorrectCaptchaException e) {
			//System.out.println("验证码错误");
		} catch (UnknownAccountException uae) {
			// System.out.println("帐号不存在 帮助用户创建 ，并提示用户为新用户");
			// return new ViewWrapper(new ForwardView("/login/index"), "帐号不存在");
		} catch (IncorrectCredentialsException ice) {
			// System.out.println("证书验证失败");
		} catch (LockedAccountException lae) {
			// System.out.println("帐号已被锁定");
			// return new ViewWrapper(new ForwardView("/login/index"),
			// "帐号已被锁定");
		} catch (ExcessiveAttemptsException eae) {
			// System.out.println("尝试的次数太多");
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			// System.out.println("密码错误或用户不存在");
		}
	}
}
