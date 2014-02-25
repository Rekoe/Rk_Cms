var ioc = {
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			// 请修改下面的数据库连接信息
			url : 'jdbc:mysql://192.168.3.104:3306/platform?useUnicode=true&characterEncoding=utf-8',
			username : 'root',
			password : 'root',
			maxActive : 20,
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