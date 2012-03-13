package com.rekoe.cms.dao;

import java.beans.PropertyVetoException;

import org.nutz.dao.impl.NutDao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DynamicDao {

	/**
	 * 
	 */
	public static NutDao getNutzDao(String url) throws PropertyVetoException
	{
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql://"+ url +":3306/xxx?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("xxxx");
		ds.setPassword("xxxx");
		ds.setAutoCommitOnClose(true);
		//ds.setPreferredTestQuery(preferredTestQuery)
		//ds.setAutomaticTestTable("");
		ds.setForceIgnoreUnresolvedTransactions(false);
		ds.setTestConnectionOnCheckin(true);
		ds.setIdleConnectionTestPeriod(60);
		/*ds.setMaxStatements(100);
		ds.setCheckoutTimeout(5000);
		ds.setInitialPoolSize(3);
		ds.setMinPoolSize(10);
		ds.setMaxPoolSize(15);
		ds.setMaxIdleTime(50);
		ds.setAcquireIncrement(3);
		ds.setAcquireRetryAttempts(30);
		ds.setAcquireRetryDelay(1000);
		ds.setMaxIdleTimeExcessConnections(1800);*/
		//ds.close();  // 关闭池内所有连接
		NutDao dao = new NutDao(ds);
		return dao;
	}
}
