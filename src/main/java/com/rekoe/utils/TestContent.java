package com.rekoe.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

public class TestContent {

	public static final String RESOURCE_SERVER_NAME = "Example OAuth Resource Server";
	public static final String ACCESS_TOKEN_VALID = "access_token_valid";
	public static final String ACCESS_TOKEN_EXPIRED = "access_token_expired";
	public static final String ACCESS_TOKEN_INSUFFICIENT = "access_token_insufficient";

	public static final String OAUTH_VERSION_1 = "oauth_token=\"some_oauth1_token\",realm=\"Something\",oauth_signature_method=\"HMAC-SHA1\"";
	public static final String OAUTH_VERSION_2 = ACCESS_TOKEN_VALID;
	public static final String OAUTH_VERSION_2_EXPIRED = ACCESS_TOKEN_EXPIRED;
	public static final String OAUTH_VERSION_2_INSUFFICIENT = ACCESS_TOKEN_INSUFFICIENT;

	public static final String OAUTH_URL_ENCODED_VERSION_1 = OAUTH_VERSION_1;
	public static final String OAUTH_URL_ENCODED_VERSION_2 = "access_token=" + OAUTH_VERSION_2;
	public static final String OAUTH_URL_ENCODED_VERSION_2_EXPIRED = "access_token=" + OAUTH_VERSION_2_EXPIRED;
	public static final String OAUTH_URL_ENCODED_VERSION_2_INSUFFICIENT = "access_token=" + OAUTH_VERSION_2_INSUFFICIENT;

	public static final String AUTHORIZATION_HEADER_OAUTH1 = "OAuth " + OAUTH_VERSION_1;
	public static final String AUTHORIZATION_HEADER_OAUTH2 = "Bearer " + OAUTH_VERSION_2;
	public static final String AUTHORIZATION_HEADER_OAUTH2_EXPIRED = "Bearer " + OAUTH_VERSION_2_EXPIRED;
	public static final String AUTHORIZATION_HEADER_OAUTH2_INSUFFICIENT = "Bearer " + OAUTH_VERSION_2_INSUFFICIENT;

	public static final String BODY_OAUTH1 = OAUTH_URL_ENCODED_VERSION_1;
	public static final String BODY_OAUTH2 = OAUTH_URL_ENCODED_VERSION_2;
	public static final String BODY_OAUTH2_EXPIRED = OAUTH_URL_ENCODED_VERSION_2_EXPIRED;
	public static final String BODY_OAUTH2_INSUFFICIENT = OAUTH_URL_ENCODED_VERSION_2_INSUFFICIENT;

	public static final String QUERY_OAUTH1 = OAUTH_URL_ENCODED_VERSION_1;
	public static final String QUERY_OAUTH2 = OAUTH_URL_ENCODED_VERSION_2;
	public static final String QUERY_OAUTH2_EXPIRED = OAUTH_URL_ENCODED_VERSION_2_EXPIRED;
	public static final String QUERY_OAUTH2_INSUFFICIENT = OAUTH_URL_ENCODED_VERSION_2_INSUFFICIENT;

	public static final String CLIENT_ID = "test_id";
	public static final String CLIENT_SECRET = "test_secret";
	public static final String USERNAME = "test_username";
	public static final String PASSWORD = "test_password";

	public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
	public static final String HEADER_AUTHORIZATION = "Authorization";

	public static final String AUTHORIZATION_CODE = "known_authz_code";

	public static final String ASSERTION = "<samlp:AuthnRequest\n" + "   xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n" + "   xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"\n" + "   ID=\"aaf23196-1773-2113-474a-fe114412ab72\"\n" + "   Version=\"2.0\"\n" + "   IssueInstant=\"2004-12-05T09:21:59Z\"\n" + "   AssertionConsumerServiceIndex=\"0\"\n" + "   AttributeConsumingServiceIndex=\"0\">\n" + "   <saml:Issuer>https://sp.example.com/SAML2</saml:Issuer>\n" + "   <samlp:NameIDPolicy\n" + "     AllowCreate=\"true\"\n" + "     Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\"/>\n" + " </samlp:AuthnRequest>";
	public static final String ASSERTION_TYPE = "http://xml.coverpages.org/saml.html";

	public static final String ACCESS_TOKEN_ENDPOINT = "http://www.rekoe.com/token";
	public static final String AUTHORIZATION_ENPOINT = "http://www.rekoe.com/authz";
	public static final String REDIRECT_URL = "http://www.rekoe.com/redirect";
	public static final String RESOURCE_SERVER = "http://www.rekoe.com";
	public static final String PROTECTED_RESOURCE_HEADER = "/resource_header";
	public static final String PROTECTED_RESOURCE_BODY = "/resource_body";
	public static final String PROTECTED_RESOURCE_QUERY = "/resource_query";

	public static final String TEST_WEBAPP_PATH = "/server";

	public static HttpURLConnection doRequest(OAuthClientRequest req) throws IOException {
		URL url = new URL(req.getLocationUri());
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.setInstanceFollowRedirects(true);
		c.connect();
		c.getResponseCode();
		return c;
	}
}
