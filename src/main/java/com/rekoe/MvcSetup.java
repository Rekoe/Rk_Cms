package com.rekoe;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.plugins.view.freemarker.FreeMarkerConfigurer;

import com.rekoe.domain.User;

import freemarker.template.Configuration;

/**
 * @author 科技㊣²º¹³<br/>
 *         2014年2月3日 下午4:48:45³<br/>
 *         http://www.rekoe.com QQ:5382211
 */
public class MvcSetup implements Setup {

	@SuppressWarnings("serial")
	@Override
	public void init(NutConfig config) {
		Ioc ioc = config.getIoc();
		Dao dao = ioc.get(Dao.class);
		// 加载freemarker自定义标签　自定义宏路径
		ioc.get(Configuration.class).setAutoImports(new HashMap<String, String>(2) {
			{
				put("p", "/ftl/pony/index.ftl");
				put("s", "/ftl/spring.ftl");
			}
		});
		ioc.get(FreeMarkerConfigurer.class, "mapTags");
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

	}

	@Override
	public void destroy(NutConfig config) {

	}
}
