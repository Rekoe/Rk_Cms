package com.rekoe.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.extras.mvc.annotation.Authority;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rekoe.cms.controller.BaseAction;
import com.rekoe.cms.model.Role;

@IocBean
@InjectName
public class CmsRoleAct extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(CmsRoleAct.class);
	@At("/admin/role/list")
	@Ok("fm:/cms_sys/role/list.html")
	@Authority(module=17 , action=2 , desc="读取操作")
	public void list(HttpServletRequest request) {
		List<Role> list = basicDao.search(Role.class, "id");
		
		log.info("list");
		request.setAttribute("list", list);
	}
	
	@At("/admin/role/add")
	@Ok("fm:/cms_sys/role/add.html")
	@Authority(module=17 , action=1 , desc="创建操作")
	public void add() {
	}
	@At("/admin/role/save")
	@Ok("redirect:${obj == null ? '/admin/role/add' : '/admin/role/list'}")
	@Authority(module=17 , action=9 , desc="创建操作")
	public Role save(@Param("::role.") Role role) {
		log.info(role.toString());
		if (role.getId() == 0){
			role =basicDao.save(role);
		}else{
			basicDao.update(role);
		}
		return role;
	}
	@At("/admin/role/edit_permis")
	@Ok("fm:/cms_sys/role/edit_permis.html")
	@Authority(module=17 , action=8 , desc="更新操作")
	public void editPermis(@Param("id") int id,HttpServletRequest request) {
		Role role = basicDao.find(id,Role.class);
		role = basicDao.findLink(role, "permission");
		request.setAttribute("role", role);
	}
	@At("/admin/role/edit")
	@Ok("fm:/cms_sys/role/edit.html")
	@Authority(module=17 , action=8 , desc="更新操作")
	public void edit(@Param("id") int id,HttpServletRequest request) {
		Role role = basicDao.find(id,Role.class);
		request.setAttribute("role", role);
	}
	
	@At("/admin/role/delete")
	@Ok("redirect:/admin/role/list")
	@Authority(module=17 , action=2 , desc="更新操作")
	public void delete(@Param("ids") String ids)
	{
		basicDao.deleteByIds(Role.class, ids);
	}
}
