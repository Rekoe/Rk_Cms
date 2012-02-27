package com.rekoe.cms.action.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.cms.common.page.Pagination;
import com.rekoe.cms.dao.ManagerDao;
import com.rekoe.cms.model.Manager;
import com.rekoe.cms.model.Role;
import com.rekoe.cms.utils.SystemContext;
import com.rk.cms.controller.BaseAction;

@IocBean
@InjectName
public class CmsAdminAct extends BaseAction{
	
	@Inject
	private ManagerDao managerDao;
	
	@At("/admin/admin/list")
	@Ok("fm:/cms_sys/admin/list.html")
	public void list(@Param("pageNo") Integer pageNo,HttpServletRequest request) {
		Pagination pagination = basicDao.getPage(Manager.class, pageNo==null?1:pageNo.intValue(), SystemContext.PAGE_SIZE, "id");
		request.setAttribute("pagination", pagination);
	}
	
	@At("/admin/admin/add")
	@Ok("fm:/cms_sys/admin/add.html")
	public void add() {
	}
	
	/**
	 * 添加新用户
	 */
	@At("/admin/admin/save")
	@Ok("redirect:${obj == null ? '/admin/admin/add' : '/admin/admin/list'}")
	public Manager save(@Param("::user.") Manager user,HttpServletRequest request) {
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()))
		{
			return null;
		}
		Manager m = basicDao.findByCondition(Manager.class, Cnd.where("username", "=", user.getUsername()));
		if(m == null)
		{
			user.setLastLoginIp(request.getLocalAddr());
			user.setLastLoginTime(new Date());
			user.setLogintimes(0);
			user = managerDao.save(user);
		}else{
			return null;
		}
		return user;
	}
	@At("/admin/admin/update")
	@Ok("redirect:${obj == null ? '/admin/admin/update' : '/admin/admin/list'}")
	public Manager update(@Param("::user.") Manager user,HttpServletRequest request) {
		if(StringUtils.isBlank(user.getPassword()))
		{
			return null;
		}
		if(!managerDao.update(user))
		{
			return null;
		}
		return user;
	}
	/**
	 * 检查用户
	 */
	@At("/admin/admin/check/username")
	@Ok("json")
	public boolean checkUserName(@Param("::user.")Manager manager)
	{
		Manager m = basicDao.findByCondition(Manager.class, Cnd.where("username", "=", manager.getUsername()));
		if(m == null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 设置管理员角色
	 */
	@At("/admin/admin/e_user_role")
	@Ok("fm:/cms_sys/admin/edit_user_role.html")
	public void editUserRole(@Param("id") int id,HttpServletRequest req)
	{
		Manager m = managerDao.find(id, Manager.class);
		m = managerDao.findLink(m, "roles");
		req.setAttribute("m", m);
		List<Role> roles = basicDao.search(Role.class, "rule");
		req.setAttribute("pagination", roles);
	}
	
	@At("/admin/admin/user/role")
	public void roleSet(@Param("id") int id, @Param("role") int role, @Param("action") boolean action,HttpServletResponse response) {
		if (action) {
			managerDao.save("t_manager_role", Chain.make("manager_id", id).add("role_id", role));
		} else {
			managerDao.delete("t_manager_role", Cnd.where("manager_id", "=", id).and("role_id", "=", role));
		}
		//return "{success:true}";
		renderJson(response, "{success:true}");
	}
	@At("/admin/admin/edit")
	@Ok("fm:/cms_sys/admin/edit.html")
	public void edit(@Param("id") int id,HttpServletRequest request) {
		Manager m = managerDao.find(id, Manager.class);
		request.setAttribute("m", m);
	}
	
	@At("/admin/admin/delete")
	@Ok("redirect:/admin/admin/list")
	public void delete(@Param("ids") String ids)
	{
		basicDao.deleteByIds(Manager.class, ids);
	}
}
