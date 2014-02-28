package com.rekoe;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.impl.NutActionChainMaker;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.rekoe.filter.ShiroActionFilter;
import com.rekoe.mvc.view.FreemarkerViewMaker;
import com.rekoe.mvc.view.JPEGViewMaker;
import com.rekoe.mvc.view.OauthJsonBodyViewMaker;

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "com/rekoe/mvc/view", "/ioc", "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.rekoe" })
@SetupBy(MvcSetup.class)
@Fail(">>:/admin/common/unauthorized.rk")
@Localization("msg")
@Filters(@By(type = ShiroActionFilter.class, args = "/login.jsp"))
@Encoding(input = "UTF-8", output = "UTF-8")
@ChainBy(type = NutActionChainMaker.class, args = { "com/rekoe/mvc/mvc-chains.js" })
@Views({ FreemarkerViewMaker.class, JPEGViewMaker.class, OauthJsonBodyViewMaker.class })
public class MainModule {

}
