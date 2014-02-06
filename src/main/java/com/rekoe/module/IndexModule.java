package com.rekoe.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @author 科技㊣²º¹³
 * 2014年2月6日 下午8:58:00
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
public class IndexModule {

	@At
	@Ok("fm:template.front.index")
	public String index() {
		return "WellCome";
	}
}
