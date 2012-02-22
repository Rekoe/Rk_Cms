package com.rekoe.cms.socialauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.brickred.socialauth.AbstractProvider;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.exception.UserDeniedPermissionException;
import org.brickred.socialauth.oauthstrategy.OAuthStrategyBase;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@SuppressWarnings("serial")
public abstract class AbstractOAuthProvider extends AbstractProvider implements AuthProvider  {
	
	private static final Log log = Logs.get();

	protected Permission scope;
	protected OAuthConfig config;
	protected Profile userProfile;
	protected AccessGrant accessGrant;
	protected OAuthStrategyBase authenticationStrategy;

	protected String[] AllPerms;
	protected String[] AuthPerms;
	protected String PROFILE_URL;
	protected Map<String, String> ENDPOINTS = new HashMap<String, String>();

	public AbstractOAuthProvider(final OAuthConfig providerConfig) {
		this.config = providerConfig;
	}

	public String getLoginRedirectURL(final String successUrl) throws Exception {
		return authenticationStrategy.getLoginRedirectURL(successUrl);
	}

	public Profile verifyResponse(HttpServletRequest httpReq) throws Exception {
		Map<String, String> params = SocialAuthUtil.getRequestParametersMap(httpReq);
		return doVerifyResponse(params);
	}

	public Profile verifyResponse(Map<String, String> params) throws Exception {
		return doVerifyResponse(params);
	}

	protected Profile doVerifyResponse(final Map<String, String> requestParams)
			throws Exception {
        log.info("Retrieving Access Token in verify response function");
        if (requestParams.get("error_reason") != null && "user_denied".equals(requestParams.get("error_reason"))) {
                throw new UserDeniedPermissionException();
        }
        accessGrant = authenticationStrategy.verifyResponse(requestParams, verifyResponseMethod());

        if (accessGrant != null) {
                log.debug("Obtaining user profile");
                Profile proFile = authLogin();
                return proFile;
        } else {
                throw new SocialAuthException("Access token not found");
        }
	}

    protected abstract Profile authLogin() throws Exception ;
	
	public Response api(String arg0, String arg1, Map<String, String> arg2,
			Map<String, String> arg3, String arg4) throws Exception {
		return null;
	}

	public List<Contact> getContactList() throws Exception {
		return null;
	}

	public void logout() {
	}

	@Override
	public void setAccessGrant(AccessGrant accessGrant) throws Exception {
		this.accessGrant = accessGrant;
	}

	@Override
	public void setPermission(Permission permission) {
		this.scope = permission;
		authenticationStrategy.setPermission(this.scope);
		authenticationStrategy.setScope(getScope());
	}

	public void updateStatus(String status) throws Exception {
	}

	public Profile getUserProfile() throws Exception {
		return userProfile;
	}

	public AccessGrant getAccessGrant() {
		return accessGrant;
	}

	public String getProviderId() {
		return config.getId();
	}

	protected String getScope() {
		StringBuffer result = new StringBuffer();
		String arr[] = null;
		if (Permission.AUTHENTICATE_ONLY.equals(scope)) {
			arr = AuthPerms;
		} else if (Permission.CUSTOM.equals(scope)
				&& config.getCustomPermissions() != null) {
			arr = config.getCustomPermissions().split(",");
		} else {
			arr = AllPerms;
		}
		if (arr.length > 0)
			result.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			result.append(",").append(arr[i]);
		}
		return result.toString();
	}
	
	protected String verifyResponseMethod() {
		return "GET";
	}
}
