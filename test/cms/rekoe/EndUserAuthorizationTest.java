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

import java.net.HttpURLConnection;
import java.util.Map;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.utils.TestContent;

/**
 *
 *
 *
 */
public class EndUserAuthorizationTest {

	private final static Log log = Logs.get();

	public static void main(String[] args) throws Exception {
		EndUserAuthorizationTest test = new EndUserAuthorizationTest();
		test.testTokenResponse();
	}
	public void testWrongParametersEndUserAuthorization() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.authorizationLocation(TestContent.AUTHORIZATION_ENPOINT).setClientId(TestContent.CLIENT_ID).setRedirectURI(TestContent.REDIRECT_URL).buildQueryMessage();
		TestContent.doRequest(request);
	}

	public void testCorrectParametersEndUserAuthorization() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.authorizationLocation(TestContent.AUTHORIZATION_ENPOINT).setClientId(TestContent.CLIENT_ID).setRedirectURI(TestContent.REDIRECT_URL + "1").setResponseType(ResponseType.CODE.toString()).buildQueryMessage();
		TestContent.doRequest(request);
	}

	public void testTokenResponse() throws Exception {
		OAuthClientRequest request = OAuthClientRequest.authorizationLocation(TestContent.AUTHORIZATION_ENPOINT).setClientId(TestContent.CLIENT_ID).setScope("scope").setRedirectURI(TestContent.REDIRECT_URL + "2").setResponseType(ResponseType.TOKEN.toString()).buildQueryMessage();
		HttpURLConnection c = TestContent.doRequest(request);
		String fragment = c.getURL().toURI().getFragment();
		log.info(fragment);
		Map<String, Object> map = OAuthUtils.decodeForm(fragment);
		log.info(map.get(OAuth.OAUTH_EXPIRES_IN));
		log.info(map.get(OAuth.OAUTH_ACCESS_TOKEN));
	}
}