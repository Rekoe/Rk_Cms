package com.rekoe.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Zone;

@IocBean(fields = { "dao" })
public class ZoneService extends BaseService<Zone> {

	private Map<String, Zone> cache = new ConcurrentHashMap<String, Zone>();
	private List<String> topList = new ArrayList<String>();
	private boolean looad;

	public ZoneService() {
		super();
	}

	public ZoneService(Dao dao) {
		super(dao);
	}

	public List<Zone> list() {
		return query(null, null);
	}

	public Zone getTopZone() {
		if (Lang.isEmpty(topList)) {
			map();
			if (Lang.isEmpty(topList)) {
				Iterator<String> iterator = cache.keySet().iterator();
				while (iterator.hasNext()) {
					String id = iterator.next();
					return cache.get(id);
				}
			}
		}
		return null;
	}

	public Map<String, Zone> map() {
		if (!looad) {
			List<Zone> list = list();
			for (Zone z : list) {
				cache.put(z.getId(), z);
				if (z.isFresh()) {
					if (!topList.contains(z.getId())) {
						topList.add(z.getId());
					}
				}
			}
			looad = true;
		}
		return cache;
	}

	public void insert(Zone zone) {
		boolean isRight = true;
		try {
			dao().insert(zone);
		} catch (Exception e) {
			isRight = false;
		}
		if (isRight) {
			cache.put(zone.getId(), zone);
			if (zone.isFresh()) {
				if (!topList.contains(zone.getId())) {
					topList.add(zone.getId());
				}
			}
		}
	}

	public Zone findBySid(int sid) {
		return dao().fetch(getEntityClass(), Cnd.where("serverid", "=", sid));
	}

	public Zone fetch(String id) {
		Zone zone = cache.get(id);
		if (Lang.isEmpty(zone)) {
			zone = dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
			if (Lang.isEmpty(zone)) {
				if (zone.isFresh()) {
					if (!topList.contains(zone.getId())) {
						topList.add(zone.getId());
					}
				}
			}
		}
		return zone;
	}

	public void update(Zone zone) {
		boolean isRight = true;
		try {
			dao().update(zone);
		} catch (Exception e) {
			isRight = false;
		}
		if (isRight) {
			if (zone.isFresh()) {
				if (!topList.contains(zone.getId())) {
					topList.add(zone.getId());
				}
			} else {
				topList.remove(zone.getId());
			}
		}
	}

	public Pagination getObjListByPager(Integer pageNumber) {
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, 20);
		List<Zone> list = dao().query(getEntityClass(), null, pager);
		pager.setRecordCount(dao().count(getEntityClass(), null));
		Pagination pagination = new Pagination(pageNumber, 20, pager.getRecordCount(), list);
		return pagination;
	}

	public Collection<Zone> getZoneList() {
		return cache.values();
	}
}
