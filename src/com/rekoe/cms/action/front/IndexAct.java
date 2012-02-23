package com.rekoe.cms.action.front;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.JPEGView;

@IocBean
@InjectName
public class IndexAct {
	@At("/index/")
	@Ok("fm:front.index")
	public void logout(HttpSession session) {
		
	}
	/**
	 * 验证码
	 */
	@At("/security/captcha/?")
	@Fail("jsp:error.404")
	public View captcha()
	{
		return new JPEGView();
	}
}
