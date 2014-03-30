package com.rekoe.module;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.ViewWrapper;
import org.nutz.mvc.view.VoidView;

import com.rekoe.domain.Account;
import com.rekoe.domain.FacebookAccount;
import com.rekoe.domain.Zone;
import com.rekoe.service.AccountService;
import com.rekoe.service.ZoneService;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;

@IocBean
@At("/app")
@Filters
public class ZoneModule {

	private final static Log log = Logs.get();
	private final String OAUTH_ID = "facebook";
	private final String FB_URL = "http://apps.facebook.com/";
	private final String FB_TYPE_USER = "me";
	@Inject
	private com.rekoe.socialauth.SocialAuthConfig socialAuthConfig;

	// https://github.com/revetkn/restfb

	@Inject
	private AccountService accountService;
	@Inject
	private ZoneService zoneService;

	@At
	public Object login(@Param("signed_request") String code, @Param("request_ids") String[] requestIds, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		com.rekoe.socialauth.OAuthConfig oAuthConfig = socialAuthConfig.getProviderConfig(OAUTH_ID);
		String appId = oAuthConfig.get_consumerKey();
		try {
			if (StringUtils.isNotBlank(code)) {
				throw Lang.makeThrow(AccountException.class, "code null");
			}
			FacebookSignedRequest facebookSR = FacebookSignedRequest.getFacebookSignedRequest(code, FacebookSignedRequest.class);
			String accessToken = facebookSR.getOauth_token();
			if (StringUtils.isBlank(accessToken)) {
				throw new AccountException(String.format("neet user reload >> code:%s \n-------------\n data %s ", code, Json.toJson(facebookSR, JsonFormat.compact())));
			}
			DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken);
			Zone topZone = zoneService.getTopZone();
			if (Lang.isEmpty(topZone)) {
				throw Lang.makeThrow(Exception.class, "not find server");
			}
			User user = facebookClient.fetchObject(FB_TYPE_USER, User.class);
			Account account = accountService.fetch(user.getId());
			Date now = Times.now();
			if (Lang.isEmpty(account)) {
				account = new Account();
				account.setCreateTime(now);
				account.setLock(false);
				account.setModifyTime(now);
				account.setPassportid(user.getId());
				account.setRegisterIp(req.getRemoteAddr());
				account.setZoneLog(topZone.getId());
				account.setLastZoneid("[" + topZone.getId() + "]");
				accountService.insert(account);
			} else {
				account.setModifyTime(now);
				accountService.update(account);
			}
			accountService.addCache(user.getId(), new FacebookAccount(code, requestIds, user, account));
			req.setAttribute("id", user.getId());
			res.addHeader("P3P", "CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
			session.setAttribute("accountID", user.getId());
			return new ForwardView("/app/" + user.getId() + "/myhome.rk");
		} catch (AccountException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			return new ViewWrapper(new ForwardView("/common/error.rk"), e.getMessage());
		}
		String namespace = oAuthConfig.get_consumerNameSpace();
		String url = "http://www.facebook.com/dialog/oauth?client_id=" + appId + "&scope=email,publish_actions,publish_stream&redirect_uri=" + FB_URL + namespace;
		res.setContentType("text/html");
		PrintWriter writer = res.getWriter();
		writer.print("<script> top.location.href='" + url + "'</script>");
		writer.close();
		return new VoidView();

	}

	@At("/?/myhome")
	@Ok("fm:template.front.zone.login")
	public Object myhome(String passportid, @Param("id") String id, HttpServletResponse res, HttpServletRequest req) throws Exception {
		if (StringUtils.isBlank(id) || !Lang.equals(id, passportid)) {
			com.rekoe.socialauth.OAuthConfig oAuthConfig = socialAuthConfig.getProviderConfig(OAUTH_ID);
			String appId = oAuthConfig.get_consumerKey();
			String namespace = oAuthConfig.get_consumerNameSpace();
			String url = "http://www.facebook.com/dialog/oauth?client_id=" + appId + "&scope=email,publish_actions,publish_stream&redirect_uri=" + FB_URL + namespace;
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			writer.print("<script> top.location.href='" + url + "'</script>");
			writer.close();
			return new VoidView();
		}
		FacebookAccount account = accountService.getFacebookAccount(passportid);
		return account.getAccount();
	}

	// http://app100615799.openwebgame.qq.com/SelectServer/qzone?appid=100615799&sFrom=qzone&qz_height=1200&openid=AD71DFFC753C720D348F19F67798BCE5&openkey=12D22ADD6465039F89BEE50275B4B7D04DBB055B86402322183F7B2AF03B3853D0D6A0C697A80AE69F75FFF6310FE4D44ED1B8E7921A06BE6C294F369382D87A9A291562B55C41062050CCEF243330429F671DF4A9D0E1C8&pf=qzone&pfkey=46d4119c4b0b20b72c36e14fced4454e&qz_ver=8&appcanvas=1&qz_style=35&params=
	@At("/SelectServer")
	@Ok("fm:template.front.zone.list")
	public Object zoneList(@Attr("accountID") String passportid, HttpServletRequest req) {
		FacebookAccount account = accountService.getFacebookAccount(passportid);
		req.setAttribute("list", zoneService.getZoneList());
		req.setAttribute("freshList", zoneService.getTopZone());
		return account.getAccount();
	}

	@At
	@Ok("fm:template.front.zone.test")
	public FacebookAccount redrict(@Attr("accountID") String passportid) {
		return accountService.getFacebookAccount(passportid);
	}
}
