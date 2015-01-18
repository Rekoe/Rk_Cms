package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.PermissionCategory;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean(args = { "refer:dao" })
public class PermissionCategoryService extends IdEntityService<PermissionCategory> {

	public PermissionCategoryService() {
	}

	public PermissionCategoryService(Dao dao) {
		super(dao);
	}

	public List<PermissionCategory> list() {
		List<PermissionCategory> list = query(null, null);
		for (PermissionCategory pc : list) {
			dao().fetchLinks(pc, "permissions");
		}
		return list;
	}

	public void insert(PermissionCategory permission) {
		dao().insert(permission);
	}

	public PermissionCategory fetchByID(String id) {
		return dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
	}

	public PermissionCategory view(String id) {
		return fetchByID(id);
	}

	public void update(PermissionCategory permission) {
		dao().update(permission);
	}

	public void remove(String id) {
		dao().execute(Sqls.createf("delete from permission_category where id = %s", id));
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}

	public Pagination getPermissionCategoryListByPager(Integer pageNumber) {
		int pageSize = 20;
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<PermissionCategory> list = dao().query(PermissionCategory.class, null, pager);
		pager.setRecordCount(dao().count(PermissionCategory.class, null));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
