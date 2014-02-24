package com.rekoe.module;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.ViewWrapper;

import com.rekoe.exception.IncorrectCaptchaException;
import com.rekoe.filter.AuthenticationFilter;

@IocBean
public class LoginModule {

	/**
	 * 本地登录
	 * 
	 * @param token
	 */
	@At("/oauth2login")
	@Filters(@By(type = AuthenticationFilter.class))
	public View localLogin(@Attr("loginToken") AuthenticationToken token) {
		try {
			Subject subject = SecurityUtils.getSubject();
			ThreadContext.bind(subject);
			subject.login(token);
			return new ServerRedirectView("/admin/main.rk");
		} catch (IncorrectCaptchaException e) {
			return new ViewWrapper(new ForwardView("/admin/index.rk"), e.getMessage());
		} catch (LockedAccountException e) {
			Map<String, Object> msgs = Mvcs.getLocaleMessage("zh_CN");
			String errMsg = msgs.get("admin.login.lockedAccount").toString();
			return new ViewWrapper(new ForwardView("/admin/index.rk"), errMsg);
		} catch (AuthenticationException e) {
			Map<String, Object> msgs = Mvcs.getLocaleMessage("zh_CN");
			String errMsg = msgs.get("login_error").toString();
			return new ViewWrapper(new ForwardView("/admin/index.rk"), errMsg);
		} catch (Exception e) {
			return new ViewWrapper(new ForwardView("/admin/index.rk"), e.getMessage());
		}
	}
}
