package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import com.rekoe.common.page.Pagination;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class BaseService<T> extends IdEntityService<T> {

	protected final static int DEFAULT_PAGE_NUMBER = 20;

	public BaseService() {
		super();
	}

	public BaseService(Dao dao) {
		super(dao);
	}

	public Pagination getObjListByPager(Integer pageNumber, int pageSize, Condition cnd) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<T> list = dao().query(getEntityClass(), cnd, pager);
		pager.setRecordCount(dao().count(getEntityClass(), cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}

	public Pagination getObjListByPager(Integer pageNumber, Condition cnd) {
		return getObjListByPager(pageNumber, DEFAULT_PAGE_NUMBER, cnd);
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}
	
	public void delete(String[] ids) {
		dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
	}
}
