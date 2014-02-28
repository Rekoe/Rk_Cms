package com.rekoe.module;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.view.RawView;
import org.nutz.mvc.view.ViewWrapper;
import org.nutz.mvc.view.VoidView;

import com.rekoe.service.OAuthService;
import com.rekoe.utils.Constants;

@IocBean
@Filters
public class AccessTokenModule {

	@Inject
	private OAuthService oAuthService;

	@At("/accessToken")
	public View token(HttpServletRequest request, HttpServletResponse resp) throws URISyntaxException, OAuthSystemException {
		try {
			// 构建OAuth请求
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
			// 检查提交的客户端id是否正确
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
				resp.setHeader("Location", response.getLocationUri());
				resp.setStatus(response.getResponseStatus());
				return new VoidView();
			}
			// 检查客户端安全KEY是否正确
			if (!oAuthService.checkClientSecret(oauthRequest.getClientSecret())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
				resp.setHeader("Location", response.getLocationUri());
				resp.setStatus(response.getResponseStatus());
				return new VoidView();
			}
			String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
			// 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!oAuthService.checkAuthCode(authCode)) {
					OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription("错误的授权码").buildJSONMessage();
					resp.setHeader("Location", response.getLocationUri());
					resp.setStatus(response.getResponseStatus());
					return new VoidView();
				}
			}

			// 生成Access Token
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			oAuthService.addAccessToken(accessToken, oAuthService.getUsernameByAuthCode(authCode));
			// 生成OAuth响应
			OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setExpiresIn(String.valueOf(oAuthService.getExpireIn())).buildJSONMessage();
			resp.setHeader("Location", response.getLocationUri());
			resp.setStatus(response.getResponseStatus());
			return new ViewWrapper(new RawView("application/json"), response.getBody());

		} catch (OAuthProblemException e) {
			e.printStackTrace();
			// 构建错误响应
			OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
			resp.setHeader("Location", response.getLocationUri());
			resp.setStatus(response.getResponseStatus());
			return new VoidView();
		}
	}
}
