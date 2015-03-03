package com.rekoe.module.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
@At("/admin/statistics")
@RequiresAuthentication
public class StatisticsAct {

	@At
	@Ok("fm:template.admin.statistics.view")
	public void view() {

	}

	@At
	@Ok("fm:template.admin.statistics.setting")
	@RequiresPermissions("admin:statistics")
	public boolean setting(@Param("isEnabled") Boolean isEnabled) {
		return Lang.isEmpty(isEnabled) ? false : isEnabled;
	}
}
