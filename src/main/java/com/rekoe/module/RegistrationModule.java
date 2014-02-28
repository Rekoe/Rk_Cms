package com.rekoe.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import com.rekoe.utils.ServerContent;

@IocBean
public class RegistrationModule {

	@POST
	@At
	@Ok("oauth")
	public Object register(HttpServletRequest request) throws OAuthSystemException {
		OAuthServerRegistrationRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));
			oauthRequest.discover();
			oauthRequest.getClientName();
			oauthRequest.getClientUrl();
			oauthRequest.getClientDescription();
			oauthRequest.getRedirectURI();
			OAuthResponse response = OAuthServerRegistrationResponse.status(HttpServletResponse.SC_OK).setClientId(ServerContent.CLIENT_ID).setClientSecret(ServerContent.CLIENT_SECRET).setIssuedAt(ServerContent.ISSUED_AT).setExpiresIn(ServerContent.EXPIRES_IN).buildJSONMessage();
			//return new ViewWrapper(new RawView("application/json"),response.getBody());
			return response.getBody();
		} catch (OAuthProblemException e) {
			OAuthResponse response = OAuthServerRegistrationResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
			//return new ViewWrapper(new RawView("application/json"),response.getBody());
			return response.getBody();
		}
	}
}