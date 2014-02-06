package com.rekoe;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.rekoe.filter.ShiroActionFilter;
import com.rekoe.mvc.view.FreemarkerViewMaker;
import com.rekoe.mvc.view.JPEGViewMaker;

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "/ioc",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.rekoe" })
@SetupBy(MvcSetup.class)
@Fail(">>:/admin/common/unauthorized")
@Localization("msg")
@Filters(@By(type = ShiroActionFilter.class, args = "/login.jsp"))
@Encoding(input = "UTF-8", output = "UTF-8")
@Views({ FreemarkerViewMaker.class,JPEGViewMaker.class})
public class MainModule {

}
