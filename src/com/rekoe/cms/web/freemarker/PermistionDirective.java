package com.rekoe.cms.web.freemarker;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;

import com.rekoe.cms.filter.AuthorityFilter;
import com.rekoe.cms.web.UserSession;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 后台管理员权限许可
 */
@IocBean
public class PermistionDirective implements TemplateDirectiveModel {
	public static final String PARAM_RESOURCE = "resource";
	public static final String PARAM_ACTION = "action";
	public static final String PARM_IS_UTIL = "util";
	@SuppressWarnings("unchecked")
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		int resource = DirectiveUtils.getInt(PARAM_RESOURCE, params);
		int action = DirectiveUtils.getInt(PARAM_ACTION, params);
		boolean isUtil = DirectiveUtils.getBool(PARM_IS_UTIL, params);
		if(isUtil)
		{
			if(hasPermission(resource, action))
			{
				body.render(env.getOut());
			}
		}else{
			ActionContext ac = Mvcs.getActionContext();
			HttpServletRequest request = ac.getRequest();
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute(AuthorityFilter.USER_SEIIOIN_KEY);
			boolean pass = userSession == null?false:userSession.check(resource, action);
			if (pass) {
				body.render(env.getOut());
			}
		}
	}
	/**
	 * 判断是否有权限
	 * @param action
	 * @param acl
	 * @return
	 */
	private static boolean hasPermission(int sys,int action){
		boolean flag = false;
		if ((action & sys) == sys) {
			// 判断角色优先级
			flag = !flag;
		}
		return flag;
	}
}
