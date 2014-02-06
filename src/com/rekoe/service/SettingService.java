package com.rekoe.service;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.Setting;

@IocBean(args = { "refer:dao" })
public class SettingService extends BaseService<Setting> {

	public SettingService(Dao dao) {
		super(dao);
	}

	public void update(Setting setting) {
		dao().update(setting);
	}

	public Setting getSetting() {
		return dao().fetch(getEntityClass(), 1);
	}
}
