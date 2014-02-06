package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.NutConfigException;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.Message;
import com.rekoe.domain.Permission;
import com.rekoe.domain.Role;
import com.rekoe.service.PermissionService;
import com.rekoe.service.RoleService;

@IocBean
@At("/role")
@RequiresAuthentication
public class AdminRoleAct {

	@Inject
	private RoleService roleService;

	@Inject
	private PermissionService permissionService;

	@At
	@Ok("fm:template.admin.role.list")
	@RequiresPermissions({ "admin:role" })
	public Object list(@Param("pageNumber") Integer pageNumber) {
		return roleService.getRoleListByPager(pageNumber, 20);
	}

	@At
	@Ok("fm:template.admin.role.edit")
	public Object edit(@Param("id") long id, HttpServletRequest req) {
		Role role = roleService.view(id);
		List<Permission> pList = permissionService.list();
		req.setAttribute("pList", pList);
		return role;
	}

	@At
	@Ok(">>:/role/list.rk")
	// @AdaptBy(type = PairIgnoreAdaptor.class)
	public void update(@Param("::role.") Role tempRole, @Param("name") String name, @Param("authorities") int[] permIds) {
		Role $role = roleService.view(tempRole.getId());
		if (!Lang.isEmpty($role)) {
			List<Permission> perms = permissionService.query(Cnd.where("id", "in", permIds), null);
			$role.setDescription(tempRole.getDescription());
			$role.setName(name);
			roleService.updateRoleRelation($role, perms);
		} else {
			throw new NutConfigException("用户不存在");
		}
	}

	/**
	 * 添加新的角色
	 */
	@At
	@Ok("fm:template.admin.role.add")
	public List<Permission> add() {
		return permissionService.list();
	}

	@At
	@Ok(">>:${obj==true?'/role/list.rk':'/admin/common/unauthorized.rk'}")
	public boolean save(@Param("name") String name, @Param("description") String desc, @Param("authorities") int[] ids) {
		Role role = roleService.fetchByName(name);
		if (Lang.isEmpty(role)) {
			role = new Role();
			role.setDescription(desc);
			role.setName(name);
			role.setPermissions(permissionService.query(Cnd.where("id", "in", ids), null));
			roleService.insert(role);
			return true;
		}
		return false;
	}

	@At
	@Ok("raw")
	@RequiresPermissions("admin:delete")
	public Message delete(@Param("ids") Long[] uids,HttpServletRequest req) {
		for (Long id : uids) {
			roleService.delete(id);
		}
		return Message.success("admin.message.success", req);
	}
}
