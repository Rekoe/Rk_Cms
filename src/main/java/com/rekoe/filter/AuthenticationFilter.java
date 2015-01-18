package com.rekoe.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class AuthenticationFilter extends FormAuthenticationFilter implements ActionFilter {

	protected AuthenticationToken createToken(HttpServletRequest request) {
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new UsernamePasswordToken(username, password, rememberMe, host);
	}

	@Override
	public View match(ActionContext actionContext) {
		HttpServletRequest request = actionContext.getRequest();
		AuthenticationToken authenticationToken = createToken(request);
		request.setAttribute("loginToken", authenticationToken);
		return null;
	}
}
