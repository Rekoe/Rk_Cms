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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.utils.TestContent;

/**
 * This tests against Section 5 of the OAuth 2.0 Draft 10 implementation
 * 
 * 
 * 
 * 
 */
public class ResourceTest {

	private final static Log log = Logs.get();

	public static void main(String[] args) throws Exception {
		ResourceTest test = new ResourceTest();
		test.testResourceAccessHeaderNoToken();
	}
	public void testResourceAccessBodyValidToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString(TestContent.BODY_OAUTH2.length()));
			OutputStream ost = httpURLConnection.getOutputStream();
			PrintWriter pw = new PrintWriter(ost);
			pw.print(TestContent.BODY_OAUTH2);
			pw.flush();
			pw.close();
			testValidTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessBodyInvalidToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString("access_token=randominvalidtoken".length()));
			OutputStream ost = httpURLConnection.getOutputStream();
			PrintWriter pw = new PrintWriter(ost);
			pw.print("access_token=randominvalidtoken");
			pw.flush();
			pw.close();
			testInvalidTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessBodyNoToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			testNoTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessBodyOAuthWrongVersionToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString("access_token=randominvalidtoken&oauth_signature_method=HMAC-SHA1".length()));
			OutputStream ost = httpURLConnection.getOutputStream();
			PrintWriter pw = new PrintWriter(ost);
			pw.print("access_token=randominvalidtoken&oauth_signature_method=HMAC-SHA1");
			pw.flush();
			pw.close();
			testWrongOAuthVersionResponse(httpURLConnection);
		}
	}

	public void testResourceAccessBodyExpiredToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString(TestContent.BODY_OAUTH2_EXPIRED.length()));
			OutputStream ost = httpURLConnection.getOutputStream();
			PrintWriter pw = new PrintWriter(ost);
			pw.print(TestContent.BODY_OAUTH2_EXPIRED);
			pw.flush();
			pw.close();
			testExpiredTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessBodyInsufficientToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_BODY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", Integer.toString(TestContent.BODY_OAUTH2_INSUFFICIENT.length()));
			OutputStream ost = httpURLConnection.getOutputStream();
			PrintWriter pw = new PrintWriter(ost);
			pw.print(TestContent.BODY_OAUTH2_INSUFFICIENT);
			pw.flush();
			pw.close();
			testInsufficientScopeResponse(httpURLConnection);
		}
	}

	public void testResourceAccessQueryValidToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_QUERY + "?" + TestContent.QUERY_OAUTH2);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("GET");
			InputStream inputStream = null;
			if (httpURLConnection.getResponseCode() == 400) {
				inputStream = httpURLConnection.getErrorStream();
			} else {
				inputStream = httpURLConnection.getInputStream();
			}
			String responseBody = OAuthUtils.saveStreamAsString(inputStream);
			log.info(responseBody);
		}
	}

	public void testResourceAccessQueryInvalidToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_QUERY + "?" + "access_token=randominvalidtoken");
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("GET");
			testInvalidTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessQueryNoToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_QUERY);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("GET");
			testNoTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessHeaderValidToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_HEADER);
		URLConnection c = url.openConnection();
		c.addRequestProperty(TestContent.HEADER_AUTHORIZATION, TestContent.AUTHORIZATION_HEADER_OAUTH2);
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("GET");
			testValidTokenResponse(httpURLConnection);
		}
	}

	public void testResourceAccessHeaderNoToken() throws Exception {
		URL url = new URL(TestContent.RESOURCE_SERVER + TestContent.PROTECTED_RESOURCE_HEADER);
		URLConnection c = url.openConnection();
		if (c instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) c;
			httpURLConnection.setRequestMethod("GET");
			testNoTokenResponse(httpURLConnection);
		}
	}

	void testInvalidTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
		log.info(httpURLConnection.getResponseCode());
		String wwwAuthHeader = httpURLConnection.getHeaderField(TestContent.HEADER_WWW_AUTHENTICATE);
		log.info(wwwAuthHeader);
		Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
		String realm = headerValues.get("realm");
		log.info(realm);
		String error = headerValues.get("error");
		// assertEquals(OAuthError.ResourceResponse.INVALID_TOKEN, error);
		log.info(error);
	}

	void testValidTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
		InputStream inputStream = null;
		if (httpURLConnection.getResponseCode() == 400) {
			inputStream = httpURLConnection.getErrorStream();
		} else {
			inputStream = httpURLConnection.getInputStream();
		}
		String responseBody = OAuthUtils.saveStreamAsString(inputStream);
		// assertEquals(TestContent.ACCESS_TOKEN_VALID, responseBody);
		log.info(responseBody);
	}

	private void testNoTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
		// For the request with no token the response should be
		// - 401
		// - WWW-Authenticate header should be there
		// - only realm should be included
		log.info(httpURLConnection.getResponseCode());
		String wwwAuthHeader = httpURLConnection.getHeaderField(TestContent.HEADER_WWW_AUTHENTICATE);
		log.info(wwwAuthHeader);
		Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
		log.info(headerValues.size());
		String realm = headerValues.get("realm");
		log.info(realm);
	}

	private void testExpiredTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
		// For the invalid token the response should be
		// - 401
		// - WWW-Authenticate header should be there
		// - realm included
		// - error=expired_token
		log.info(httpURLConnection.getResponseCode());
		String wwwAuthHeader = httpURLConnection.getHeaderField(TestContent.HEADER_WWW_AUTHENTICATE);
		log.info(wwwAuthHeader);
		Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
		String realm = headerValues.get("realm");
		log.info(realm);
		String error = headerValues.get("error");
		// assertEquals(OAuthError.ResourceResponse.EXPIRED_TOKEN, error);
		log.info(error);
	}

	private void testInsufficientScopeResponse(HttpURLConnection httpURLConnection) throws Exception {
		// For the invalid token the response should be
		// - 403
		// - WWW-Authenticate header should be there
		// - realm included
		// - error=insufficient_scope
		log.info(httpURLConnection.getResponseCode());
		String wwwAuthHeader = httpURLConnection.getHeaderField(TestContent.HEADER_WWW_AUTHENTICATE);
		log.info(wwwAuthHeader);
		Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
		String realm = headerValues.get("realm");
		log.info(realm);
		String error = headerValues.get("error");
		// assertEquals(OAuthError.ResourceResponse.INSUFFICIENT_SCOPE, error);
		log.info(error);
	}

	private void testWrongOAuthVersionResponse(HttpURLConnection httpURLConnection) throws Exception {
		// For the wrong OAuth version response
		// - 400
		// - WWW-Authenticate header should be there
		// - error=invalid_request
		log.info(httpURLConnection.getResponseCode());
		String wwwAuthHeader = httpURLConnection.getHeaderField(TestContent.HEADER_WWW_AUTHENTICATE);
		log.info(wwwAuthHeader);
		Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
		String realm = headerValues.get("realm");
		log.info(realm);
		String error = headerValues.get("error");
		// assertEquals(OAuthError.CodeResponse.INVALID_REQUEST, error);
		log.info(error);
	}
}