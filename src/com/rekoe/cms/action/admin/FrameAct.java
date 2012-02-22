package com.rekoe.cms.action.admin;

import org.nutz.extras.mvc.annotation.Authority;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@InjectName
public class FrameAct {

	@At("/admin/frame/user_main")
	@Ok("fm:cms_sys.frame.user_main")
	@Authority(module=1, action=1, desc="用户管理-main")
	public void userMain() {}

	@At("/admin/frame/user_left")
	@Ok("fm:cms_sys.frame.user_left")
	@Authority(module=1, action=1, desc="用户管理-left")
	public void userLeft() {}

	@At("/admin/frame/user_right")
	@Ok("fm:cms_sys.frame.user_right")
	@Authority(module=1, action=1, desc="用户管理-right")
	public void userRight() {}
	//文章
	@At("/admin/frame/article_main")
	@Ok("fm:cms_sys.frame.article_main")
	@Authority(module=1, action=1, desc="文章管理-main")
	public void artcileMain() {}

	@At("/admin/frame/article_left")
	@Ok("fm:cms_sys.frame.article_left")
	@Authority(module=1, action=1, desc="文章管理-left")
	public void artcileLeft() {}

	@At("/admin/frame/article_right")
	@Ok("fm:cms_sys.frame.article_right")
	@Authority(module=1, action=1, desc="文章管理-right")
	public void artcileRight() {}
}
