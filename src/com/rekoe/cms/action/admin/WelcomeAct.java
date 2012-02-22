package com.rekoe.cms.action.admin;

import org.nutz.extras.mvc.annotation.Authority;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@IocBean
@InjectName
public class WelcomeAct {
	private static final Logger log = LoggerFactory.getLogger(WelcomeAct.class);
	@At("/admin/index")
	@Ok("fm:/cms_sys/index.html")
	@Authority(module=1, action=1, desc="管理首页")
	public void index() {
		log.info("admin index");
	}
	@At("/admin/top")
	@Ok("fm:/cms_sys/top.html")
	@Authority(module=1, action=1, desc="管理主界面")
	public void top() {
	}
	@At("/admin/main")
	@Ok("fm:/cms_sys/main.html")
	@Authority(module=1, action=1, desc="管理头部")
	public void main() {
	}
	@At("/admin/left")
	@Ok("fm:/cms_sys/left.html")
	@Authority(module=1, action=1, desc="管理左侧")
	public void left() {
	}
	@At("/admin/right")
	@Ok("fm:/cms_sys/right.html")
	@Authority(module=1, action=1, desc="管理右侧")
	public void right() {
	}
}
