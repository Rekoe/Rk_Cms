package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Zone;

@IocBean(fields = { "dao" })
public class ZoneService extends BaseService<Zone> {
	public ZoneService() {
		super();
	}

	public ZoneService(Dao dao) {
		super(dao);
	}

	public List<Zone> list() {
		return query(null, null);
	}

	public void insert(Zone zone) {
		dao().insert(zone);
	}

	public Zone findBySid(int sid) {
		return dao().fetch(getEntityClass(), Cnd.where("serverid", "=", sid));
	}

	public Zone fetch(String id) {
		return dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
	}

	public void update(Zone zone) {
		dao().update(zone);
	}

	public Pagination getObjListByPager(Integer pageNumber) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, 20);
		List<Zone> list = dao().query(getEntityClass(), null, pager);
		pager.setRecordCount(dao().count(getEntityClass(), null));
		Pagination pagination = new Pagination(pageNumber, 20, pager.getRecordCount(), list);
		return pagination;
	}
}
