var ioc = {
	dbconf: {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields: {
			paths : "jdbc.properties"
		}
	},
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			// 请修改下面的数据库连接信息
			url : {java:"$dbconf.get('db.url', 'jdbc:mysql://127.0.0.1:3306/platform?useUnicode=true&characterEncoding=utf-8')"},
			username : {java:"$dbconf.get('db.username', 'root')"},
			password : {java:"$dbconf.get('db.password', 'root')"},
			maxActive : {java:"$dbconf.getInt('db.maxActive', 20)"},
			validationQuery : "SELECT 'x'",
			testWhileIdle : true,
			testOnBorrow : false,
			testOnReturn : false
		}
	},

	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [ {
			refer : 'dataSource'
		} ]
	}
};