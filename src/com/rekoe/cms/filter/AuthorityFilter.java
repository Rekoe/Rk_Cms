package com.rekoe.cms.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.extras.mvc.annotation.Authority;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.FreemarkerView;

import com.rekoe.cms.web.UserSession;

public class AuthorityFilter implements ActionFilter{
	
	private static final Log log = Logs.getLog(AuthorityFilter.class);
	//private static final View UNAUTH =  new HttpStatusView(403);
	public static final String USER_SEIIOIN_KEY = "_USER_SESSION_KEY_";
	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";
	@Override
	public View match(ActionContext ac) {
		HttpServletRequest req = ac.getRequest();
		HttpSession session = req.getSession();
		long time = System.currentTimeMillis();
		req.setAttribute(START_TIME, time);
		Authority a = ac.getMethod().getAnnotation(Authority.class);
		boolean havePermission = true;
		if(a == null){
			return null;
		}
		int resource = a.module();
		int action = a.action();
		UserSession userSession = (UserSession) session.getAttribute(USER_SEIIOIN_KEY);
		if(userSession != null)
		{
			havePermission = userSession.check(resource, action);
		}else{
			havePermission = false;
		}
		if(!havePermission)
		{
			if(log.isWarnEnabled()){
				log.warnf("%s 没有权限操作: [%s]", req.getRemoteHost(),a.desc());
			}
			return new FreemarkerView("/error/403.html");//UNAUTH;
		}else{
			if(log.isInfoEnabled()){
				log.infof("%s 操作了: [%s]", req.getRemoteHost(),a.desc());
			}
			//time = System.currentTimeMillis() - time;
			//log.debugf("process in {} ms: {}", time, req.getRequestURI());
			return null;
		}
	}
}