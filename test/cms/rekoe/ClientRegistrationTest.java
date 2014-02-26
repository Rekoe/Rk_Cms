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

package cms.rekoe;

import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.ext.dynamicreg.client.OAuthRegistrationClient;
import org.apache.oltu.oauth2.ext.dynamicreg.client.request.OAuthClientRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.utils.ClientContent;

/**
 *
 *
 *
 */
public class ClientRegistrationTest {

	private final static Log log = Logs.get();

	public static void main(String[] args) throws Exception {
		ClientRegistrationTest test = new ClientRegistrationTest();
		test.testInvalidType();
	}
	public void testPushMetadataRegistration() throws Exception {
		OAuthClientRequest request = OAuthClientRegistrationRequest.location(ClientContent.REGISTRATION_ENDPOINT, OAuthRegistration.Type.PUSH).setName(ClientContent.APP_NAME).setUrl(ClientContent.APP_URL).setDescription(ClientContent.APP_DESCRIPTION).setIcon(ClientContent.APP_ICON).setRedirectURL(ClientContent.APP_REDIRECT_URI).buildJSONMessage();
		OAuthRegistrationClient oauthclient = new OAuthRegistrationClient(new URLConnectionClient());
		OAuthClientRegistrationResponse response = oauthclient.clientInfo(request);
		log.info(response.getClientId());
		log.info(response.getClientSecret());
		log.info(response.getExpiresIn());
		log.info(response.getIssuedAt());
	}

	public void testInvalidType() throws Exception {
		OAuthClientRequest request = OAuthClientRegistrationRequest.location(ClientContent.REGISTRATION_ENDPOINT, "unknown_type").setName(ClientContent.APP_NAME).setUrl(ClientContent.APP_URL).setDescription(ClientContent.APP_DESCRIPTION).setIcon(ClientContent.APP_ICON).setRedirectURL(ClientContent.APP_REDIRECT_URI).buildBodyMessage();
		OAuthRegistrationClient oauthclient = new OAuthRegistrationClient(new URLConnectionClient());
		try {
			OAuthClientRegistrationResponse response = oauthclient.clientInfo(request);
			response.getIssuedAt();
		} catch (OAuthProblemException e) {
			log.info(e.getError());
		}
	}
}
