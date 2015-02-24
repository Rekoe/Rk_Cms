var ioc = {
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		 fields : {
			driverClassName : 'org.sqlite.JDBC',
            url : 'jdbc:sqlite:D:/Tools/Gits/Rk_Cms/src/test/resources/crawler.s3db'
        }
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	}
}