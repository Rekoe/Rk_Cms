package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.service.PermissionCategoryService;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
@At("/admin/permission/category")
@RequiresAuthentication
public class PermissionCategoryAct {

	@Inject
	private PermissionCategoryService permissionCategoryService;

	@At
	@Ok("fm:template.admin.permission_category.list")
	public Pagination list(@Param("pageNumber") Integer pageNumber) {
		return permissionCategoryService.getPermissionCategoryListByPager(pageNumber);
	}

	@At
	@Ok("fm:template.admin.permission_category.edit")
	public PermissionCategory edit(String id) {
		return permissionCategoryService.fetchByID(id);
	}
	@At
	@Ok(">>:/admin/permission/category/list.rk")
	public boolean update(@Param("name")String name,@Param("id")String id) {
		permissionCategoryService.update(Chain.make("name", name), Cnd.where("id", "=", id));
		return true;
	}
	@At
	@Ok("fm:template.admin.permission_category.add")
	public void add() {
	}
	
	@At
	@Ok(">>:/admin/permission/category/list.rk")
	public void save(@Param("name") String name) {
		PermissionCategory pc = new PermissionCategory();
		pc.setName(name);
		permissionCategoryService.insert(pc);
	}
	
	@At
	@Ok("json")
	public Message delete(@Param("id") String id, HttpServletRequest req) {
		PermissionCategory pc = permissionCategoryService.fetchByID(id);
		if (pc.isLocked()) {
			return Message.error("admin.permissionCategory.deleteLockedNotAllowed", req);
		}
		permissionCategoryService.remove(id);
		return Message.success("admin.common.success", req);
	}
}
