package com.rekoe.module.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Zone;
import com.rekoe.service.ZoneService;

@IocBean
@At("/admin/zone")
@RequiresAuthentication
public class ZoneAct {

	@Inject
	private ZoneService zoneService;

	@At
	@Ok("fm:template.admin.zone.list")
	public Pagination list(@Param("pageNumber") Integer pageNumber) {
		return zoneService.getObjListByPager(pageNumber);
	}

	@At
	@Ok("fm:template.admin.zone.add")
	public void add() {
	}

	@At
	@Ok(">>:/admin/zone/list.rk")
	@RequiresAuthentication
	public boolean save(@Param("::zone.") Zone zone) {
		if (Lang.isEmpty(zoneService.findBySid(zone.getServerid()))) {
			zoneService.insert(zone);
			return true;
		}
		return false;
	}

	@At
	@Ok("fm:template.admin.zone.edit")
	public Zone edit(String id, HttpServletRequest req) {
		return zoneService.fetch(id);
	}

	@At
	@Ok(">>:/admin/zone/list.rk")
	public boolean update(@Param("::zone.") Zone zone) {
		zoneService.update(zone);
		return true;
	}
}
