/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rekoe.cms.authorize.test;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.utils.TestContent;

/**
 *
 *
 *
 */
public class AccessTokenTestAuthCodeTest{

	private final static Log log = Logs.get();
	public static void main(String[] args) throws Exception {
		AccessTokenTestAuthCodeTest test = new AccessTokenTestAuthCodeTest();
		test.testSuccessfullAccesToken();
	}
	public void testSuccessfullAccesToken() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setGrantType(GrantType.AUTHORIZATION_CODE).setCode(TestContent.AUTHORIZATION_CODE).setRedirectURI(TestContent.REDIRECT_URL).setClientId(TestContent.CLIENT_ID).setClientSecret(TestContent.CLIENT_SECRET).buildBodyMessage();
		log.info(request.getLocationUri());
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthAccessTokenResponse response = oAuthClient.accessToken(request, OAuth.HttpMethod.GET);
		log.info(response.getAccessToken());
		log.info(response.getExpiresIn());
	}

	public void testSuccessfullAccesTokenGETMethod() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setGrantType(GrantType.AUTHORIZATION_CODE).setCode(TestContent.AUTHORIZATION_CODE).setRedirectURI(TestContent.REDIRECT_URL).setClientId(TestContent.CLIENT_ID).setClientSecret(TestContent.CLIENT_SECRET).buildQueryMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthAccessTokenResponse response = oAuthClient.accessToken(request, OAuth.HttpMethod.GET);
		log.info(response.getAccessToken());
		log.info(response.getExpiresIn());

	}

	public void testNoneGrantType() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setGrantType(null).setClientId(TestContent.CLIENT_ID).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		try {
			oAuthClient.accessToken(request);
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}
	}

	public void testInvalidRequest() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setClientId(TestContent.CLIENT_ID).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		try {
			oAuthClient.accessToken(request);
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}
	}

	public void testInvalidClient() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setGrantType(GrantType.AUTHORIZATION_CODE).setCode(TestContent.AUTHORIZATION_CODE).setClientId("unknownid").setRedirectURI(TestContent.REDIRECT_URL).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		try {
			oAuthClient.accessToken(request);
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}
	}

	public void testInvalidGrantType() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setParameter(OAuth.OAUTH_GRANT_TYPE, "unknown_grant_type").setCode(TestContent.AUTHORIZATION_CODE).setRedirectURI(TestContent.REDIRECT_URL).setClientId(TestContent.CLIENT_ID).buildBodyMessage();
		OAuthClient oAuthclient = new OAuthClient(new URLConnectionClient());
		try {
			oAuthclient.accessToken(request);
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}

	}

	public void testInvalidCode() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(TestContent.ACCESS_TOKEN_ENDPOINT).setGrantType(GrantType.AUTHORIZATION_CODE).setRedirectURI(TestContent.REDIRECT_URL).setCode("unknown_code").setClientId(TestContent.CLIENT_ID).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		try {
			oAuthClient.accessToken(request);
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}
	}
}