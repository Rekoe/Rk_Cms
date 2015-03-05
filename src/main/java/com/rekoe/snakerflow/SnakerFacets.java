package com.rekoe.snakerflow;

import javax.sql.DataSource;

import org.nutz.ioc.loader.annotation.IocBean;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.cfg.Configuration;

/**
 * 获取SnakerEngine的门面类
 * @author yuqs
 * @since 2.0
 */
@IocBean(args="dataSource")
public class SnakerFacets {
    
    private SnakerEngine engine;

    public SnakerFacets(DataSource dataSource) {
        Configuration cnf = new Configuration();
        cnf.initAccessDBObject(dataSource);
        engine = cnf.buildSnakerEngine();
    }

    public SnakerEngine getEngine() {
        return engine;
    }
}
