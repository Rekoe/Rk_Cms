package com.rekoe.cms.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rekoe.cms.dao.ManagerDao;
import com.rekoe.cms.filter.AuthorityFilter;
import com.rekoe.cms.model.Manager;
import com.rekoe.cms.model.Role;
import com.rekoe.cms.web.UserSession;

@IocBean
@InjectName
public class CmsLoginAct {
	private static final Logger log = LoggerFactory.getLogger(CmsLoginAct.class);
	
	@Inject
	private ManagerDao managerDao;
	@At("/admin")
	@Ok("fm:/cms_sys/login.html")
	public void input(HttpServletRequest request)
	{
		log.info("admin input");
	}
	@At("/admin/index")
	@Ok("fm:/cms_sys/index.html")
	public void index(HttpServletRequest req)
	{
		UserSession userSession = (UserSession) req.getSession().getAttribute(AuthorityFilter.USER_SEIIOIN_KEY);
		log.info(""+userSession);
	}
	@At("/admin/login")
	@Ok("fm:${obj == null ? '/cms_sys/login.html' : '/cms_sys/index.html'}")
	public UserSession submit(@Param("username") String username, @Param("password") String password, HttpServletRequest req)
	{
		Manager m = managerDao.login(username, password);
		if (m != null && m.isState()) {
			m = managerDao.findLink(m, "roles");
			List<Role> roles = m.getRoles();
			List<Role> temp = new ArrayList<Role>();
			for (Role r : roles) {
				r = managerDao.findLink(r, "permission");
				temp.add(r);
			}
			m.setRoles(temp);
			roles = null;// 释放资源
			req.getSession().setAttribute("admin", m);
			req.setAttribute("admin", m);
			if (m.getLoginTime() != null) {
				m.setLastLoginTime(m.getLoginTime());
			}
			if (m.getLoginIp() != null) {
				m.setLastLoginIp(m.getLoginIp());
			}
			m.setLoginTime(new Date());
			m.setLoginIp(req.getRemoteAddr());
			m.setLogintimes(m.getLogintimes() + 1);
			managerDao.update(m);
			UserSession userSession = new UserSession(temp);
			req.getSession().setAttribute(AuthorityFilter.USER_SEIIOIN_KEY, userSession);
			return userSession;
		} else {
			if (m == null) {
				req.setAttribute("error", "用户名或者密码错误！");
			} else {
				req.setAttribute("error", "用户已经被禁用！");
			}
			return null;
		}
	}
	
}
