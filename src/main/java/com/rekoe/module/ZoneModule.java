package com.rekoe.module;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.VoidView;

import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;

@IocBean
@At("/app")
public class ZoneModule {

	private final static Log log = Logs.get();
	private final String OAUTH_ID = "facebook";
	private final String FB_URL = "http://apps.facebook.com/";
	private final String FB_TYPE_USER = "me";
	@Inject
	private com.rekoe.socialauth.SocialAuthConfig socialAuthConfig;

	// https://github.com/revetkn/restfb

	@At
	@Ok("fm:template.front.zone.login")
	public Object login(@Param("signed_request") String code, @Param("request_ids") String[] requestIds, HttpServletRequest req, HttpServletResponse res) throws Exception {
		boolean isRight = true;
		isRight = StringUtils.isNotBlank(code);
		com.rekoe.socialauth.OAuthConfig oAuthConfig = socialAuthConfig.getProviderConfig(OAUTH_ID);
		String appId = oAuthConfig.get_consumerKey();
		if (isRight) {
			try {
				FacebookSignedRequest facebookSR = FacebookSignedRequest.getFacebookSignedRequest(code, FacebookSignedRequest.class);
				String accessToken = facebookSR.getOauth_token();
				if (StringUtils.isBlank(accessToken)) {
					throw new AccountException(String.format("neet user reload >> code:%s \n-------------\n data %s ", code, Json.toJson(facebookSR, JsonFormat.compact())));
				}
				DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken);
				User account = facebookClient.fetchObject(FB_TYPE_USER, User.class);
			} catch (Exception e) {
				isRight = false;
				log.error(e.getMessage());
			}
		}
		if (!isRight) {
			String namespace = oAuthConfig.get_consumerNameSpace();
			String url = "http://www.facebook.com/dialog/oauth?client_id=" + appId + "&scope=email,publish_actions,publish_stream&redirect_uri=" + FB_URL + namespace;
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			writer.print("<script> top.location.href='" + url + "'</script>");
			writer.close();
			return new VoidView();
		}else{
			res.addHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
		}
		return null;
	}
}
