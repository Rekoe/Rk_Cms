package com.rekoe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;
import com.rekoe.domain.Role;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean(args = { "refer:dao" })
public class RoleService extends BaseService<Role> {

	public RoleService(Dao dao) {
		super(dao);
	}

	public List<Role> list() {
		return query(null, null);
	}

	public void insert(Role role) {
		role = dao().insert(role);
		dao().insertRelation(role, "permissions");
	}

	public void delete(Long id) {
		dao().delete(Role.class, id);
		dao().clear("system_role_permission", Cnd.where("roleid", "=", id));
		dao().clear("system_user_role", Cnd.where("roleid", "=", id));
	}

	public Role view(Long id) {
		return dao().fetchLinks(fetch(id), "permissions");
	}

	public void update(Role role) {
		dao().update(role);
	}

	public Role fetchByName(String name) {
		return fetch(Cnd.where("name", "=", name));
	}

	public List<String> getPermissionNameList(Role role) {
		dao().fetchLinks(role, "permissions");
		List<String> permissionNameList = new ArrayList<String>();
		for (Permission permission : role.getPermissions()) {
			permissionNameList.add(permission.getName());
		}
		return permissionNameList;
	}

	public void updateRoleRelation(Role role, List<Permission> perms) {
		dao().clearLinks(role, "permissions");
		role.getPermissions().clear();
		dao().update(role);
		if (!Lang.isEmpty(perms)) {
			role.setPermissions(perms);
			dao().insertRelation(role, "permissions");
		}
	}

	public Map<Long, String> map() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<Role> roles = query(null, null);
		for (Role role : roles) {
			map.put(role.getId(), role.getName());
		}
		return map;
	}

	public void addPermission(Long roleId, Long permissionId) {
		dao().insert("system_role_permission", Chain.make("roleid", roleId).add("permissionid", permissionId));
	}

	public void removePermission(Long roleId, Long permissionId) {
		dao().clear("system_role_permission", Cnd.where("roleid", "=", roleId).and("permissionid", "=", permissionId));
	}

	public Pagination getRoleListByPager(Integer pageNumber, int pageSize) {
		return getObjListByPager(pageNumber, pageSize, null);
	}
}
