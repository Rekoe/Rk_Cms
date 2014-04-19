package com.rekoe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Permission;

@IocBean(args = { "refer:dao" })
public class PermissionService extends IdEntityService<Permission> {

	public PermissionService() {
	}

	public PermissionService(Dao dao) {
		super(dao);
	}

	public List<Permission> list() {
		return query(null, null);
	}

	public Map<Long, String> map() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<Permission> permissions = query(null, null);
		for (Permission permission : permissions) {
			map.put(permission.getId(), permission.getName());
		}
		return map;
	}

	public void insert(Permission permission) {
		dao().insert(permission);
	}

	public Permission view(Long id) {
		return fetch(id);
	}

	public void update(Permission permission) {
		dao().update(permission);
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}

	public Pagination getPermissionListByPager(Integer pageNumber) {
		return getPermissionListByPager(pageNumber, null);
	}

	public Pagination getPermissionListByPager(Integer pageNumber, String permissionCategoryId) {
		int pageSize = 20;
		pageNumber = getPageNumber(pageNumber);
		Cnd cnd = Cnd.where("permissionCategoryId", "=", permissionCategoryId);
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<Permission> list = dao().query(Permission.class, StringUtils.isBlank(permissionCategoryId) ? null : cnd, pager);
		pager.setRecordCount(dao().count(Permission.class, StringUtils.isBlank(permissionCategoryId) ? null : cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
