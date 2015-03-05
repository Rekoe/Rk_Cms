package com.rekoe;

import java.util.List;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.Ioc2;
import org.nutz.ioc.ObjectProxy;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.snaker.engine.SnakerEngine;

import com.rekoe.domain.User;
import com.rekoe.snakerflow.SnakerFacets;

/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class MvcSetup implements Setup {

	@Override
	public void init(NutConfig config) {
		Ioc ioc = config.getIoc();
		Dao dao = ioc.get(Dao.class);
		Daos.createTablesInPackage(dao, User.class.getPackage().getName(), false);
		// 若必要的数据表不存在，则初始化数据库
		if (0 == dao.count(User.class)) {
			FileSqlManager fm = new FileSqlManager("init_system_h2.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
			// 初始化用户密码（全部都是123）及salt
			List<User> userList = dao.query(User.class, null);
			for (User user : userList) {
				RandomNumberGenerator rng = new SecureRandomNumberGenerator();
				String salt = rng.nextBytes().toBase64();
				String hashedPasswordBase64 = new Sha256Hash("123", salt, 1024).toBase64();
				user.setSalt(salt);
				user.setPassword(hashedPasswordBase64);
				dao.update(user);
			}
		}
		
		// 初始化工作流引擎
		SnakerEngine snakerEngine = ioc.get(SnakerFacets.class).getEngine();
		Ioc2 ioc2 = (Ioc2)ioc;
		ioc2.getIocContext().save("app", "snakerEngine", new ObjectProxy(snakerEngine));
	}

	@Override
	public void destroy(NutConfig config) {

	}
}
