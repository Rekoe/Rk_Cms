var ioc = {
	gatheConf : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : "gathe_core.properties"
		}
	},
	dbconf : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : "jdbc.properties"
		}
	},
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			create : "init",
			depose : 'close'
		},
		fields : {
			url : {
				java : "$dbconf.get('db.url', 'jdbc:mysql://127.0.0.1:3306/platform?useUnicode=true&characterEncoding=utf-8')"
			},
			username : {
				java : "$dbconf.get('db.username', 'root')"
			},
			password : {
				java : "$dbconf.get('db.password', 'root')"
			},
			maxActive : {
				java : "$dbconf.getInt('db.maxActive', 20)"
			},
			validationQuery : "SELECT 'x'",
			testWhileIdle : true,
			testOnBorrow : false,
			testOnReturn : false,
			filters : "mergeStat",
			connectionProperties : "druid.stat.slowSqlMillis=1000"
		}
	},

	dao : {
		type : "org.nutz.dao.impl.NutDaoExt",
		args : [ {
			refer : "dataSource"
		} ],
		fields : {
			executor : {
				refer : "cacheExecutor"
			}
		}
	},
	cacheExecutor : {
		type : "org.nutz.plugins.cache.dao.CachedNutDaoExecutor",
		fields : {
			cacheProvider : {
				refer : "cacheProvider"
			},
			cachedTableNames : [ "article", "article_category", "acquisition",
					"crawler_rule", "system_permission", "permission_category",
					"system_role", "system_setting" ],
			enableWhenTrans : false, // 事务作用域内不启用缓存,默认也是false
			db : "MYSQL"
		}
	},
	cacheProvider : {
		type : "org.nutz.plugins.cache.dao.impl.provider.MemoryDaoCacheProvider",
		fields : {
			cacheSize : 10000000
		},
		events : {
			create : "init"
		}
	}
};