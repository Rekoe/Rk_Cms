package com.rekoe.module;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

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
			if (StringUtils.isBlank(code)) {
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
				log.error("topZone is null");
				throw Lang.makeThrow(Exception.class, "not find server");
			}
			User user = facebookClient.fetchObject(FB_TYPE_USER, User.class);
			com.rekoe.domain.Account account = accountService.fetch(user.getId());
			Date now = Times.now();
			if (Lang.isEmpty(account)) {
				account = new com.rekoe.domain.Account();
				account.setCreateTime(now);
				account.setLock(false);
				account.setModifyTime(now);
				account.setPassportid(user.getId());
				account.setRegisterIp(req.getRemoteAddr());
				account.setLastZoneid(topZone.getId());
				account.setZoneLog("[\"" + account.getLastZoneid() + "\"]");
				accountService.insert(account);
			} else {
				account.setModifyTime(now);
				accountService.update(account);
			}
			accountService.addCache(user.getId(), new FacebookAccount(code, requestIds, user, account));
			res.addHeader("P3P", "CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
			session.setAttribute("accountID", user.getId());
			return new ForwardView("/app/" + user.getId() + "/myhome.rk");
		} catch (AccountException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			return new ViewWrapper(new ForwardView("/admin/common/unauthorized.rk"), e.getMessage());
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
	public Object myhome(String passportid, @Attr("accountID") String id, HttpServletResponse res) throws Exception {
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
		Zone zone = zoneService.fetch(account.getAccount().getLastZoneid());
		return zone;
	}

	@At("/SelectServer")
	@Ok("fm:template.front.zone.list")
	public Zone zoneList(@Attr("accountID") String passportid, HttpServletRequest req) {
		FacebookAccount account = accountService.getFacebookAccount(passportid);
		req.setAttribute("zones", zoneService.getZoneList());
		req.setAttribute("top", zoneService.getTopZone());
		req.setAttribute("accountID", passportid);
		return zoneService.fetch(account.getAccount().getLastZoneid());
	}

	@At
	@Ok("fm:template.front.zone.test")
	public Zone redirect(@Param("id") String id, @Attr("accountID") String accountID, HttpServletRequest req, HttpServletResponse res) {
		res.addHeader("P3P", "CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
		FacebookAccount account = accountService.getFacebookAccount(accountID);
		req.setAttribute("account", account);
		if (StringUtils.isBlank(account.getAccount().getZoneLog())) {
			account.getAccount().setZoneLog("[]");
		}
		List<String> zoneHistory = Json.fromJsonAsList(String.class, account.getAccount().getZoneLog());
		int len = zoneHistory.size();
		if (len >= 5) {
			zoneHistory.remove(len - 1);
		}
		if (zoneHistory.contains(id)) {
			zoneHistory.remove(id);
		}
		if (Lang.isEmpty(zoneHistory)) {
			zoneHistory.add(id);
		} else {
			zoneHistory.set(0, id);
		}
		account.getAccount().setLastZoneid(id);
		account.getAccount().setZoneLog(Json.toJson(zoneHistory, JsonFormat.compact()));
		accountService.update(account.getAccount());
		accountService.removeCache(accountID);
		return zoneService.fetch(id);
	}

	@At
	@Ok("fm:template.front.zone.local")
	public String local(HttpServletRequest req) {
		String url = "https://s1koruyucu.oasgames.com/facebook/login";
		req.setAttribute("url", url);
		String code = "uzU3BhI9PYgjsOEbPeaS8wBiJ1MMyYMqVnBzp63DR8s.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImV4cGlyZXMiOjEzOTYzMzU2MDAsImlzc3VlZF9hdCI6MTM5NjMzMTA3NSwib2F1dGhfdG9rZW4iOiJDQUFLVjBpRGZFRWdCQUJXVHI2MTNaQmxFSnRsRVlZbGRuNmtpanlEVlhROUQ1YW4zNUoyN2VxYTdJZENlZm5NQm1pajRmRnF3Y1ROVmpUOGx6VFpBOHdzemp1TGJqRFpBM29JWXg1VEFWWkNXdTdVQkZBMzhPYU1WaGVuV1pDWkFmVW5hYnQ0Y1d6RlVwZ1pCcExpdThEcFcxamlvd3BDbUQ2RjA4cTZjcG5kZjByeGJNS2s5UFNhWkFJd3c3enZaQVpBQ3AzYjFibTFyMnFYd1pEWkQiLCJ1c2VyIjp7ImNvdW50cnkiOiJ1cyIsImxvY2FsZSI6InpoX0NOIiwiYWdlIjp7Im1pbiI6MjF9fSwidXNlcl9pZCI6IjEwMDAwMTcyODU3OTg1OCJ9";
		return code;
	}
}
