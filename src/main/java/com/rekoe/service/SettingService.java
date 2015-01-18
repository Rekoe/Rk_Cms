package com.rekoe.service;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import com.rekoe.domain.Setting;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
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
