package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.service.PermissionCategoryService;
import com.rekoe.service.PermissionService;

/**
 * @author 科技㊣²º¹³
 * 2014年4月19日 上午9:20:28
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
@At("/admin/permission")
@RequiresAuthentication
public class PermissionAct {

	@Inject
	private PermissionCategoryService permissionCategoryService;
	@Inject
	private PermissionService permissionService;

	@At
	@Ok("fm:template.admin.permission.list")
	public Pagination list(@Param("pageNumber") Integer pageNumber) {
		return permissionService.getPermissionListByPager(pageNumber);
	}

	@At("/list_category/?")
	@Ok("fm:template.admin.permission.list")
	public Pagination listCategory(String id,@Param("pageNumber") Integer pageNumber) {
		return permissionService.getPermissionListByPager(pageNumber,id);
	}

	@At
	@Ok("fm:template.admin.permission.edit")
	public List<PermissionCategory> edit(long id, HttpServletRequest req) {
		Permission permission = permissionService.fetch(id);
		req.setAttribute("permission", permission);
		return add();
	}

	@At
	@Ok("fm:template.admin.permission.add")
	public List<PermissionCategory> add() {
		List<PermissionCategory> list = permissionCategoryService.list();
		return list;
	}
	
	@At
	@Ok("json")
	public Message delete(@Param("id") long id, HttpServletRequest req) {
		Permission permission = permissionService.fetch(id);
		if (permission.isLocked()) {
			return Message.error("admin.permissionCategory.deleteLockedNotAllowed", req);
		}
		permissionService.delete(id);
		return Message.success("admin.common.success", req);
	}
	
	@At
	@Ok(">>:/admin/permission/list.rk")
	public boolean update(@Param("::permission.")Permission permission,@Param("description")String description,@Param("name")String name,@Param("id")String id) {
		permission.setName(name);
		permission.setDescription(description);
		permissionService.update(permission);
		return true;
	}
	
	@At
	@Ok(">>:/admin/permission/list.rk")
	public void save(@Param("name") String name,@Param("permissionCategoryId") String permissionCategoryId,@Param("description") String description) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setDescription(description);
		permission.setPermissionCategoryId(permissionCategoryId);
		permissionService.insert(permission);
	}
}
