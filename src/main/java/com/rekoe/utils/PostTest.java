package com.rekoe.utils;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

public class PostTest {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException {
		//DirectTokenSampleClientOAuth();
		AuthCodeSampleClientOauth();
	}

	public static void DirectTokenSampleClientOAuth() throws OAuthSystemException, OAuthProblemException {
		TokenRequestBuilder builder = OAuthClientRequest.tokenLocation(ClientParams.OAUTH_SERVER_URL + "accessToken.rk");
		OAuthClientRequest request = builder.setGrantType(GrantType.PASSWORD).setClientId(ClientParams.CLIENT_ID).setClientSecret(ClientParams.CLIENT_SECRET).setUsername(ClientParams.USERNAME).setPassword(ClientParams.PASSWORD).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
		System.out.println("access token: " + oauthResponse.getAccessToken());
		System.out.println("expira em: " + oauthResponse.getExpiresIn());
	}

	public static void AuthCodeSampleClientOauth() throws OAuthSystemException, OAuthProblemException {
		OAuthClientRequest request = OAuthClientRequest.authorizationLocation(ClientParams.OAUTH_SERVER_URL + "authorize.rk").setClientId(ClientParams.CLIENT_ID).setRedirectURI(ClientParams.OAUTH_SERVER_URL + "redirect.rk").setResponseType(ResponseType.CODE.toString()).setState("state").buildQueryMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
		System.out.println("access token: " + oauthResponse.getAccessToken());
		System.out.println("expira em: " + oauthResponse.getExpiresIn());

	}
}
