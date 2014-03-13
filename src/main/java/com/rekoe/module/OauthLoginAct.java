package com.rekoe.module;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.stream.NullInputStream;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.ViewWrapper;

import com.rekoe.shiro.OAuthToken;

@IocBean(create = "init")
@At("/user")
@Filters
public class OauthLoginAct {

	// 需要登录之后才能访问,否则跳转到首页
	@RequiresAuthentication
	@At
	public Object authOnly() {
		return "You are authed!";
	}

	/* 提供社会化登录 */
	@At("/login/?")
	@Ok("void")
	public void login(String provider, HttpSession session, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String returnTo = req.getRequestURL().toString() + "/callback";
		if (req.getParameterMap().size() > 0) {
			StringBuilder sb = new StringBuilder().append(returnTo).append("?");
			for (Object name : req.getParameterMap().keySet()) {
				sb.append(name).append('=').append(URLEncoder.encode(req.getParameter(name.toString()), Encoding.UTF8)).append("&");
			}
			returnTo = sb.toString();
		}
		SocialAuthManager manager = new SocialAuthManager(); // 每次都要新建哦
		manager.setSocialAuthConfig(config);
		session.setAttribute("openid.manager", manager);
		String url = manager.getAuthenticationUrl(provider, returnTo);
		res.setHeader("Location", url);
		res.setStatus(302);
	}

	// 没登录就不要登出了
	@RequiresAuthentication
	@At("/logout")
	@Ok(">>:/admin/index.rk")
	public void logout(HttpSession session) {
		// session.invalidate(); //销毁会话,啥都米有了
		SecurityUtils.getSubject().logout();
	}

	/* 无需做链接,这是OpenID的回调地址 */
	@RequiresGuest
	@At("/login/?/callback")
	public View returnPoint(String providerId, HttpServletRequest request, HttpSession session) throws Exception {
		SocialAuthManager manager = (SocialAuthManager) session.getAttribute("openid.manager");
		if (manager == null)
			throw new SocialAuthException("Not manager found!");
		session.removeAttribute("openid.manager"); // 防止重复登录的可能性
		Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request);
		AuthProvider provider = manager.connect(paramsMap);
		Profile p = provider.getUserProfile();
		Subject currentUser = SecurityUtils.getSubject();
		ThreadContext.bind(currentUser);
		OAuthToken token = new OAuthToken(p,request.getRemoteAddr());
		try {
			currentUser.login(token); // 这里的授权,请查看shiro.ini中的nutRealm
		} catch (UnknownAccountException uae) {
			return new ViewWrapper(new ForwardView("/login/index"), "帐号不存在");
		} catch (IncorrectCredentialsException ice) {
		} catch (LockedAccountException lae) {
			return new ViewWrapper(new ForwardView("/login/index"), "帐号已被锁定");
		} catch (ExcessiveAttemptsException eae) {
			return new ViewWrapper(new ForwardView("/"), eae.getMessage());
		} catch (AuthenticationException ae) {
			return new ViewWrapper(new ForwardView("/"), ae.getMessage());
		}
		return new ViewWrapper(new ServerRedirectView("/admin/index"), null);
	}

	private SocialAuthConfig config;

	public void init() throws Exception {
		SocialAuthConfig config = new SocialAuthConfig();
		File devConfig = Files.findFile("oauth_consumer.properties_dev"); // 开发期所使用的配置文件
		if (devConfig == null)
			devConfig = Files.findFile("oauth_consumer.properties"); // 真实环境所使用的配置文件
		if (devConfig == null)
			config.load(new NullInputStream());
		else
			config.load(new FileInputStream(devConfig));
		this.config = config;
	}

}
