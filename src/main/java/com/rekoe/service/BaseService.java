package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import com.rekoe.common.page.Pagination;

public class BaseService<T> extends IdEntityService<T> {

	public BaseService() {
		super();
	}

	public BaseService(Dao dao) {
		super(dao);
	}

	public Pagination getObjListByPager(Dao dao, Integer pageNumber, int pageSize, Condition cnd, Class<T> entityType) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao.createPager(pageNumber, pageSize);
		List<T> list = dao.query(entityType, cnd, pager);
		pager.setRecordCount(dao.count(entityType, cnd));
		Pagination pagination = new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
		return pagination;
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}
}
