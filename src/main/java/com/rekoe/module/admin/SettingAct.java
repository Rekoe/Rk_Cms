package com.rekoe.module.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.rekoe.domain.Setting;
import com.rekoe.service.SettingService;

@IocBean
@At("/setting")
@RequiresAuthentication
public class SettingAct {

	@Inject
	private SettingService settingService;

	@At
	@Ok("fm:template.admin.setting.edit")
	public Setting edit() {
		return settingService.getSetting();
	}

	@At
	@Ok("forward:/setting/edit.rk")
	public boolean update(@Param("siteName") String siteName,
			@Param("certtext") String certtext,
			@Param("siteEnabled") boolean isSiteEnabled,
			@Param("siteCloseMessage") String siteCloseMessage) {
		Setting setting = settingService.getSetting();
		setting.setCerttext(certtext);
		setting.setSiteCloseMessage(siteCloseMessage);
		setting.setSiteEnabled(isSiteEnabled);
		setting.setSiteName(siteName);
		settingService.update(setting);
		return true;
	}

}
