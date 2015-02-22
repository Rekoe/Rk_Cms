package com.rekoe;

import java.util.List;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.rekoe.domain.Article;
import com.rekoe.domain.ArticleCategory;
import com.rekoe.domain.CmsAcquisition;
import com.rekoe.domain.Permission;
import com.rekoe.domain.PermissionCategory;
import com.rekoe.domain.Role;
import com.rekoe.domain.Setting;
import com.rekoe.domain.User;

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
		dao.create(CmsAcquisition.class, false);
		// 若必要的数据表不存在，则初始化数据库
		if (!dao.exists(User.class)) {
			dao.create(User.class, true);
			dao.create(Role.class, true);
			dao.create(Permission.class, true);
			dao.create(Setting.class, true);
			dao.create(Article.class, true);
			dao.create(ArticleCategory.class, true);
			dao.create(PermissionCategory.class, true);
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
